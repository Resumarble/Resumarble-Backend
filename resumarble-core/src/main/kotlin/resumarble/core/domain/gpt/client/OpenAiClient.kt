package resumarble.core.domain.gpt.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import resumarble.core.domain.gpt.ChatCompletionsMessageResponse
import resumarble.core.domain.gpt.ChatCompletionsRequest
import resumarble.core.global.config.FeignConfig

@FeignClient(name = "open-ai", url = "\${external.openai.url}", configuration = [FeignConfig::class])
interface OpenAiClient {

    @PostMapping("/v1/chat/completions", consumes = ["application/json"])
    fun generateChatCompletion(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        accessToken: String,
        request: ChatCompletionsRequest
    ): ChatCompletionsMessageResponse
}
