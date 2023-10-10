package resumarble.api.resume

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.core.domain.resume.facade.InterviewQuestion
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
        @RequestBody request: InterviewQuestionRequest,
        @AuthenticationPrincipal user: JwtUserDetails?
    ): Response<InterviewQuestionResponse> {
        val command = request.toCommand(user?.userId ?: 0L)
        return Response.ok(interviewQuestionFacade.generateInterviewQuestion(command))
    }

    @Operation(
        operationId = "interview-questions",
        summary = "면접 예상 질문 생성 폼 여러개 요청",
        description = "테스트 API로, 개발 완료시 /interview-questions로 변경 예정입니다."
    )
    @PostMapping("/multiple-interview-questions")
    fun multipleInterviewQuestions(
        @RequestBody request: MultipleInterviewQuestionRequest
    ): Response<List<InterviewQuestionResponse>> {
        return Response.ok(
            listOf(
                InterviewQuestionResponse(
                    listOf(
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        ),
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        ),
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        )
                    )
                ),
                InterviewQuestionResponse(
                    listOf(
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        ),
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        ),
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        )
                    )
                ),
                InterviewQuestionResponse(
                    listOf(
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        ),
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        ),
                        InterviewQuestion(
                            "Question",
                            "Answer"
                        )
                    )
                )
            )
        )
    }
}
