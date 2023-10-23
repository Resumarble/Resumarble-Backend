package resumarble.core.domain.resume.facade

import feign.FeignException.FeignClientException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.log.application.UserRequestLogWriter
import resumarble.core.domain.prediction.facade.PredictionFacade
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.global.annotation.Facade
import resumarble.core.global.error.CompletionFailedException
import resumarble.core.global.util.logger
import resumarble.core.global.util.loggingStopWatch

@Facade
class InterviewQuestionFacade(
    private val promptService: PromptService,
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper,
    private val predictionFacade: PredictionFacade,
    private val userRequestLogWriter: UserRequestLogWriter
) {
    suspend fun generateInterviewQuestions(commands: List<InterviewQuestionCommand>): List<InterviewQuestion> {
        return coroutineScope {
            val deferreds = commands.map { command ->
                async(Dispatchers.Default) {
                    generateInterviewQuestion(command)
                }
            }
            deferreds.awaitAll()
                .flatten()
        }
    }

    fun generateInterviewQuestion(command: InterviewQuestionCommand): List<InterviewQuestion> {
        val promptResponse = promptService.getPrompt(PromptType.INTERVIEW_QUESTION)
        val completionRequest = prepareCompletionRequest(command, promptResponse)

        val completionResult =
            loggingStopWatch { requestChatCompletion(completionRequest, command.userId, command.content) }

        userRequestLogWriter.saveUserRequestLog(command.toSaveLogCommand())

        predictionFacade.savePrediction(openAiMapper.completionToSavePredictionCommand(command, completionResult))

        return completionResult
    }

    private fun prepareCompletionRequest(
        command: InterviewQuestionCommand,
        promptResponse: PromptResponse
    ): ChatCompletionRequest {
        val completionRequestForm = command.toRequestForm(promptResponse, PROMPT_LANGUAGE)
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
        } catch (e: FeignClientException) {
            logger.error(e.message, e)
            throw CompletionFailedException(
                userId = userId,
                userContent = userContent
            )
        }
    }

    companion object {
        private const val PROMPT_LANGUAGE = "korean"
    }
}
