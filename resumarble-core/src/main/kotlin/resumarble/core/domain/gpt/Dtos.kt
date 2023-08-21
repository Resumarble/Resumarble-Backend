package resumarble.core.domain.gpt

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionsRequest(
    val model: String = GPT_MODEL,
    val maxTokens: Int = 2000,
    val messages: MutableList<ChatCompletionsMessage> = mutableListOf()
)

data class ChatCompletionsMessage(
    val role: String,
    val content: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionsMessageResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<ChoicesDto> = listOf(),
    val usage: UsageDto
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChoicesDto(
    val index: Int,
    val message: ChatCompletionsMessage,
    val finishReason: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UsageDto(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int
)

private const val GPT_MODEL = "gpt-3.5-turbo"
