package resumarble.core.domain.prediction.application.port.`in`

interface DeletePredictionUseCase {

    fun deletePrediction(command: DeletePredictionCommand)
}
