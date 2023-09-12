package resumarble.infrastructure.user.adapter

import org.springframework.data.repository.findByIdOrNull
import resumarble.core.domain.user.application.port.out.FindUserPort
import resumarble.core.domain.user.application.port.out.JoinUserPort
import resumarble.core.domain.user.domain.User
import resumarble.infrastructure.annotation.Adapter
import resumarble.infrastructure.user.entity.UserEntity
import resumarble.infrastructure.user.entity.UserJpaRepository

@Adapter
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : JoinUserPort, FindUserPort {

    override fun join(user: User) {
        userJpaRepository.save(UserEntity.from(user))
    }

    override fun findUserById(userId: Long): User? {
        return userJpaRepository.findByIdOrNull(userId)?.toDomain()
    }

    override fun findUserByEmail(email: String): User? {
        val userEntity = userJpaRepository.findByEmail(email)
        return userEntity?.toDomain()
    }

    override fun existsUserByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }
}
