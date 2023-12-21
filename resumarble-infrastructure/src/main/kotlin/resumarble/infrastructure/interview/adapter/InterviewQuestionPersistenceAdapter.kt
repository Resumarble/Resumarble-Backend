package resumarble.infrastructure.interview.adapter

import org.springframework.data.domain.Pageable
import resumarble.core.domain.prediction.application.port.out.DeleteInterviewQuestionPort
import resumarble.core.domain.prediction.application.port.out.FindInterviewQuestionPort
import resumarble.core.domain.prediction.application.port.out.SaveInterviewQuestionPort
import resumarble.core.domain.prediction.domain.InterviewQuestion
import resumarble.infrastructure.annotation.Adapter
import resumarble.infrastructure.interview.entity.InterviewQuestionEntity
import resumarble.infrastructure.interview.entity.InterviewQuestionEntityJpaRepository

@Adapter
class InterviewQuestionPersistenceAdapter(
    private val repository: InterviewQuestionEntityJpaRepository
) : SaveInterviewQuestionPort, FindInterviewQuestionPort, DeleteInterviewQuestionPort {
    override fun saveInterviewQuestion(interviewQuestions: List<InterviewQuestion>) {
        repository.saveAll(
            interviewQuestions.map { InterviewQuestionEntity.from(it) }
        )
    }

    override fun findInterviewQuestionListByUserId(userId: Long, page: Pageable): List<InterviewQuestion>? {
        // TODO: 페이지네이션 조회
        return listOf()
    }

    override fun deleteInterviewQuestion(predictionId: Long) {
        repository.deleteById(predictionId)
    }

    override fun findInterviewQuestionById(interviewQuestionId: Long): InterviewQuestion? {
        TODO("Not yet implemented")
    }
}
