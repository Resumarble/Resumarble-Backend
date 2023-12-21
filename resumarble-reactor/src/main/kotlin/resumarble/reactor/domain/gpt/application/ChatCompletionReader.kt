package resumarble.reactor.domain.gpt.application

import org.springframework.stereotype.Component
import resumarble.reactor.domain.gpt.adapter.out.ChatCompletionRequest
import resumarble.reactor.domain.gpt.mapper.OpenAiMapper
import resumarble.reactor.domain.interview.application.InterviewQuestionCommand
import resumarble.reactor.domain.interview.application.PredictionResponse

@Component
class ChatCompletionReader(
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper
) {

    suspend fun readChatCompletion(
        command: InterviewQuestionCommand
    ): List<PredictionResponse> {
        val completionRequest = prepareCompletionRequest(command)
        return requestChatCompletionToOpenAi(completionRequest)
    }

    private suspend fun requestChatCompletionToOpenAi(
        completionRequest: ChatCompletionRequest
    ): List<PredictionResponse> {
        val completionResult = openAiService.requestChatCompletion(completionRequest)
        return openAiMapper.completionToInterviewQuestionResponse(completionResult)
    }

    private fun prepareCompletionRequest(
        command: InterviewQuestionCommand
    ): ChatCompletionRequest {
        val completionRequestForm = command.toRequestForm()
        return openAiMapper.promptAndContentToChatCompletionRequest(completionRequestForm, command.content)
    }
}
