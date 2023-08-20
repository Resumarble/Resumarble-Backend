package resumarble.core.domain.job.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import resumarble.core.domain.job.domain.Job

interface JobJpaRepository : JpaRepository<Job, Long>
