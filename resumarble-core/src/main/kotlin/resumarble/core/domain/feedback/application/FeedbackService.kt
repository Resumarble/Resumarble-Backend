package resumarble.core.domain.feedback.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FeedbackService {

    suspend fun requestFeedback(command: List<FeedbackCommand>): String {
        return "테스트 내용입니다."
    }
}
