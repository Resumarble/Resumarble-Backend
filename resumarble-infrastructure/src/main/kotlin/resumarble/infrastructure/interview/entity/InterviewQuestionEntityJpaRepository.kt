package resumarble.infrastructure.interview.entity

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface InterviewQuestionEntityJpaRepository : JpaRepository<InterviewQuestionEntity, Long> {

    fun findAllByUserIdOrderByCreatedDateDesc(userId: Long, page: Pageable): Slice<InterviewQuestionEntity>?

    fun findEntityById(predictionId: Long): InterviewQuestionEntity?
}
