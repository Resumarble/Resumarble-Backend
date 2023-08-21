package resumarble.core.domain.gpt

import org.springframework.stereotype.Component

@Component
class OpenAiMapper {
    fun promptAndContentToChatCompletionRequest(
        prompt: String,
        content: String
    ): ChatCompletionRequest {
        return ChatCompletionRequest(
            messages = arrayListOf(
                ChatCompletionMessage(
                    role = OpenAiRole.SYSTEM.value,
                    content = prompt
                ),
                ChatCompletionMessage(
                    role = OpenAiRole.USER.value,
                    content = content
                )
            )
        )
    }
}
