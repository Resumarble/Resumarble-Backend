package resumarble.core.domain.prediction.domain

import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job

class Prediction(

    val userId: Long,

    val job: Job,

    val category: Category,

    private val questionAndAnswerList: List<QuestionAndAnswer>
) {
    val questionAndAnswer: List<QuestionAndAnswer>
        get() = questionAndAnswerList.toList()
}