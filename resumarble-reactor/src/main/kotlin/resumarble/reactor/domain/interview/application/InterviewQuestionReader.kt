package resumarble.reactor.domain.interview.application

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import resumarble.reactor.domain.interview.domain.InterviewQuestion
import resumarble.reactor.domain.interview.infrastructure.InterviewQuestionRepository
import resumarble.reactor.global.annotation.Reader

@Reader
class InterviewQuestionReader(
    private val interviewQuestionRepository: InterviewQuestionRepository
) {
    suspend fun getInterviewQuestions(userId: Long, page: Pageable): Flow<InterviewQuestion> {
        return interviewQuestionRepository.findAllByUserId(userId, page.pageSize, page.offset.toInt())
    }
}
