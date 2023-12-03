package resumarble.reactor.domain.interview.infrastructure

import org.springframework.data.r2dbc.repository.R2dbcRepository
import resumarble.reactor.domain.interview.domain.InterviewQuestion

interface InterviewQuestionRepository : R2dbcRepository<InterviewQuestion, Long>
