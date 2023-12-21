package resumarble.core.domain.resume.facade

import resumarble.core.domain.prompt.application.PromptResponse

private const val PROMPT_LANGUAGE = "korean"

data class InterviewQuestionCommand(
    val userId: Long = 0L,
    val job: String,
    val career: String,
    val category: String,
    val content: String,
    val language: String = PROMPT_LANGUAGE
) {
    fun toRequestForm(prompt: PromptResponse, language: String): String {
        return prompt.createRequestForm(job, category, career, language)
    }
}

data class Predictions(
    val interviews: List<Prediction>
)

data class Prediction(
    val question: String,
    val bestAnswer: String
)
