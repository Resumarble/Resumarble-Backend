package resumarble.core.domain.resume.facade

import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.log.application.UserRequestLogCommand
import resumarble.core.domain.log.application.UserRequestLogService
import resumarble.core.domain.log.constraints.RequestOutcome
import resumarble.core.domain.prediction.facade.PredictionFacade
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.global.annotation.Facade
import resumarble.core.global.util.loggingStopWatch

@Facade
class InterviewQuestionFacade(
    private val promptService: PromptService,
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper,
    private val predictionFacade: PredictionFacade,
    private val userRequestLogService: UserRequestLogService
) {

    fun generateInterviewQuestion(command: InterviewQuestionCommand): InterviewQuestionResponse {
        val promptResponse = promptService.getPrompt(PromptType.INTERVIEW_QUESTION)
        val completionRequest = prepareCompletionRequest(command, promptResponse)
        val completionResult = loggingStopWatch { requestChatCompletion(completionRequest) }
        userRequestLogService.saveUserRequestLog(
            UserRequestLogCommand.from(
                command.userId,
                command.content,
                RequestOutcome.SUCCESS
            )
        )
        // TODO: 책임과 역할 분리하기
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

    private fun requestChatCompletion(completionRequest: ChatCompletionRequest): InterviewQuestionResponse {
        return openAiMapper.completionToInterviewQuestionResponse(
            openAiService.requestChatCompletion(completionRequest)
        )
    }

    companion object {
        private const val PROMPT_LANGUAGE = "korean"
    }
}
