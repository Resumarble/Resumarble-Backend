package resumarble.core.domain.prediction.application.port.`in`

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class PredictionResponse(
    @JsonProperty("prediction_id")
    val predictionId: Long,
    val job: String,
    val category: String,
    @JsonProperty("question_and_answer")
    val questionAndAnswer: List<QuestionAndAnswerResponse>,
    @JsonProperty("created_date")
    val createdDate: LocalDateTime
)

data class QuestionAndAnswerResponse(
    val question: String,
    val answer: String
)
