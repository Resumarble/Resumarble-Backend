package resumarble.core.domain.resume

import resumarble.api.resume.InterviewQuestionRequest
import resumarble.api.resume.ResumeInfo
import resumarble.core.domain.log.application.UserRequestLogCommand
import resumarble.core.domain.log.constraints.RequestOutcome
import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.QuestionAndAnswer
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.domain.prediction.facade.SavePredictionCommand
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.domain.resume.facade.InterviewQuestion
import resumarble.core.domain.resume.facade.InterviewQuestionCommand

object ResumeFixture {

    fun interviewQuestionCommand(): InterviewQuestionCommand =
        InterviewQuestionCommand(
            job = "Backend Developer",
            career = "3 years",
            category = "Teck Stack",
            content = "Kotlin, Java, Spring Boot, JPA, MySQL, Redis, Kafka, Git, Kubernetes"
        )

    fun promptResponse(): PromptResponse =
        PromptResponse(
            promptType = PromptType.INTERVIEW_QUESTION,
            content = "This is Test Prompt"
        )

    fun interviewQuestionResponse(): List<InterviewQuestion> =
        listOf(
            InterviewQuestion(
                question = "What is your career?",
                bestAnswer = "3 years"
            ),
            InterviewQuestion(
                question = "What is your job?",
                bestAnswer = "Backend Developer"
            ),
            InterviewQuestion(
                question = "What is your tech stack?",
                bestAnswer = "Kotlin, Java, Spring Boot, JPA, MySQL, Redis, Kafka, Git, Kubernetes"
            )
        )

    fun interviewQuestionRequest(): InterviewQuestionRequest {
        return InterviewQuestionRequest(
            job = "Backend Developer",
            career = "3 years",
            listOf(
                ResumeInfo(
                    category = "Technology Stack",
                    content = "Kotlin, Java, Spring Boot, JPA, MySQL, Redis, Kafka, Git, Kubernetes"
                ),
                ResumeInfo(
                    category = "Technology Stack",
                    content = "Kotlin, Java, Spring Boot, JPA, MySQL, Redis, Kafka, Git, Kubernetes"
                )
            )
        )
    }

    fun savePredictionCommand(): SavePredictionCommand {
        return SavePredictionCommand(
            userId = 1L,
            job = Job.BACKEND_ENGINEER,
            category = Category.CAREER_HISTORY,
            questionAndAnswerList = listOf(
                QuestionAndAnswer(
                    question = Question("질문1"),
                    answer = Answer("답변1")
                ),
                QuestionAndAnswer(
                    question = Question("질문2"),
                    answer = Answer("답변2")
                ),
                QuestionAndAnswer(
                    question = Question("질문3"),
                    answer = Answer("답변3")
                )
            )
        )
    }

    fun userRequestLogCommand(): UserRequestLogCommand {
        return UserRequestLogCommand(
            userId = 1L,
            userContent = "질문1",
            requestOutcome = RequestOutcome.SUCCESS
        )
    }
}
