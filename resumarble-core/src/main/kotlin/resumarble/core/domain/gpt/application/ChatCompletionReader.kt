package resumarble.core.domain.gpt.application

import org.springframework.stereotype.Component
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.mapper.OpenAiMapper
import resumarble.core.domain.log.application.UserRequestLogPublisher
import resumarble.core.domain.log.constraints.RequestOutcome
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.resume.facade.InterviewQuestion
import resumarble.core.domain.resume.facade.InterviewQuestionCommand

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
        return requestChatCompletionToOpenAi(completionRequest, command.userId, command.content)
    }

    private fun prepareCompletionRequest(
        command: InterviewQuestionCommand,
        promptResponse: PromptResponse,
        promptLanguage: String
    ): ChatCompletionRequest {
        val completionRequestForm = command.toRequestForm(promptResponse, promptLanguage)
        return openAiMapper.promptAndContentToChatCompletionRequest(completionRequestForm, command.content)
    }

    private fun requestChatCompletionToOpenAi(
        completionRequest: ChatCompletionRequest,
        userId: Long,
        userContent: String
    ): List<InterviewQuestion> {
        var outcome: RequestOutcome
        var result: List<InterviewQuestion>

        try {
            val completionResult = openAiService.requestChatCompletion(completionRequest)
            result = openAiMapper.completionToInterviewQuestionResponse(completionResult)
            outcome = RequestOutcome.SUCCESS
        } catch (e: Exception) {
            result = emptyList()
            outcome = RequestOutcome.FAILED
        }

        userRequestLogPublisher.publish(
            UserRequestLogEvent(
                userId = userId,
                userContent = userContent,
                requestOutcome = outcome
            )
        )

        return result
    }
}
