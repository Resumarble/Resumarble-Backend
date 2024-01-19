package resumarble.api.swagger

import io.swagger.v3.oas.annotations.Operation
import resumarble.api.feedback.FeedbackRequest
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.core.domain.feedback.application.FeedbackResponse

interface SwaggerFeedbackWebPort {

    @Operation(
        operationId = "feedbacks",
        summary = "피드백 요청",
        description = "피드백을 요청한다."
    )
    suspend fun requestFeedback(
        request: List<FeedbackRequest>,
        user: JwtUserDetails?
    ): Response<List<FeedbackResponse>>
}
