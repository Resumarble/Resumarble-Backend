package resumarble.infrastructure.user.entity

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?

    fun existsByEmail(email: String): Boolean
}
