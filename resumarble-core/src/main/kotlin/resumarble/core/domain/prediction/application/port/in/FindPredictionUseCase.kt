package resumarble.core.domain.prediction.application.port.`in`

interface FindPredictionUseCase {

    fun getPredictionByUserId(userId: Long): List<PredictionResponse>
}
