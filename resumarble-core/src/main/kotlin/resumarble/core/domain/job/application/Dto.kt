package resumarble.core.domain.job.application

data class JobResponse(
    val jobTitleKr: String,

    val jobTitleEn: String
) {
    companion object {
        @JvmStatic
        fun from(jobTitleKr: String, jobTitleEn: String): JobResponse {
            return JobResponse(jobTitleKr, jobTitleEn)
        }
    }
}

data class JobListResponse(
    val jobs: List<JobResponse>
) {
    companion object {
        @JvmStatic
        fun of(jobs: List<JobResponse>): JobListResponse {
            return JobListResponse(jobs)
        }
    }
}
