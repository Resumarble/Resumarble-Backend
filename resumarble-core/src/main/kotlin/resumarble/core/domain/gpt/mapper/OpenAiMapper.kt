package resumarble.core.domain.gpt.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import resumarble.core.domain.gpt.ChatCompletionMessage
import resumarble.core.domain.gpt.ChatCompletionMessageResponse
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.OpenAiRole
import resumarble.core.domain.resume.facade.InterviewQuestion
import resumarble.core.domain.resume.facade.InterviewQuestionResponse
import resumarble.core.global.annotation.Mapper

@Mapper
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

    fun completionToInterviewQuestionResponse(completion: ChatCompletionMessageResponse): List<InterviewQuestion> {
        val interviewQuestionResponse = objectMapper.readValue<InterviewQuestionResponse>(
            completion.questionAndAnswer
        )
        return interviewQuestionResponse.interviews
    }
}