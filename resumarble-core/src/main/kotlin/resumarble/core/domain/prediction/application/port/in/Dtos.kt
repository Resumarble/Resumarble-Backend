package resumarble.core.domain.prediction.application.port.`in`

data class PredictionResponse(
    val userId: Long,
    val questionAndAnswer: List<QuestionAndAnswerResponse>
    // TODO: 노출 화면 확정시 필드 추가하기
)

data class QuestionAndAnswerResponse(
    val question: String,
    val answer: String
)
