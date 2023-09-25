package resumarble.core.domain.user.application.service

import resumarble.core.domain.prediction.application.port.`in`.PredictionResponse

data class MyPageResponse(
    val predictions: List<PredictionResponse>
)
