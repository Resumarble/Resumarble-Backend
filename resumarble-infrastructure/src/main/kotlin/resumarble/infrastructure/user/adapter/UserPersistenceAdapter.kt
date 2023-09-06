package resumarble.infrastructure.user.adapter

import resumarble.core.domain.user.domain.User
import resumarble.core.domain.user.repository.UserRepository
import resumarble.core.global.error.UserNotFoundException
import resumarble.infrastructure.annotation.Adapter
import resumarble.infrastructure.user.entity.UserJpaRepository

@Adapter
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    fun findByEmail(email: String): User {
        val userEntity = userJpaRepository.findByEmail(email) ?: throw UserNotFoundException()
        return userEntity.toDomain()
    }
}
