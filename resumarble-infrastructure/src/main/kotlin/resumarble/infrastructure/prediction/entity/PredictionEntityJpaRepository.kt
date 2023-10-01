package resumarble.infrastructure.prediction.entity

import org.springframework.data.jpa.repository.JpaRepository

interface PredictionEntityJpaRepository : JpaRepository<PredictionEntity, Long> {

    fun findAllByUserId(userId: Long): List<PredictionEntity>?
}
