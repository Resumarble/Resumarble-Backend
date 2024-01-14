package resumarble.api.feedback

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.core.domain.feedback.application.FeedbackService

@RestController
@RequestMapping("/feedbacks")
class FeedbackApi(
    private val feedbackService: FeedbackService
) {

    @PostMapping
    suspend fun requestFeedback(
        @RequestBody request: List<FeedbackRequest>,
        @AuthenticationPrincipal user: JwtUserDetails?
    ): Response<String> {
        val userId = user?.userId ?: 0L
        val commands = request.map { it.toCommand(userId) }
        return Response.ok(feedbackService.requestFeedback(commands))
    }
}
