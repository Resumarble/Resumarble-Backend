package resumarble.core.domain.prompt.application

interface PromptService {
    fun registerPrompt(command: RegisterPromptCommand)

    fun deletePrompt(id: Long)

    fun getPrompt(id: Long): PromptResponse
}
