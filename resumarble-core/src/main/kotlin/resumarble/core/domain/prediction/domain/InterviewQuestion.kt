package resumarble.core.domain.prediction.domain

import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.global.error.UnIdentifiedException
import java.time.LocalDateTime

class InterviewQuestion(

    val interviewQuestionId: Long = 0L,

    val userId: Long,

    val job: Job,

    val category: Category,

    val question: Question,

    val answer: Answer,

    val createdDate: LocalDateTime = LocalDateTime.now()
) {

    fun authenticate(userId: Long): Boolean {
        if (this.userId != userId) {
            throw UnIdentifiedException()
        }
        return true
    }
}

@JvmInline
value class Question(val value: String)

@JvmInline
value class Answer(val value: String)
