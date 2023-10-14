package resumarble.core.domain.prediction.application.service

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

    override fun getPredictionByUserId(userId: Long): List<PredictionResponse> {
        findPredictionPort.findPredictionsByUserId(userId)?.let { predictions ->
            return predictions.map {
                PredictionResponse(
                    predictionId = it.predictionId,
                    job = it.job.jobTitleKr,
                    category = it.category.value,
                    questionAndAnswer = it.questionAndAnswer.map { questionAndAnswer ->
                        QuestionAndAnswerResponse(
                            qaId = questionAndAnswer.id,
                            question = questionAndAnswer.question.value,
                            answer = questionAndAnswer.answer.value
                        )
                    },
                    createdDate = it.createdDate
                )
            }
        } ?: return emptyList()
    }
}
