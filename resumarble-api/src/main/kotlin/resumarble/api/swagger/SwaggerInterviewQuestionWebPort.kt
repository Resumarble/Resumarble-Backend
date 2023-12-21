package resumarble.api.swagger

import io.swagger.v3.oas.annotations.Operation
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.api.resume.InterviewQuestionRequest
import resumarble.core.domain.resume.facade.Prediction

interface SwaggerInterviewQuestionWebPort {

    @Operation(
        operationId = "interview-questions",
        summary = "면접 예상 질문 생성 폼 여러개 요청",
        description = "이력서를 기반으로 면접 예상 질문을 생성한다."
    )
    suspend fun interviewQuestions(
        request: InterviewQuestionRequest,
        user: JwtUserDetails?
    ): Response<List<Prediction>>
}
