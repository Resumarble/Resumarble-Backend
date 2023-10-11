package resumarble.core.domain.prediction.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.`in`.DeletePredictionCommand
import resumarble.core.domain.prediction.application.port.`in`.DeletePredictionUseCase
import resumarble.core.domain.prediction.application.port.`in`.SavePredictionUseCase
import resumarble.core.domain.prediction.application.port.out.DeletePredictionPort
import resumarble.core.domain.prediction.application.port.out.FindPredictionPort
import resumarble.core.domain.prediction.application.port.out.SavePredictionPort
import resumarble.core.domain.prediction.domain.Prediction
import resumarble.core.global.error.PredictionNotFoundException

@Service
@Transactional
class PredictionCommandService(
    private val savePredictionPort: SavePredictionPort,
    private val findPredictionPort: FindPredictionPort,
    private val deletePredictionPort: DeletePredictionPort
) : SavePredictionUseCase, DeletePredictionUseCase {
    override fun savePrediction(prediction: Prediction) {
        savePredictionPort.savePrediction(prediction)
    }

    override fun deletePrediction(command: DeletePredictionCommand) {
        val prediction: Prediction =
            findPredictionPort.findPredictionById(command.predictionId) ?: throw PredictionNotFoundException()
        prediction.authenticate(command.userId)
        deletePredictionPort.deletePrediction(command.predictionId)
    }
}
