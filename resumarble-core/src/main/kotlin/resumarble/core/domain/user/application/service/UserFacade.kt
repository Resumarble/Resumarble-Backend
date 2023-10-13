package resumarble.core.domain.user.application.service

import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.`in`.FindPredictionUseCase
import resumarble.core.global.annotation.Facade

@Facade
class UserFacade(
    private val findPredictionUseCase: FindPredictionUseCase
) {

    @Transactional(readOnly = true)
    fun getMyPredictions(userId: Long): MyPageResponse {
        return MyPageResponse(findPredictionUseCase.getPredictionByUserId(userId))
    }
}
