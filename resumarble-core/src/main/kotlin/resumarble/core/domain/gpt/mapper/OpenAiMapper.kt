package resumarble.core.domain.gpt.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import resumarble.core.domain.feedback.application.FeedbackResponse
import resumarble.core.domain.feedback.application.FeedbackResponseList
import resumarble.core.domain.gpt.ChatCompletionMessage
import resumarble.core.domain.gpt.ChatCompletionMessageResponse
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.OpenAiRole
import resumarble.core.domain.resume.facade.Prediction
import resumarble.core.domain.resume.facade.Predictions
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

    fun completionToInterviewQuestionResponse(completion: ChatCompletionMessageResponse): List<Prediction> {
        val interviewQuestionResponse = objectMapper.readValue<Predictions>(
            completion.questionAndAnswer
        )
        return interviewQuestionResponse.interviews
    }

    fun completionToFeedbackResponse(completion: ChatCompletionMessageResponse): List<FeedbackResponse> {
        val feedbackResponse = objectMapper.readValue<FeedbackResponseList>(
            completion.questionAndAnswer
        )
        return feedbackResponse.feedbacks
    }
}
