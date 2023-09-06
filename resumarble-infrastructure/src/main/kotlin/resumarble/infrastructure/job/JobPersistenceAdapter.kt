package resumarble.infrastructure.job

import resumarble.core.domain.job.domain.Job
import resumarble.core.domain.job.infrastructure.JobRepository
import resumarble.infrastructure.annotation.Adapter
import resumarble.infrastructure.job.entity.JobJpaRepository

@Adapter
class JobPersistenceAdapter(
    private val jobJpaRepository: JobJpaRepository
) : JobRepository {
    override fun findAll(): List<Job> = jobJpaRepository.findAll()
}
