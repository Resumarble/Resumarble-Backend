package resumarble.api.resume

import io.swagger.v3.oas.annotations.Operation
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.core.domain.resume.facade.InterviewQuestionResponse

interface SwaggerInterviewQuestionWebPort {

    @Operation(
        operationId = "interview-questions",
        summary = "면접 예상 질문 생성",
        description = "이력서를 기반으로 면접 예상 질문을 생성한다."
    )
    fun interviewQuestions(
        request: InterviewQuestionRequest,
        user: JwtUserDetails?
    ): Response<InterviewQuestionResponse>

    @Operation(
        operationId = "interview-questions",
        summary = "면접 예상 질문 생성 폼 여러개 요청",
        description = "테스트 API로, 개발 완료시 /interview-questions로 변경 예정입니다."
    )
    suspend fun multipleInterviewQuestions(
        request: MultipleInterviewQuestionRequest,
        user: JwtUserDetails?
    ): Response<List<InterviewQuestionResponse>>
}
