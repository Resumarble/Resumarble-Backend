package resumarble.api.presentation.job

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.response.Response
import resumarble.core.domain.job.application.JobListResponse
import resumarble.core.domain.job.application.JobQueryService

@Tag(name = "JobController", description = "직업 관련 API")
@RestController
@RequestMapping("/jobs")
class JobController(
    private val jobQueryService: JobQueryService
) {

    @Operation(
        operationId = "jobs",
        summary = "직업 목록 조회",
        description = "직업 목록을 조회한다."
    )
    @GetMapping
    fun jobs(): Response<JobListResponse> {
        return Response.ok(jobQueryService.getAllJobs())
    }
}
