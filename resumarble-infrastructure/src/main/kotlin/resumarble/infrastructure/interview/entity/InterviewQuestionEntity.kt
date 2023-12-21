package resumarble.infrastructure.interview.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.InterviewQuestion
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "interview_question")
@SQLDelete(sql = "UPDATE interview_question SET is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
class InterviewQuestionEntity(

    val userId: Long,

    @Enumerated(EnumType.STRING)
    val job: Job,

    @Enumerated(EnumType.STRING)
    val category: Category,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Column(name = "question", columnDefinition = "TEXT")
    val question: String,

    @Column(name = "answer", columnDefinition = "TEXT")
    val answer: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity() {

    fun toDomain() =
        InterviewQuestion(
            interviewQuestionId = id,
            userId = userId,
            job = job,
            category = category,
            question = Question(question),
            answer = Answer(answer),
            createdDate = createdDate
        )
    companion object {
        @JvmStatic
        fun from(interviewQuestion: InterviewQuestion) = InterviewQuestionEntity(
            userId = interviewQuestion.userId,
            job = interviewQuestion.job,
            category = interviewQuestion.category,
            question = interviewQuestion.question.value,
            answer = interviewQuestion.answer.value
        )
    }
}
