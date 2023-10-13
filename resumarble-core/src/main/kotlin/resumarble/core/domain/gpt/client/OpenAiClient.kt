package resumarble.core.domain.gpt.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import resumarble.core.domain.gpt.ChatCompletionMessageResponse
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.global.config.FeignConfig

@FeignClient(name = "open-ai", url = "\${external.openai.url}", configuration = [FeignConfig::class])
interface OpenAiClient {

    @PostMapping("/v1/chat/completions", consumes = ["application/json"])
    fun generateChatCompletion(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        accessToken: String,
        request: ChatCompletionRequest
    ): ChatCompletionMessageResponse
}
