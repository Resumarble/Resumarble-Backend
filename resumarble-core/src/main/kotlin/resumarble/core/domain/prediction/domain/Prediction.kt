package resumarble.core.domain.prediction.domain

import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import java.time.LocalDateTime

class Prediction(

    val predictionId: Long = 0L,

    val userId: Long,

    val job: Job,

    val category: Category,

    private val questionAndAnswerList: List<QuestionAndAnswer>,

    val createdDate: LocalDateTime = LocalDateTime.now()

) {
    val questionAndAnswer: List<QuestionAndAnswer>
        get() = questionAndAnswerList.toList()
}
