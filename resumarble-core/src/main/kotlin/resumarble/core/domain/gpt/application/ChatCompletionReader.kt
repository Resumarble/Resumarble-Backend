package resumarble.core.domain.gpt.application

import org.springframework.stereotype.Component
import resumarble.core.domain.feedback.application.FeedbackCommand
import resumarble.core.domain.feedback.application.FeedbackResponse
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.mapper.OpenAiMapper
import resumarble.core.domain.log.application.UserRequestLogCommand
import resumarble.core.domain.log.application.UserRequestLogWriter
import resumarble.core.domain.log.constraints.RequestOutcome
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.resume.facade.InterviewQuestionCommand
import resumarble.core.domain.resume.facade.Prediction

@Component
class ChatCompletionReader(
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper,
    private val userRequestLogWriter: UserRequestLogWriter
) {

    fun readChatCompletion(
        command: InterviewQuestionCommand,
        promptResponse: PromptResponse
    ): List<Prediction> {
        val completionRequest = prepareCompletionRequest(command, promptResponse, command.language)
        return requestChatCompletionToOpenAi(completionRequest, command.userId, command.content)
    }

    fun readChatCompletionFeedback(
        command: FeedbackCommand,
        prompt: String
    ): List<FeedbackResponse> {
        val request: ChatCompletionRequest =
            openAiMapper.promptAndContentToChatCompletionRequest(prompt, command.answer)
        val result = openAiService.requestChatCompletion(request)
        return openAiMapper.completionToFeedbackResponse(result)
    }

    private fun requestChatCompletionToOpenAi(
        completionRequest: ChatCompletionRequest,
        userId: Long,
        userContent: String
    ): List<Prediction> {
        var outcome: RequestOutcome
        var result: List<Prediction>

        try {
            val completionResult = openAiService.requestChatCompletion(completionRequest)
            result = openAiMapper.completionToInterviewQuestionResponse(completionResult)
            outcome = RequestOutcome.SUCCESS
        } catch (e: Exception) {
            result = emptyList()
            outcome = RequestOutcome.FAILED
        }

        userRequestLogWriter.save(
            UserRequestLogCommand(
                userId = userId,
                userContent = userContent,
                requestOutcome = outcome
            )
        )
        return result
    }

    private fun prepareCompletionRequest(
        command: InterviewQuestionCommand,
        promptResponse: PromptResponse,
        promptLanguage: String
    ): ChatCompletionRequest {
        val completionRequestForm = command.toRequestForm(promptResponse, promptLanguage)
        return openAiMapper.promptAndContentToChatCompletionRequest(completionRequestForm, command.content)
    }
}
