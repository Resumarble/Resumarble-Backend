package resumarble.reactor.domain.interview.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("interview_question")
class InterviewQuestion(
    @Id
    val id: Long = 0L
)
