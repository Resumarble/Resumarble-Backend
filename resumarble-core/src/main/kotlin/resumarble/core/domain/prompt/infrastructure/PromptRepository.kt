package resumarble.core.domain.prompt.infrastructure

import resumarble.core.domain.prompt.domain.Prompt
import resumarble.core.domain.prompt.domain.PromptType

interface PromptRepository {
    fun save(prompt: Prompt): Prompt
    fun findById(id: Long): Prompt
    fun deleteById(id: Long)

    fun existsByPromptType(promptType: PromptType): Boolean
}
