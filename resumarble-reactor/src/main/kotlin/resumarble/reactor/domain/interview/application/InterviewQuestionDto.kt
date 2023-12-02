package resumarble.reactor.domain.interview.application

import resumarble.reactor.domain.gpt.prompt.Prompt
import resumarble.reactor.domain.interview.domain.Category

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
) {
    fun toCommand(userId: Long, category: Category): SaveInterviewQuestionCommand {
        return SaveInterviewQuestionCommand(
            userId = userId,
            category = Category.fromValue(category.value),
            question = question,
            answer = bestAnswer
        )
    }
}

data class SaveInterviewQuestionCommand(
    val userId: Long,
    val category: Category,
    val question: String,
    val answer: String
)
