package resumarble.core.domain.prediction.domain.application.port.`in`

interface FindPredictionUseCase {

    fun getPredictionByUserId(userId: Long): List<PredictionResponse>
}
