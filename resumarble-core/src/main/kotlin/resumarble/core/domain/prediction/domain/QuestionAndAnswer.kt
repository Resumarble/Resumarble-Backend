package resumarble.core.domain.prediction.domain

data class QuestionAndAnswer(
    val id: Long = 0L,
    val question: Question,
    val answer: Answer
)

@JvmInline
value class Question(val value: String)

@JvmInline
value class Answer(val value: String)
