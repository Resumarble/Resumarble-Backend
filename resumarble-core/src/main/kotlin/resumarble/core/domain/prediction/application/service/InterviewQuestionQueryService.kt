package resumarble.core.domain.prediction.application.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.`in`.FindInterviewQuestionUseCase
import resumarble.core.domain.prediction.application.port.`in`.PredictionResponse
import resumarble.core.domain.prediction.application.port.`in`.QuestionAndAnswerResponse
import resumarble.core.domain.prediction.application.port.out.FindInterviewQuestionPort

@Service
@Transactional(readOnly = true)
class InterviewQuestionQueryService(
    private val findInterviewQuestionPort: FindInterviewQuestionPort
) : FindInterviewQuestionUseCase {

    override fun getInterviewQuestionByUserId(userId: Long, page: Pageable): List<PredictionResponse> {
        findInterviewQuestionPort.findInterviewQuestionListByUserId(userId, page)?.let { interviewQuestionList ->
            return interviewQuestionList
                .map { interviewQuestion ->
                    PredictionResponse(
                        predictionId = interviewQuestion.interviewQuestionId,
                        job = interviewQuestion.job.jobTitleKr,
                        category = interviewQuestion.category.value,
                        QuestionAndAnswerResponse(
                            question = interviewQuestion.question.value,
                            answer = interviewQuestion.answer.value
                        ),
                        createdDate = interviewQuestion.createdDate
                    )
                }
        } ?: return emptyList()
    }
}
