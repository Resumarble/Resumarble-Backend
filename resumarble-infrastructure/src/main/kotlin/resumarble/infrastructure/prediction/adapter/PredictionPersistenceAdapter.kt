package resumarble.infrastructure.prediction.adapter

import resumarble.core.domain.prediction.application.port.out.FindPredictionPort
import resumarble.core.domain.prediction.application.port.out.SavePredictionPort
import resumarble.core.domain.prediction.domain.Prediction
import resumarble.infrastructure.annotation.Adapter
import resumarble.infrastructure.prediction.entity.PredictionEntity
import resumarble.infrastructure.prediction.entity.PredictionEntityJpaRepository
import resumarble.infrastructure.prediction.entity.QuestionAndAnswerEntity
import resumarble.infrastructure.prediction.entity.QuestionAndAnswerEntityJpaRepository

@Adapter
class PredictionPersistenceAdapter(
    private val predictionRepository: PredictionEntityJpaRepository,
    private val questionAndAnswerRepository: QuestionAndAnswerEntityJpaRepository
) : SavePredictionPort, FindPredictionPort {
    override fun savePrediction(prediction: Prediction) {
        val predictionEntity = predictionRepository.save(PredictionEntity.from(prediction))
        val questionAndAnswerEntities = prediction.questionAndAnswer.map {
            QuestionAndAnswerEntity.from(predictionEntity.id, it)
        }
        questionAndAnswerRepository.saveAll(questionAndAnswerEntities)
    }

    override fun findPredictionsByUserId(userId: Long): List<Prediction>? {
        return predictionRepository.findAllByUserId(userId)?.let { predictions ->
            predictions.map {
                val questionAndAnswerEntities = questionAndAnswerRepository.findAllByPredictionId(it.id)
                it.toDomain(questionAndAnswerEntities)
            }
        }
    }
}
