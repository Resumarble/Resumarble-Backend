package resumarble.api.resume

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.api.swagger.SwaggerInterviewQuestionWebPort
import resumarble.core.domain.resume.facade.InterviewQuestion
import resumarble.core.domain.resume.facade.InterviewQuestionFacade

@RestController
@RequestMapping("/resumes")
class InterviewQuestionController(
    private val interviewQuestionFacade: InterviewQuestionFacade
) : SwaggerInterviewQuestionWebPort {

    @PostMapping("/interview-questions")
    override suspend fun interviewQuestions(
        @RequestBody request: InterviewQuestionRequest,
        @AuthenticationPrincipal user: JwtUserDetails?
    ): Response<List<InterviewQuestion>> {
        val userId = user?.userId ?: 0L
        val commands = request.toCommandList(userId)
        val responses = interviewQuestionFacade.generateInterviewQuestions(userId, commands)
        return Response.ok(responses)
    }
}
