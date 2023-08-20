package resumarble.core.domain.job.infrastructure

import resumarble.core.domain.job.domain.Job

interface JobRepository {
    fun findAll(): List<Job>
}
