package resumarble.infrastructure.user.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password

@Entity
@Table(name = "user")
class UserEntity(
    @Column(unique = true, nullable = false)
    @Email(message = "이메일 형식이 아닙니다.")
    private val email: String,

    @Embedded
    @Column(nullable = false)
    private val password: Password,

    @Column(nullable = false)
    private val nickname: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private val id: Long = 0L
) {
    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(user.email, user.password, user.nickname, user.userId)
        }
    }

    fun toDomain(): User {
        return User(email, password, nickname, id)
    }
}
