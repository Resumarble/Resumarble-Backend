package resumarble.infrastructure.prediction.adapter

import org.springframework.data.domain.Pageable
import resumarble.core.domain.prediction.application.port.out.DeletePredictionPort
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
) : SavePredictionPort, FindPredictionPort, DeletePredictionPort {
    override fun savePrediction(prediction: Prediction) {
        val predictionEntity = predictionRepository.save(PredictionEntity.from(prediction))
        val questionAndAnswerEntities = prediction.questionAndAnswer.map {
            QuestionAndAnswerEntity.from(predictionEntity.id, it)
        }
        questionAndAnswerRepository.saveAll(questionAndAnswerEntities)
    }

    override fun findPredictionsByUserId(userId: Long, page: Pageable): List<Prediction>? {
        val predictionsPage = predictionRepository.findAllByUserIdOrderByCreatedDateDesc(userId, page)

        val predictions = predictionsPage?.content

        return predictions?.let { predictionEntities ->
            predictionEntities.map {
                val questionAndAnswerEntities = questionAndAnswerRepository.findAllByPredictionId(it.id)
                it.toDomain(questionAndAnswerEntities)
            }
        }
    }

    override fun deletePrediction(predictionId: Long) {
        predictionRepository.deleteById(predictionId)
    }

    override fun findPredictionById(predictionId: Long): Prediction? {
        return predictionRepository.findPredictionEntityById(predictionId)?.toDomainWithoutQuestionAndAnswer()
    }
}
