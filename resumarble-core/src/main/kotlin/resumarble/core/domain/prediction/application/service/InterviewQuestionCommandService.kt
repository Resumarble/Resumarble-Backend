package resumarble.core.domain.prediction.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prediction.application.port.`in`.DeleteInterviewQuestionUseCase
import resumarble.core.domain.prediction.application.port.`in`.DeletePredictionCommand
import resumarble.core.domain.prediction.application.port.`in`.SaveInterviewQuestionUseCase
import resumarble.core.domain.prediction.application.port.out.DeleteInterviewQuestionPort
import resumarble.core.domain.prediction.application.port.out.FindInterviewQuestionPort
import resumarble.core.domain.prediction.application.port.out.SaveInterviewQuestionPort
import resumarble.core.domain.prediction.domain.InterviewQuestion
import resumarble.core.global.error.PredictionNotFoundException

@Service
@Transactional
class InterviewQuestionCommandService(
    private val saveInterviewQuestionPort: SaveInterviewQuestionPort,
    private val findInterviewQuestionPort: FindInterviewQuestionPort,
    private val deletePredictionPort: DeleteInterviewQuestionPort
) : SaveInterviewQuestionUseCase, DeleteInterviewQuestionUseCase {
    override fun saveInterviewQuestion(interviewQuestionList: List<InterviewQuestion>) {
        saveInterviewQuestionPort.saveInterviewQuestion(interviewQuestionList)
    }

    override fun deleteInterviewQuestion(command: DeletePredictionCommand) {
        val interviewQuestion: InterviewQuestion =
            findInterviewQuestionPort.findInterviewQuestionById(command.interviewQuestionId)
                ?: throw PredictionNotFoundException()
        interviewQuestion.authenticate(command.userId)
    }
}
