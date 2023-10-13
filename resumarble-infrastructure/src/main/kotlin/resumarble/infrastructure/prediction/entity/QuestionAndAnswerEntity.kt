package resumarble.infrastructure.prediction.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.QuestionAndAnswer
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "question_and_answer")
data class QuestionAndAnswerEntity(

    private val predictionId: Long,
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private val question: Question,

    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private val answer: Answer,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) : BaseEntity() {

    fun toDomain() = QuestionAndAnswer(
        question = question,
        answer = answer
    )

    companion object {
        @JvmStatic
        fun from(predictionId: Long, questionAndAnswer: QuestionAndAnswer) = QuestionAndAnswerEntity(
            predictionId = predictionId,
            question = questionAndAnswer.question,
            answer = questionAndAnswer.answer
        )
    }
}
