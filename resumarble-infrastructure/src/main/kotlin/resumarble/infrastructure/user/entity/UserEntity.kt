package resumarble.infrastructure.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.constraints.UserRole
import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password

@Entity
@Table(name = "user")
class UserEntity(

    @Column(nullable = false, unique = true)
    private val account: String,

    @Embedded
    private val password: Password,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val jwtProvider: JwtProvider,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private val userRole: UserRole,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private val id: Long = 0L
) {
    companion object {
        @JvmStatic
        fun from(user: User): UserEntity {
            return UserEntity(
                user.account,
                user.password,
                user.provider,
                user.role,
                user.userId
            )
        }
    }

    fun toDomain(): User {
        return User(account, password, jwtProvider, userRole, id)
    }
}
