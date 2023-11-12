package resumarble.infrastructure.prediction.entity

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PredictionEntityJpaRepository : JpaRepository<PredictionEntity, Long> {

    fun findAllByUserIdOrderByCreatedDateDesc(userId: Long, page: Pageable): Page<PredictionEntity>?

    fun findPredictionEntityById(predictionId: Long): PredictionEntity?
}
