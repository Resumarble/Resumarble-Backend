package resumarble.core.domain.prompt.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import resumarble.core.domain.prompt.domain.Prompt
import resumarble.core.domain.prompt.domain.PromptType

interface PromptJpaRepository : JpaRepository<Prompt, Long> {
    fun existsByPromptType(promptType: PromptType): Boolean

    fun findByPromptType(promptType: PromptType): Prompt
}
