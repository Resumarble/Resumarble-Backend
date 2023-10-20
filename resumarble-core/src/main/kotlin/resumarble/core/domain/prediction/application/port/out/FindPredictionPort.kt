package resumarble.core.domain.prediction.application.port.out

import org.springframework.data.domain.Pageable
import resumarble.core.domain.prediction.domain.Prediction

interface FindPredictionPort {

    fun findPredictionsByUserId(userId: Long, page: Pageable): List<Prediction>?

    fun findPredictionById(predictionId: Long): Prediction?
}
