package resumarble.core.domain.prediction.application.port.out

interface DeletePredictionPort {

    fun deletePrediction(predictionId: Long)
}
