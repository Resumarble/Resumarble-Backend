package resumarble.reactor.domain.interview.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("interview_question")
class InterviewQuestion(

    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column("is_deleted")
    val isDeleted: Boolean = false,

    @Column("is_visible")
    val isVisible: Boolean = false,

    @Column("category")
    val category: Category,

    @Column("question")
    val question: String,

    @Column("answer")
    val answer: String,

    @Column("user_id")
    val userId: Long,

    @Id
    var id: Long = 0L
)
