package resumarble.core.domain.gpt.application

import org.springframework.stereotype.Component
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.mapper.OpenAiMapper
import resumarble.core.domain.log.application.UserRequestLogCommand
import resumarble.core.domain.log.application.UserRequestLogPublisher
import resumarble.core.domain.log.constraints.RequestOutcome
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.resume.facade.InterviewQuestion
import resumarble.core.domain.resume.facade.InterviewQuestionCommand
import resumarble.core.global.error.CompletionFailedException

@Component
class ChatCompletionReader(
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper,
    private val userRequestLogPublisher: UserRequestLogPublisher
) {

    fun readChatCompletion(
        command: InterviewQuestionCommand,
        promptResponse: PromptResponse
    ): List<InterviewQuestion> {
        val completionRequest = prepareCompletionRequest(command, promptResponse, command.language)
        return requestChatCompletion(completionRequest, command.userId, command.content)
    }

    private fun prepareCompletionRequest(
        command: InterviewQuestionCommand,
        promptResponse: PromptResponse,
        promptLanguage: String
    ): ChatCompletionRequest {
        val completionRequestForm = command.toRequestForm(promptResponse, promptLanguage)
        return openAiMapper.promptAndContentToChatCompletionRequest(completionRequestForm, command.content)
    }

    private fun requestChatCompletion(
        completionRequest: ChatCompletionRequest,
        userId: Long,
        userContent: String
    ): List<InterviewQuestion> {
        try {
            val completionResult = openAiService.requestChatCompletion(completionRequest)
            return openAiMapper.completionToInterviewQuestionResponse(
                completionResult
            )
        } catch (e: Exception) {
            userRequestLogPublisher.publish(
                UserRequestLogCommand(
                    userId = userId,
                    userContent = userContent,
                    RequestOutcome.FAILED
                )
            )
            throw CompletionFailedException()
        }
    }
}
