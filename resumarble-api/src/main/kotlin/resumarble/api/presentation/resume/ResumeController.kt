package resumarble.api.presentation.resume

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.response.Response
import resumarble.core.domain.resume.facade.InterviewQuestionResponse
import resumarble.core.domain.resume.facade.ResumeFacade

@RestController
@RequestMapping("/resumes")
class ResumeController(
    private val resumeFacade: ResumeFacade
) {
    @PostMapping("/interview-questions")
    fun interviewQuestions(
        @RequestBody request: InterviewQuestionRequest
    ): Response<InterviewQuestionResponse> {
        val command = request.toCommand()
        return Response.ok(resumeFacade.generateInterviewQuestion(command))
    }
}
