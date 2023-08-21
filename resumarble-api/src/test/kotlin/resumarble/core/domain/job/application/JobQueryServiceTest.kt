package resumarble.core.domain.job.application

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import resumarble.core.domain.job.JobFixture
import resumarble.core.domain.job.infrastructure.JobRepository

class JobQueryServiceTest : BehaviorSpec({

    val jobRepository = mockk<JobRepository>()
    val sut = JobQueryService(jobRepository)

    Given("직업 목록을 조회하는 경우") {
        val jobs = JobFixture.jobs()
        When("정상적인 요청일 때") {
            every { jobRepository.findAll() } returns jobs
            Then("직업 목록이 반환된다.") {
                sut.getAllJobs()
                verify(exactly = 1) {
                    jobRepository.findAll()
                }
            }
        }
    }
})
