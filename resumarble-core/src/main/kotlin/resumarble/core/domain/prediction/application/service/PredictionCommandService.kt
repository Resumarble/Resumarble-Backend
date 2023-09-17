package resumarble.core.domain.prediction.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.`in`.SavePredictionUseCase
import resumarble.core.domain.prediction.application.port.out.SavePredictionPort
import resumarble.core.domain.prediction.domain.Prediction

@Service
@Transactional
class PredictionCommandService(
    private val savePredictionPort: SavePredictionPort
) : SavePredictionUseCase {
    override fun savePrediction(prediction: Prediction) {
        savePredictionPort.savePrediction(prediction)
    }
}
