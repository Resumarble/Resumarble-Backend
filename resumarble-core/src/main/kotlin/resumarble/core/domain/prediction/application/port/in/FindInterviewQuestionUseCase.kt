package resumarble.core.domain.prediction.application.port.`in`

import org.springframework.data.domain.Pageable

interface FindInterviewQuestionUseCase {

    fun getInterviewQuestionByUserId(userId: Long, page: Pageable): Pair<List<FindInterviewQuestionResponse>, Boolean>
}
