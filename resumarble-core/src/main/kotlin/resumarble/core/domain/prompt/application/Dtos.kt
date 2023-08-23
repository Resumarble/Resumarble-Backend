package resumarble.core.domain.prompt.application

import resumarble.core.domain.prompt.domain.Prompt
import resumarble.core.domain.prompt.domain.PromptType

data class RegisterPromptCommand(
    val promptType: PromptType,
    val content: String
) {
    fun toEntity(): Prompt =
        Prompt(
            promptType = promptType,
            content = content
        )
}

data class PromptResponse(
    val promptType: PromptType,
    val content: String
) {
    fun createRequestForm(
        job: String,
        resumeType: String,
        questionDifficult: String,
        career: String,
        language: String
    ) = content.format(job, resumeType, questionDifficult, career, language)

    companion object {
        @JvmStatic
        fun of(prompt: Prompt) = PromptResponse(
            promptType = prompt.promptType,
            content = prompt.content
        )
    }
}
