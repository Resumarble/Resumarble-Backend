package resumarble.core.domain.prompt.application

import resumarble.core.domain.prompt.domain.PromptType

interface PromptService {
    fun registerPrompt(command: RegisterPromptCommand)

    fun deletePrompt(id: Long)

    fun getPrompt(promptType: PromptType): PromptResponse
}
