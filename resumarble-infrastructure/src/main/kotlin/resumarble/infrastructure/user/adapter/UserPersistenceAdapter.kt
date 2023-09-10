package resumarble.infrastructure.user.adapter

import resumarble.core.domain.user.application.port.out.FindUserPort
import resumarble.core.domain.user.application.port.out.JoinUserPort
import resumarble.core.domain.user.domain.User
import resumarble.core.global.error.UserNotFoundException
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

    override fun getUserByUserId(userId: Long): User {
        val userEntity = userJpaRepository.findById(userId).orElseThrow { throw UserNotFoundException() }
        return userEntity.toDomain()
    }

    override fun getUserByEmail(email: String): User {
        val userEntity = userJpaRepository.findByEmail(email) ?: throw UserNotFoundException()
        return userEntity.toDomain()
    }

    override fun existsUserByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }
}
