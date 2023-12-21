package resumarble.infrastructure.interview.entity

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface InterviewQuestionEntityJpaRepository : JpaRepository<InterviewQuestionEntity, Long> {

    fun findAllByUserIdOrderByCreatedDateDesc(userId: Long, page: Pageable): Page<InterviewQuestionEntity>?

    fun findEntityById(predictionId: Long): InterviewQuestionEntity?
}
