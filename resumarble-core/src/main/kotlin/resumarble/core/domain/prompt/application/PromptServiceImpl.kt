package resumarble.core.domain.prompt.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.prompt.infrastructure.PromptRepository
import resumarble.core.global.error.PromptAlreadyExistsException

@Service
@Transactional
class PromptServiceImpl(
    private val promptRepository: PromptRepository
) : PromptService {
    override fun registerPrompt(command: RegisterPromptCommand) {
        if (promptRepository.existsByPromptType(command.promptType)) {
            throw PromptAlreadyExistsException()
        }
        promptRepository.save(command.toEntity())
    }

    override fun deletePrompt(id: Long) {
        promptRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    override fun getPrompt(id: Long): PromptResponse {
        val prompt = promptRepository.findById(id)

        return PromptResponse.of(prompt)
    }
}
