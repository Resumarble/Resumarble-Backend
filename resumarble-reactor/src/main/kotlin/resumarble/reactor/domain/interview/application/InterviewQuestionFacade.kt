package resumarble.reactor.domain.interview.application

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.slf4j.LoggerFactory
import resumarble.reactor.domain.gpt.application.ChatCompletionReader
import resumarble.reactor.domain.interview.domain.Category
import resumarble.reactor.global.annotation.Facade
import java.time.LocalDateTime

@Facade
class InterviewQuestionFacade(
    private val chatCompletionReader: ChatCompletionReader,
    private val interviewQuestionWriter: InterviewQuestionWriter
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
                        it.toCommand(command.userId, Category.fromValue(command.category))
                    }
                    interviewQuestionWriter.save(predictionResponses)
                }
            }

            completionResult.await()
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        log.error("사용자 결과를 저장하는데 예외가 발생했습니다. $exception")
        log.error("예외 발생 시각: " + LocalDateTime.now())
    }

    private val log = LoggerFactory.getLogger(this::class.java)
}
