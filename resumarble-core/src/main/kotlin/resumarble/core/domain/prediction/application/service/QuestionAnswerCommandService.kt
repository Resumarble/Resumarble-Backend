package resumarble.core.domain.prediction.application.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.out.QuestionAnswerRepository

@Service
@Transactional
class QuestionAnswerCommandService(
    private val questionAnswerRepository: QuestionAnswerRepository
) {
    fun deleteQuestionAnswer(qaId: Long) {
        questionAnswerRepository.findQuestionAnswer(qaId)
            ?: throw EntityNotFoundException()
        questionAnswerRepository.delete(qaId)
    }
}
