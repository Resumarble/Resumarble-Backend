package resumarble.core.domain.prediction.application.port.`in`

import java.time.LocalDateTime

data class PredictionResponse(
    val predictionId: Long,
    val job: String,
    val category: String,
    val questionAndAnswer: List<QuestionAndAnswerResponse>,
    val createdDate: LocalDateTime
)

data class QuestionAndAnswerResponse(
    val qaId: Long = 0L,
    val question: String,
    val answer: String
)
