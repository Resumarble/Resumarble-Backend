package resumarble.core.domain.resume

import resumarble.api.resume.InterviewQuestionRequest
import resumarble.api.resume.ResumeInfo
import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.domain.prediction.facade.QuestionAndAnswer
import resumarble.core.domain.prediction.facade.SavePredictionCommand
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.domain.resume.facade.InterviewQuestionCommand
import resumarble.core.domain.resume.facade.Prediction

object ResumeFixture {

    fun interviewQuestionCommand(): InterviewQuestionCommand =
        InterviewQuestionCommand(
            job = "Backend Engineer",
            career = "3 years",
            category = "Teck Stack",
            content = "Kotlin, Java, Spring Boot, JPA, MySQL, Redis, Kafka, Git, Kubernetes"
        )

    fun promptResponse(): PromptResponse =
        PromptResponse(
            promptType = PromptType.INTERVIEW_QUESTION,
            content = "This is Test Prompt"
        )

    fun interviewQuestionResponse(): List<Prediction> =
        listOf(
            Prediction(
                question = "What is your career?",
                bestAnswer = "3 years"
            ),
            Prediction(
                question = "What is your job?",
                bestAnswer = "Backend Engineer"
            ),
            Prediction(
                question = "What is your tech stack?",
                bestAnswer = "Kotlin, Java, Spring Boot, JPA, MySQL, Redis, Kafka, Git, Kubernetes"
            )
        )

    fun interviewQuestionRequest(): InterviewQuestionRequest {
        return InterviewQuestionRequest(
            job = "Backend Engineer",
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
            listOf(
                QuestionAndAnswer(
                    question = Question("What is your career?"),
                    answer = Answer("3 years")
                )
            )
        )
    }
}
