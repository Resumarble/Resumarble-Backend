package resumarble.infrastructure.prediction.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import resumarble.core.domain.prediction.domain.Prediction
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "prediction")
class PredictionEntity(

    val userId: Long,

    @Enumerated(EnumType.STRING)
    val job: Job,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity() {

    fun toDomain(questionAndAnswerEntities: List<QuestionAndAnswerEntity>) =
        Prediction(
            userId = userId,
            job = job,
            questionAndAnswerList = questionAndAnswerEntities.map { it.toDomain() }
        )

    companion object {
        @JvmStatic
        fun from(prediction: Prediction) = PredictionEntity(
            userId = prediction.userId,
            job = prediction.job
        )
    }
}
