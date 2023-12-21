package resumarble.core.domain.prediction.application.port.out

import resumarble.core.domain.prediction.domain.InterviewQuestion

interface SaveInterviewQuestionPort {
    fun saveInterviewQuestion(interviewQuestions: List<InterviewQuestion>)
}
