package resumarble.reactor.domain.gpt.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.reactor.domain.gpt.adapter.out.ChatCompletionMessageResponse
import resumarble.reactor.domain.gpt.adapter.out.ChatCompletionRequest
import resumarble.reactor.domain.gpt.adapter.out.OpenAiClient

@Service
@Transactional(readOnly = true)
class OpenAiService(
    private val openAiWebClient: OpenAiClient
) {

    suspend fun requestChatCompletion(
        request: ChatCompletionRequest
    ): ChatCompletionMessageResponse {
        return openAiWebClient.generateChatCompletion(request)
    }
}
