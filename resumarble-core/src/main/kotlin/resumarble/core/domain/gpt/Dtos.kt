package resumarble.core.domain.gpt

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionRequest(
    val model: String = GPT_MODEL,
    val maxTokens: Int = 2000,
    val messages: MutableList<ChatCompletionMessage> = mutableListOf()
)

data class ChatCompletionMessage(
    val role: String,
    val content: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionMessageResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choices> = listOf(),
    val usage: Usage
) {
    val questionAndAnswer: String
        get() = choices[0].message.content
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Choices(
    val index: Int,
    val message: ChatCompletionMessage,
    val finishReason: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Usage(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int
)

private const val GPT_MODEL = "gpt-3.5-turbo"
