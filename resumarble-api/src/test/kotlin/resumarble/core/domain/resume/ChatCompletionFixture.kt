package resumarble.core.domain.resume

import resumarble.core.domain.gpt.ChatCompletionMessage
import resumarble.core.domain.gpt.ChatCompletionMessageResponse
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.Choices
import resumarble.core.domain.gpt.OpenAiRole
import resumarble.core.domain.gpt.Usage

object ChatCompletionFixture {

    fun chatCompletionRequest(): ChatCompletionRequest {
        return ChatCompletionRequest(
            messages = listOf(
                ChatCompletionMessage(
                    role = OpenAiRole.SYSTEM.value,
                    content = "your role is a Test Manager"
                ),
                ChatCompletionMessage(
                    role = OpenAiRole.USER.value,
                    content = "What is your career?"
                )
            )
        )
    }

    fun chatCompletionMessageResponse(): ChatCompletionMessageResponse {
        return ChatCompletionMessageResponse(
            "chatcmpl-7QyqpwdfhqwajicIEznoc6Q47XAyW",
            "chat.completion",
            1677664795,
            "gpt-3.5-turbo-0614",
            listOf(
                Choices(
                    index = 0,
                    message = ChatCompletionMessage(
                        role = OpenAiRole.SYSTEM.value,
                        content = "The 2020 World Series was played in Texas at Globe Life Field in Arlington."
                    ),
                    finishReason = "stop"
                )
            ),
            Usage(
                promptTokens = 57,
                completionTokens = 17,
                totalTokens = 74
            )
        )
    }
}
