package resumarble.core.domain.job.domain

import jakarta.persistence.*
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "job")
class Job(

    @Embedded
    private val jobTitle: JobTitle,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) : BaseEntity() {
    constructor(jobTitleKr: String, jobTitleEn: String) : this(JobTitle(jobTitleKr, jobTitleEn))

    val jobTitleKr: String
        get() = jobTitle.jobTitleKr

    val jobTitleEn: String
        get() = jobTitle.jobTitleEn
}
