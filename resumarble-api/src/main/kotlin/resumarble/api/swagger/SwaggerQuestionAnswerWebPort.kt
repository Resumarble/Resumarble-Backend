package resumarble.api.swagger

import io.swagger.v3.oas.annotations.Operation
import resumarble.api.global.response.Response

interface SwaggerQuestionAnswerWebPort {

    @Operation(
        operationId = "deleteQuestionAnswer",
        summary = "예상 질문 삭제",
        description = "예상 질문을 삭제합니다."
    )
    fun deleteQuestionAnswer(qaId: Long): Response<Unit>
}
