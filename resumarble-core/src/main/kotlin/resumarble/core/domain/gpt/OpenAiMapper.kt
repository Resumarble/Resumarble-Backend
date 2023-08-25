package resumarble.core.domain.gpt

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import resumarble.core.domain.resume.facade.InterviewQuestionResponse

@Component
class OpenAiMapper(
    private val objectMapper: ObjectMapper
) {
    fun promptAndContentToChatCompletionRequest(
        prompt: String,
        content: String
    ): ChatCompletionRequest {
        return ChatCompletionRequest(
            messages = arrayListOf(
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

    fun completionToInterviewQuestionResponse(completion: ChatCompletionMessageResponse): InterviewQuestionResponse {
        return objectMapper.readValue<InterviewQuestionResponse>(
            completion.questionAndAnswer
        )
    }
}
