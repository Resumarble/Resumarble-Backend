package resumarble.api.job

import io.kotest.assertions.print.print
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import resumarble.api.presentation.job.JobController
import resumarble.core.domain.application.JobQueryService
import resumarble.core.domain.job.JobFixture

class JobControllerTest : DescribeSpec({
    val jobQueryService = mockk<JobQueryService>()
    val mockMvc = MockMvcBuilders.standaloneSetup(JobController(jobQueryService)).build()

    describe("JobController") {
        val response = JobFixture.jobListResponse()
        context("직업 목록을 조회하면") {
            every { jobQueryService.getAllJobs() } returns response
            it("직업 목록을 반환한다.") {
                mockMvc.perform(
                    get("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isOk)
                    .andDo { print() }

                verify(exactly = 1) { jobQueryService.getAllJobs() }
            }
        }
    }
})
