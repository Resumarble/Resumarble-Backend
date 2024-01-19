package resumarble.api.feedback

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.api.swagger.SwaggerFeedbackWebPort
import resumarble.core.domain.feedback.application.FeedbackResponse
import resumarble.core.domain.feedback.application.FeedbackService

@RestController
@RequestMapping("/feedbacks")
class FeedbackApi(
    private val feedbackService: FeedbackService
) : SwaggerFeedbackWebPort {

    @PostMapping
    override suspend fun requestFeedback(
        @RequestBody request: List<FeedbackRequest>,
        @AuthenticationPrincipal user: JwtUserDetails?
    ): Response<List<FeedbackResponse>> {
        val userId = user?.userId ?: 0L
        val commands = request.map { it.toCommand(userId) }
        return Response.ok(feedbackService.requestFeedbacks(commands))
    }
}
