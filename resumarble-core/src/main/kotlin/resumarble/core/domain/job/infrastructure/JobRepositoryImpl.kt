package resumarble.core.domain.job.infrastructure

import org.springframework.stereotype.Repository
import resumarble.core.domain.job.domain.Job

@Repository
class JobRepositoryImpl(
    private val jobJpaRepository: JobJpaRepository
) : JobRepository {
    override fun findAll(): List<Job> = jobJpaRepository.findAll()
}
