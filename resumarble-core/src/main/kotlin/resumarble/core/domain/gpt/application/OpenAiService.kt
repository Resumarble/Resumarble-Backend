package resumarble.core.domain.gpt.application

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.gpt.ChatCompletionMessageResponse
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.client.OpenAiClient

@Service
@Transactional(readOnly = true)
class OpenAiService(
    private val openAiClient: OpenAiClient
) {

    @Value("\${external.openai.token}")
    private lateinit var token: String

    fun requestOpenAiChatCompletion(
        request: ChatCompletionRequest
    ): ChatCompletionMessageResponse {
        return openAiClient.generateChatCompletion(
            accessToken = "Bearer $token",
            request = request
        )
    }
}
