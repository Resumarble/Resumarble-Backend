package resumarble.core.domain.resume

import resumarble.api.resume.InterviewQuestionRequest
import resumarble.api.resume.ResumeInfo
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.domain.resume.facade.InterviewQuestion
import resumarble.core.domain.resume.facade.InterviewQuestionCommand
import resumarble.core.domain.resume.facade.InterviewQuestionResponse

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

    fun interviewQuestionResponse(): InterviewQuestionResponse =
        InterviewQuestionResponse(
            interviews = listOf(
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
        )

    fun interviewQuestionRequest(): InterviewQuestionRequest {
        return InterviewQuestionRequest(
            job = "Backend Developer",
            career = "3 years",
            ResumeInfo(
                category = "Technology Stack",
                content = "Kotlin, Java, Spring Boot, JPA, MySQL, Redis, Kafka, Git, Kubernetes"
            )
        )
    }
}
