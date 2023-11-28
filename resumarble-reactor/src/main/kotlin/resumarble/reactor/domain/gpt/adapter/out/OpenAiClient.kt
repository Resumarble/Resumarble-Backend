package resumarble.reactor.domain.gpt.adapter.out

interface OpenAiClient {

    suspend fun generateChatCompletion(
        request: ChatCompletionRequest
    ): ChatCompletionMessageResponse
}
