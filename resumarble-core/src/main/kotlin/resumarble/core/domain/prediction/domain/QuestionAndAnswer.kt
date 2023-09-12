package resumarble.core.domain.prediction.domain

data class QuestionAndAnswer(
    val question: Question,
    val answer: Answer
)

@JvmInline
value class Question(val value: String)

@JvmInline
value class Answer(val value: String)
