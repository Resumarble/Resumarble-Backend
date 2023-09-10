package resumarble.infrastructure.prediction.entity

import org.springframework.data.jpa.repository.JpaRepository

interface PredictionEntityJpaRepository : JpaRepository<PredictionEntity, Long>
