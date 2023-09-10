package resumarble.infrastructure.prediction.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "prediction")
class PredictionEntity(

    val userId: Long,

    val career: Career,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity()

@JvmInline
value class Career(
    private val value: String
)
