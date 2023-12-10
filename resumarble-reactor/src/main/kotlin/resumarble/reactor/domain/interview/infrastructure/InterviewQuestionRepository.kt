package resumarble.reactor.domain.interview.infrastructure

import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import resumarble.reactor.domain.interview.domain.InterviewQuestion

interface InterviewQuestionRepository : R2dbcRepository<InterviewQuestion, Long> {
    @Query("SELECT * FROM interview_question WHERE user_id = :userId AND is_deleted = FALSE")
    fun findAllByUserId(userId: Long, page: Pageable): Flux<InterviewQuestion>
}
