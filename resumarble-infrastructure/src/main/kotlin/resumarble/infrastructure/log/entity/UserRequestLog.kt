package resumarble.infrastructure.log.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_request_log")
class UserRequestLog(

    @Column(nullable = false, name = "user_id")
    val userId: Long,

    @Column(nullable = false, name = "user_content")
    val userContent: String,

    @Enumerated(EnumType.STRING)
    val requestOutcome: RequestOutcome,

    @Column(nullable = false, name = "request_date")
    val requestDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
