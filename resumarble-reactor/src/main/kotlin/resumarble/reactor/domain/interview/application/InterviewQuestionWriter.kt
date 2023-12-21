package resumarble.reactor.domain.interview.application

import org.springframework.transaction.annotation.Transactional
import resumarble.reactor.domain.interview.domain.InterviewQuestion
import resumarble.reactor.domain.interview.infrastructure.InterviewQuestionRepository
import resumarble.reactor.global.annotation.Writer

@Writer
@Transactional
class InterviewQuestionWriter(
    private val interviewQuestionRepository: InterviewQuestionRepository
) {
    suspend fun save(commands: List<SaveInterviewQuestionCommand>) {
        interviewQuestionRepository.saveAll(commands.map { it.toEntity() })
            .collect {}
    }

    suspend fun renewDeleteQuestion(interviewQuestion: InterviewQuestion) {
        interviewQuestionRepository.save(interviewQuestion)
    }
}
