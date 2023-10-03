package resumarble.infrastructure.log.entity

import org.springframework.data.jpa.repository.JpaRepository

interface UserRequestLogJpaRepository : JpaRepository<UserRequestLog, Long>
