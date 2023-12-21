package resumarble.core.domain.prediction.application.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.`in`.FindInterviewQuestionResponse
import resumarble.core.domain.prediction.application.port.`in`.FindInterviewQuestionUseCase
import resumarble.core.domain.prediction.application.port.out.FindInterviewQuestionPort

@Service
@Transactional(readOnly = true)
class InterviewQuestionQueryService(
    private val findInterviewQuestionPort: FindInterviewQuestionPort
) : FindInterviewQuestionUseCase {

    override fun getInterviewQuestionByUserId(
        userId: Long,
        page: Pageable
    ): Pair<List<FindInterviewQuestionResponse>, Boolean> {
        val pair = findInterviewQuestionPort.findInterviewQuestionListByUserId(
            userId,
            page
        )
        return Pair(
            FindInterviewQuestionResponse.of(pair.first),
            pair.second
        )
    }
}
