package resumarble.reactor.domain.gpt.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import resumarble.reactor.domain.gpt.adapter.out.ChatCompletionMessage
import resumarble.reactor.domain.gpt.adapter.out.ChatCompletionMessageResponse
import resumarble.reactor.domain.gpt.adapter.out.ChatCompletionRequest
import resumarble.reactor.domain.gpt.constraints.OpenAiRole
import resumarble.reactor.domain.interview.application.InterviewQuestionResponse
import resumarble.reactor.domain.interview.application.PredictionResponse

@Component
class OpenAiMapper(
    private val objectMapper: ObjectMapper
) {
    fun promptAndContentToChatCompletionRequest(
        prompt: String,
        content: String
    ): ChatCompletionRequest {
        return ChatCompletionRequest(
            messages = listOf(
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

    fun completionToInterviewQuestionResponse(completion: ChatCompletionMessageResponse): List<PredictionResponse> {
        val interviewQuestionResponse = objectMapper.readValue<InterviewQuestionResponse>(
            completion.questionAndAnswer
        )
        return interviewQuestionResponse.interviews
    }
}
