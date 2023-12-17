package resumarble.reactor.domain.interview.infrastructure

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import resumarble.reactor.domain.interview.domain.InterviewQuestion

@Repository
interface InterviewQuestionRepository : CoroutineCrudRepository<InterviewQuestion, Long> {
    @Query(
        "SELECT * FROM interview_question WHERE user_id = :userId AND is_deleted = FALSE" + " ORDER BY created_at DESC LIMIT :limit OFFSET :offset"
    )
    suspend fun findAllByUserId(userId: Long, limit: Int, offset: Int): Flow<InterviewQuestion>
}
