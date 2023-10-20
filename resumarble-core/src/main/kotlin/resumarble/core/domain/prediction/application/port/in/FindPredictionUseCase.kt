package resumarble.core.domain.prediction.application.port.`in`

import org.springframework.data.domain.Pageable

interface FindPredictionUseCase {

    fun getPredictionByUserId(userId: Long, page: Pageable): List<PredictionResponse>
}
