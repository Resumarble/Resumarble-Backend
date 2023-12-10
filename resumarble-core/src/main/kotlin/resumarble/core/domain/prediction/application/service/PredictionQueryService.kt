package resumarble.core.domain.prediction.application.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.`in`.FindPredictionUseCase
import resumarble.core.domain.prediction.application.port.`in`.PredictionResponse
import resumarble.core.domain.prediction.application.port.`in`.QuestionAndAnswerResponse
import resumarble.core.domain.prediction.application.port.out.FindPredictionPort

@Service
@Transactional(readOnly = true)
class PredictionQueryService(
    private val findPredictionPort: FindPredictionPort
) : FindPredictionUseCase {

    override fun getPredictionByUserId(userId: Long, page: Pageable): List<PredictionResponse> {
        findPredictionPort.findPredictionsByUserId(userId, page)?.let { predictions ->
            return predictions
                .filter { it.questionAndAnswer.isNotEmpty() }
                .map { prediction ->
                    PredictionResponse(
                        predictionId = prediction.predictionId,
                        job = prediction.job.jobTitleKr,
                        category = prediction.category.value,
                        questionAndAnswer = prediction.questionAndAnswer.map { questionAndAnswer ->
                            QuestionAndAnswerResponse(
                                qaId = questionAndAnswer.id,
                                question = questionAndAnswer.question.value,
                                answer = questionAndAnswer.answer.value
                            )
                        },
                        createdDate = prediction.createdDate
                    )
                }
        } ?: return emptyList()
    }
}
