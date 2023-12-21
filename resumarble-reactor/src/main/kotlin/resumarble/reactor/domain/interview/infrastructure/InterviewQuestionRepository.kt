package resumarble.reactor.domain.interview.infrastructure

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import resumarble.reactor.domain.interview.domain.InterviewQuestion

@Repository
interface InterviewQuestionRepository : CoroutineCrudRepository<InterviewQuestion, Long> {

    suspend fun findAllByUserIdAndIsDeletedIsFalseOrderByCreatedAtDesc(
        userId: Long,
        page: Pageable
    ): Flow<InterviewQuestion>

    @Query("SELECT COUNT(*) FROM interview_question WHERE user_id = :userId AND is_deleted = false")
    suspend fun countByUserIdAndIsDeletedIsFalse(userId: Long): Long
}
