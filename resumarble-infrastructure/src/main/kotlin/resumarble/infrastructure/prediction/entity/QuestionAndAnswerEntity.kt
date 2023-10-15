package resumarble.infrastructure.prediction.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.QuestionAndAnswer
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "question_and_answer")
@SQLDelete(sql = "UPDATE question_and_answer SET is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
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

    @Column(name = "is_deleted")
    private var isDeleted: Boolean = false,

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
