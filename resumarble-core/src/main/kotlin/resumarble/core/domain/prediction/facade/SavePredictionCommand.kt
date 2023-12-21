package resumarble.core.domain.prediction.facade

import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.InterviewQuestion
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job

data class SavePredictionCommand(
    val userId: Long,
    val job: Job,
    val category: Category,
    val questionAndAnswerList: List<QuestionAndAnswer>
) {
    fun toDomain(): List<InterviewQuestion> {
        return questionAndAnswerList.map {
            InterviewQuestion(
                userId = userId,
                job = job,
                category = category,
                question = it.question,
                answer = it.answer
            )
        }
    }
}

data class QuestionAndAnswer(
    val question: Question,
    val answer: Answer
)
