package resumarble.infrastructure.prediction.entity

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
import resumarble.core.domain.prediction.domain.Prediction
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "prediction")
@SQLDelete(sql = "UPDATE prediction SET is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
class PredictionEntity(

    val userId: Long,

    @Enumerated(EnumType.STRING)
    val job: Job,

    @Enumerated(EnumType.STRING)
    val category: Category,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity() {

    fun toDomain(questionAndAnswerEntities: List<QuestionAndAnswerEntity>) =
        Prediction(
            predictionId = id,
            userId = userId,
            job = job,
            category = category,
            questionAndAnswerList = questionAndAnswerEntities.map { it.toDomain() },
            createdDate = createdDate
        )

    fun toDomainWithoutQuestionAndAnswer() = Prediction(
        predictionId = id,
        userId = userId,
        job = job,
        category = category,
        questionAndAnswerList = listOf(),
        createdDate = createdDate
    )

    companion object {
        @JvmStatic
        fun from(prediction: Prediction) = PredictionEntity(
            userId = prediction.userId,
            job = prediction.job,
            category = prediction.category
        )
    }
}
