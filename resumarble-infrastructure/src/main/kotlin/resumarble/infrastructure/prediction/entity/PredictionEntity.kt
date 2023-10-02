package resumarble.infrastructure.prediction.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import resumarble.core.domain.prediction.domain.Prediction
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "prediction")
class PredictionEntity(

    val userId: Long,

    @Enumerated(EnumType.STRING)
    val job: Job,

    @Enumerated(EnumType.STRING)
    val category: Category,

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

    companion object {
        @JvmStatic
        fun from(prediction: Prediction) = PredictionEntity(
            userId = prediction.userId,
            job = prediction.job,
            category = prediction.category
        )
    }
}
