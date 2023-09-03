package resumarble.api.presentation.resume

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.response.Response
import resumarble.core.domain.resume.facade.InterviewQuestionFacade
import resumarble.core.domain.resume.facade.InterviewQuestionResponse

@RestController
@RequestMapping("/resumes")
class InterviewQuestionController(
    private val interviewQuestionFacade: InterviewQuestionFacade
) {
    @Operation(
        operationId = "interview-questions",
        summary = "면접 예상 질문 생성",
        description = "이력서를 기반으로 면접 예상 질문을 생성한다."
    )
    @PostMapping("/interview-questions")
    fun interviewQuestions(
        @RequestBody request: InterviewQuestionRequest
    ): Response<InterviewQuestionResponse> {
        val command = request.toCommand()
        return Response.ok(interviewQuestionFacade.generateInterviewQuestion(command))
    }
}
