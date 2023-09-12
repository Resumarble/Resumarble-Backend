package resumarble.core.domain.prediction.domain.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.domain.application.port.`in`.FindPredictionUseCase
import resumarble.core.domain.prediction.domain.application.port.`in`.PredictionResponse
import resumarble.core.domain.prediction.domain.application.port.`in`.QuestionAndAnswerResponse
import resumarble.core.domain.prediction.domain.application.port.out.FindPredictionPort

@Service
@Transactional(readOnly = true)
class PredictionQueryService(
    private val findPredictionPort: FindPredictionPort
) : FindPredictionUseCase {

    override fun getPredictionByUserId(userId: Long): List<PredictionResponse> {
        return findPredictionPort.findPredictionByUserId(userId)?.let { predictions ->
            predictions.map {
                PredictionResponse(
                    userId = it.userId,
                    questionAndAnswer = it.questionAndAnswer.map { questionAndAnswer ->
                        QuestionAndAnswerResponse(
                            question = questionAndAnswer.question.value,
                            answer = questionAndAnswer.answer.value
                        )
                    }
                )
            }
        } ?: emptyList()
    }
}
