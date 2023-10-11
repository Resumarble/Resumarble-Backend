package resumarble.api.resume

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.api.swagger.SwaggerInterviewQuestionWebPort
import resumarble.core.domain.resume.facade.InterviewQuestionFacade
import resumarble.core.domain.resume.facade.InterviewQuestionResponse

@RestController
@RequestMapping("/resumes")
class InterviewQuestionController(
    private val interviewQuestionFacade: InterviewQuestionFacade
) : SwaggerInterviewQuestionWebPort {

    @PostMapping("/interview-questions")
    override fun interviewQuestions(
        @RequestBody request: InterviewQuestionRequest,
        @AuthenticationPrincipal user: JwtUserDetails?
    ): Response<InterviewQuestionResponse> {
        val command = request.toCommand(user?.userId ?: 0L)
        return Response.ok(interviewQuestionFacade.generateInterviewQuestion(command))
    }

    @PostMapping("/multiple-interview-questions")
    override suspend fun multipleInterviewQuestions(
        @RequestBody request: MultipleInterviewQuestionRequest,
        @AuthenticationPrincipal user: JwtUserDetails?
    ): Response<List<InterviewQuestionResponse>> {
        val responses = interviewQuestionFacade.generateInterviewQuestions(request.toCommandList(913L))
        return Response.ok(responses)
    }
}
