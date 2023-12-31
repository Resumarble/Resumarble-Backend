package resumarble.infrastructure.prompt.adapter

import org.springframework.data.repository.findByIdOrNull
import resumarble.core.domain.prompt.domain.Prompt
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.domain.prompt.infrastructure.PromptRepository
import resumarble.core.global.error.PromptNotFoundException
import resumarble.infrastructure.annotation.Adapter
import resumarble.infrastructure.prompt.entity.PromptJpaRepository

@Adapter
class PromptPersistenceAdapter(
    private val promptJpaRepository: PromptJpaRepository
) : PromptRepository {
    override fun save(prompt: Prompt): Prompt {
        return promptJpaRepository.save(prompt)
    }

    override fun findById(id: Long): Prompt {
        return promptJpaRepository.findByIdOrNull(id) ?: throw PromptNotFoundException()
    }

    override fun deleteById(id: Long) {
        promptJpaRepository.deleteById(id)
    }

    override fun findByPromptType(promptType: PromptType): Prompt {
        return promptJpaRepository.findByPromptType(promptType)
    }

    override fun existsByPromptType(promptType: PromptType): Boolean {
        return promptJpaRepository.existsByPromptType(promptType)
    }
}
