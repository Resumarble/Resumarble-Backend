package resumarble.infrastructure.job.entity

import org.springframework.data.jpa.repository.JpaRepository
import resumarble.core.domain.job.domain.Job

interface JobJpaRepository : JpaRepository<Job, Long>
