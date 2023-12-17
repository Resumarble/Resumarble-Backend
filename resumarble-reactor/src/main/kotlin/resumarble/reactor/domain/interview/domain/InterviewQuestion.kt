package resumarble.reactor.domain.interview.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import resumarble.reactor.global.exception.BusinessException
import resumarble.reactor.global.exception.ErrorCode
import java.time.LocalDateTime

@Table("interview_question")
class InterviewQuestion(

    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column("is_deleted")
    var isDeleted: Boolean = false,

    @Column("category")
    val category: Category,

    val job: String,

    @Column("question")
    val question: String,

    @Column("answer")
    val answer: String,

    @Column("user_id")
    val userId: Long,

    @Id
    var id: Long = 0L
) {
    fun delete() {
        this.isDeleted = true
    }

    fun authenticate(userId: Long) {
        if (this.userId != userId) {
            throw BusinessException(ErrorCode.UNAUTHORIZED_ACCESS)
        }
    }
}
