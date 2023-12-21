package resumarble.reactor.domain.interview.application

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import resumarble.reactor.domain.gpt.application.ChatCompletionReader
import resumarble.reactor.domain.interview.domain.Category
import resumarble.reactor.domain.interview.domain.InterviewQuestion
import resumarble.reactor.global.annotation.Facade
import resumarble.reactor.global.exception.BusinessException
import resumarble.reactor.global.exception.ErrorCode
import java.time.LocalDateTime

@Facade
class InterviewQuestionFacade(
    private val chatCompletionReader: ChatCompletionReader,
    private val interviewQuestionWriter: InterviewQuestionWriter,
    private val interviewQuestionReader: InterviewQuestionReader
) {
    suspend fun generateInterviewQuestions(
        command: List<InterviewQuestionCommand>
    ): List<PredictionResponse> {
        return supervisorScope {
            command.map { command ->
                async(Dispatchers.Default) {
                    generateInterviewQuestion(command)
                }
            }.awaitAll().flatten()
        }
    }

    private suspend fun generateInterviewQuestion(command: InterviewQuestionCommand): List<PredictionResponse> {
        return coroutineScope {
            val completionResult = async(Dispatchers.IO) {
                chatCompletionReader.readChatCompletion(command)
            }

            supervisorScope {
                launch(Dispatchers.IO + handler) {
                    val predictionResponses = completionResult.await().map {
                        it.toCommand(command.userId, Category.fromValue(command.category), command.job)
                    }
                    interviewQuestionWriter.save(predictionResponses)
                }
            }
            completionResult.await()
        }
    }

    @Transactional
    suspend fun getInterviewQuestionsWithNextPageIndicator(
        userId: Long,
        page: Pageable
    ): MyPageInterviewQuestionResponse {
        // 현재 페이지의 데이터 조회
        val interviewQuestions = interviewQuestionReader.getInterviewQuestions(userId, page).toList()

        // 전체 데이터 수 조회
        val totalCount = interviewQuestionReader.getInterviewQuestionCount(userId)

        // 현재 페이지 번호와 페이지 크기
        val currentPage = page.pageNumber
        val pageSize = page.pageSize

        // 다음 페이지 존재 여부 계산
        val hasNextPage = (currentPage + 1) * pageSize < totalCount

        // 응답 객체 생성 및 반환
        return MyPageInterviewQuestionResponse(
            interviewQuestions = interviewQuestions.map { FindInterviewQuestionResponse.from(it) },
            hasNextPage = hasNextPage
        )
    }

    suspend fun deleteInterviewQuestion(interviewQuestionId: Long, userId: Long) {
        val interviewQuestion: InterviewQuestion = interviewQuestionReader.getInterviewQuestion(interviewQuestionId)
            ?: throw BusinessException(ErrorCode.NOT_FOUND)
        interviewQuestion.authenticate(userId)
        interviewQuestion.delete()
        interviewQuestionWriter.renewDeleteQuestion(interviewQuestion)
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        log.error("사용자 결과를 저장하는데 예외가 발생했습니다. $exception")
        log.error("예외 발생 시각: " + LocalDateTime.now())
    }

    private val log = LoggerFactory.getLogger(this::class.java)
}
