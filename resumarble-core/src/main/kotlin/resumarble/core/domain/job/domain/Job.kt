package resumarble.core.domain.job.domain

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import resumarble.core.global.domain.BaseEntity

@Entity
@Table(name = "job")
class Job private constructor(

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
