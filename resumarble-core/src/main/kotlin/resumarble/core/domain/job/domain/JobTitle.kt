package resumarble.core.domain.job.domain

import jakarta.persistence.Embeddable

@Embeddable
class JobTitle(
    val jobTitleKr: String,
    val jobTitleEn: String
)
