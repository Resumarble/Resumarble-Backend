package resumarble.infrastructure.prediction.adapter

import resumarble.core.domain.prediction.application.port.out.QuestionAnswerRepository
import resumarble.core.domain.prediction.domain.QuestionAndAnswer
import resumarble.infrastructure.annotation.Adapter
import resumarble.infrastructure.prediction.entity.QuestionAndAnswerEntityJpaRepository

@Adapter
class QuestionAnswerPersistenceAdapter(
    private val questionAnswerRepository: QuestionAndAnswerEntityJpaRepository
) : QuestionAnswerRepository {

    override fun delete(qaId: Long) {
        questionAnswerRepository.deleteById(qaId)
    }

    override fun findQuestionAnswer(qaId: Long): QuestionAndAnswer? {
        return questionAnswerRepository.findById(qaId)
            .map { it.toDomain() }
            .orElse(null)
    }
}
