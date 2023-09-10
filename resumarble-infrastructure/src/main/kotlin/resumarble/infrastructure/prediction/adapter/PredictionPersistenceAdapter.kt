package resumarble.infrastructure.prediction.adapter

import resumarble.infrastructure.annotation.Adapter
import resumarble.infrastructure.prediction.entity.PredictionEntityJpaRepository
import resumarble.infrastructure.prediction.entity.QuestionAndAnswerEntityJpaRepository

@Adapter
class PredictionPersistenceAdapter(
    private val predictionRepository: PredictionEntityJpaRepository,
    private val questionAndAnswerRepository: QuestionAndAnswerEntityJpaRepository
) {
    fun save() {
        // TODO
    }
}
