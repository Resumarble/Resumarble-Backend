package resumarble.core.domain.prediction.application.port.`in`

import resumarble.core.domain.prediction.domain.Prediction

interface SavePredictionUseCase {

    fun savePrediction(prediction: Prediction)
}
