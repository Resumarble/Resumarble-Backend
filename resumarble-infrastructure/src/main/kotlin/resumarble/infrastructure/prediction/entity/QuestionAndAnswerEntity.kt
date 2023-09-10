package resumarble.infrastructure.prediction.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "question_and_answer")
data class QuestionAndAnswerEntity(

    private val interviewPredictionId: Long,
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
) : BaseEntity()

@JvmInline
value class Question(
    private val value: String
)

@JvmInline
value class Answer(
    private val value: String
)
