package resumarble.core.domain.job

import resumarble.core.domain.application.JobListResponse
import resumarble.core.domain.application.JobResponse
import resumarble.core.domain.job.domain.Job

object JobFixture {

    fun jobs(): List<Job> {
        return listOf(
            Job(
                jobTitleKr = "백엔드 개발자",
                jobTitleEn = "Backend Engineer"
            ),
            Job(
                jobTitleKr = "프론트엔드 개발자",
                jobTitleEn = "Frontend Engineer"
            )
        )
    }

    fun jobListResponse(): JobListResponse {
        return JobListResponse.of(
            jobs = listOf(
                JobResponse.from(
                    jobTitleKr = "백엔드 개발자",
                    jobTitleEn = "Backend Engineer"
                ),
                JobResponse.from(
                    jobTitleKr = "프론트엔드 개발자",
                    jobTitleEn = "Frontend Engineer"
                )
            )
        )
    }
}
