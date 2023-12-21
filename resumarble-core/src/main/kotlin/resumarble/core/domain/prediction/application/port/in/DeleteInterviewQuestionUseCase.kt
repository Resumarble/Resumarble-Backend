package resumarble.core.domain.prediction.application.port.`in`

interface DeleteInterviewQuestionUseCase {

    fun deleteInterviewQuestion(command: DeletePredictionCommand)
}
