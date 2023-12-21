package resumarble.core.domain.prediction.application.port.`in`

import resumarble.core.domain.prediction.domain.InterviewQuestion

interface SaveInterviewQuestionUseCase {

    fun saveInterviewQuestion(interviewQuestionList: List<InterviewQuestion>)
}
