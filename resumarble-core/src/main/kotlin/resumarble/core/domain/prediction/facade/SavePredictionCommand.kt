package resumarble.core.domain.prediction.facade

import resumarble.core.domain.prediction.domain.Prediction
import resumarble.core.domain.prediction.domain.QuestionAndAnswer
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job

data class SavePredictionCommand(
    val userId: Long,
    val job: Job,
    val category: Category,
    val questionAndAnswerList: List<QuestionAndAnswer>
) {
    fun toDomain(): Prediction {
        return Prediction(
            userId = userId,
            job = job,
            category = category,
            questionAndAnswerList = questionAndAnswerList
        )
    }
}
