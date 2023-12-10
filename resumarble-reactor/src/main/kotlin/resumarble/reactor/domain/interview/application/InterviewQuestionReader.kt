package resumarble.reactor.domain.interview.application

import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import resumarble.reactor.domain.interview.domain.InterviewQuestion
import resumarble.reactor.domain.interview.infrastructure.InterviewQuestionRepository
import resumarble.reactor.global.annotation.Reader

@Reader
class InterviewQuestionReader(
    private val interviewQuestionRepository: InterviewQuestionRepository
) {
    fun getInterviewQuestions(userId: Long, page: Pageable): Flux<InterviewQuestion> {
        return interviewQuestionRepository.findAllByUserId(userId, page)
    }
}
