package resumarble.reactor.domain.interview.application

import resumarble.reactor.domain.gpt.prompt.Prompt
import resumarble.reactor.domain.interview.domain.Category
import resumarble.reactor.domain.interview.domain.InterviewQuestion

private const val PROMPT_LANGUAGE = "korean"

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
    fun toCommand(userId: Long, category: Category, job: String): SaveInterviewQuestionCommand {
        return SaveInterviewQuestionCommand(
            userId = userId,
            category = Category.fromValue(category.value),
            job = job,
            question = question,
            answer = bestAnswer
        )
    }
}

data class SaveInterviewQuestionCommand(
    val userId: Long,
    val category: Category,
    val job: String,
    val question: String,
    val answer: String
) {
    fun toEntity(): InterviewQuestion {
        return InterviewQuestion(
            userId = userId,
            category = category,
            job = job,
            question = question,
            answer = answer
        )
    }
}
