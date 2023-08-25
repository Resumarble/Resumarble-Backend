package resumarble.core.domain.resume.facade

import resumarble.core.domain.prompt.application.PromptResponse

data class InterviewQuestionCommand(
    val job: String,
    val career: String,
    val category: String,
    val content: String
) {
    fun toRequestForm(prompt: PromptResponse, language: String): String {
        return prompt.createRequestForm(job, career, category, content, language)
    }
}

data class InterviewQuestionResponse(
    val interviews: List<InterviewQuestion>
)

data class InterviewQuestion(
    val question: String,
    val bestAnswer: String
)
