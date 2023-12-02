package resumarble.reactor.domain.interview.application

import resumarble.reactor.domain.gpt.prompt.Prompt

const val PROMPT_LANGUAGE = "korean"

data class InterviewQuestionCommand(
    val userId: Long = 0L,
    val job: String,
    val career: String,
    val category: String,
    val content: String,
    val language: String = PROMPT_LANGUAGE
) {
    fun toRequestForm(): String {
        return Prompt.generateInterviewQuestionPrompt(job, category, career, PROMPT_LANGUAGE)
    }
}

data class InterviewQuestionResponse(
    val interviews: List<PredictionResponse>
)

data class PredictionResponse(
    val question: String,
    val bestAnswer: String
)
