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
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.constraints.UserRole
import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.domain.password.Password

@Entity
@Table(name = "user")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE user SET is_deleted = true where user_id = ?")
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

    private var isDeleted: Boolean = false,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private val id: Long = 0L
) {
    companion object {
        @JvmStatic
        fun from(user: User): UserEntity {
            return UserEntity(
                account = user.account,
                password = user.password,
                jwtProvider = user.provider,
                userRole = user.role,
                id = user.userId
            )
        }
    }

    fun toDomain(): User {
        return User(account, password, jwtProvider, userRole, id)
    }
}
