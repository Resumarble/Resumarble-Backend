package resumarble.reactor.domain.interview.application

import org.springframework.transaction.annotation.Transactional
import resumarble.reactor.domain.interview.infrastructure.InterviewQuestionRepository
import resumarble.reactor.global.annotation.Writer

@Writer
@Transactional
class InterviewQuestionWriter(
    private val interviewQuestionRepository: InterviewQuestionRepository
) {
    suspend fun save(commands: List<SaveInterviewQuestionCommand>) {
        interviewQuestionRepository.saveAll(commands.map { it.toEntity() })
    }
}
