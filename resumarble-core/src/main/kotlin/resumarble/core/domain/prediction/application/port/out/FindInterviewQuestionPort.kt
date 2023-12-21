package resumarble.core.domain.prediction.application.port.out

import org.springframework.data.domain.Pageable
import resumarble.core.domain.prediction.domain.InterviewQuestion

interface FindInterviewQuestionPort {

    fun findInterviewQuestionListByUserId(userId: Long, page: Pageable): Pair<List<InterviewQuestion>, Boolean>

    fun findInterviewQuestionById(interviewQuestionId: Long): InterviewQuestion?
}
