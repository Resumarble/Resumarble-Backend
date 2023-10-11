package resumarble.api.swagger

import io.swagger.v3.oas.annotations.Operation
import resumarble.api.global.response.Response
import resumarble.core.domain.prediction.application.port.`in`.PredictionResponse

interface SwaggerPredictionWebPort {

    @Operation(
        operationId = "prediction",
        summary = "예상 질문 조회",
        description = "유저 PK로 해당 유저의 예상 질문들을 조회한다."
    )
    fun getPredictionByUserId(userId: Long): Response<List<PredictionResponse>>
}
