package resumarble.core.domain.user.application.service

import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.`in`.FindInterviewQuestionUseCase
import resumarble.core.global.annotation.Facade

@Facade
class UserFacade(
    private val findInterviewQuestionUseCase: FindInterviewQuestionUseCase
) {

    @Transactional(readOnly = true)
    fun getMyInterviewQuestionList(userId: Long, page: Pageable): MyPageResponse {
        return MyPageResponse.of(findInterviewQuestionUseCase.getInterviewQuestionByUserId(userId, page))
    }
}
