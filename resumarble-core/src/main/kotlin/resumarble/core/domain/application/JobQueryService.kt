package resumarble.core.domain.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.job.infrastructure.JobRepository

@Service
class JobQueryService(
    private val jobRepository: JobRepository
) {
    @Transactional(readOnly = true)
    fun getAllJobs(): JobListResponse {
        val jobs = jobRepository.findAll()
        return JobListResponse.of(
            jobs = jobs.map {
                JobResponse.from(
                    it.jobTitleKr,
                    it.jobTitleEn
                )
            }
        )
    }
}
