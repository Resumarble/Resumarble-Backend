package resumarble.core.domain.prediction.domain.application.port.out

import resumarble.core.domain.prediction.domain.Prediction

interface SavePredictionPort {
    fun savePrediction(prediction: Prediction)
}
