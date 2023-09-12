package resumarble.core.domain.prediction.domain.application.port.out

import resumarble.core.domain.prediction.domain.Prediction

interface FindPredictionPort {

    fun findPredictionByUserId(userId: Long): List<Prediction>?
}
