package resumarble.reactor.domain.gpt.adapter.out.impl

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import resumarble.reactor.domain.gpt.adapter.out.ChatCompletionMessageResponse
import resumarble.reactor.domain.gpt.adapter.out.ChatCompletionRequest
import resumarble.reactor.domain.gpt.adapter.out.OpenAiClient

@Component
class OpenAiWebClient(
    private val webClient: WebClient
) : OpenAiClient {

    override suspend fun generateChatCompletion(
        request: ChatCompletionRequest
    ): ChatCompletionMessageResponse {
        return webClient.post()
            .uri("/v1/chat/completions")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(ChatCompletionMessageResponse::class.java)
            .awaitSingle()
    }
}
