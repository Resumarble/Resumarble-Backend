package resumarble.infrastructure.prediction.adapter

import org.springframework.data.repository.findByIdOrNull
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

    override fun findPredictionByUserId(userId: Long): List<Prediction> {
        predictionRepository.findByIdOrNull(userId)?.let { predictionEntity ->
            val questionAndAnswerEntities = questionAndAnswerRepository.findAllByPredictionId(predictionEntity.id)
            return listOf(predictionEntity.toDomain(questionAndAnswerEntities))
        } ?: return emptyList()
    }
}
