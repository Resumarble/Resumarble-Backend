package resumarble.core.domain.resume.facade

import jakarta.annotation.PreDestroy
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.prediction.facade.PredictionFacade
import resumarble.core.domain.prompt.application.PromptResponse
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.global.annotation.Facade
import resumarble.core.global.util.loggingErrorMarking
import resumarble.core.global.util.loggingStopWatch

@Facade
class InterviewQuestionFacade(
    private val promptService: PromptService,
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper,
    private val predictionFacade: PredictionFacade
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun generateInterviewQuestion(command: InterviewQuestionCommand): InterviewQuestionResponse {
        val completionResult = loggingStopWatch {
            val promptResponse = promptService.getPrompt(PromptType.INTERVIEW_QUESTION)
            val completionRequest = prepareCompletionRequest(command, promptResponse)
            requestChatCompletion(completionRequest)
        }

        asyncSavePrediction(command, completionResult)

        return completionResult
    }

    private fun asyncSavePrediction(command: InterviewQuestionCommand, result: InterviewQuestionResponse) {
        scope.launch(handler) {
            predictionFacade.savePrediction(
                command.job,
                command.category,
                result
            )
        }
    }

    private fun prepareCompletionRequest(
        command: InterviewQuestionCommand,
        promptResponse: PromptResponse
    ): ChatCompletionRequest {
        val completionRequestForm = command.toRequestForm(promptResponse, PROMPT_LANGUAGE)
        return openAiMapper.promptAndContentToChatCompletionRequest(completionRequestForm, promptResponse.content)
    }

    private fun requestChatCompletion(completionRequest: ChatCompletionRequest): InterviewQuestionResponse {
        return openAiMapper.completionToInterviewQuestionResponse(
            openAiService.requestChatCompletion(completionRequest)
        )
    }

    @PreDestroy
    fun cleanUp() {
        scope.cancel()
    }

    val handler = CoroutineExceptionHandler { _, throwable ->
        loggingErrorMarking {
            SAVE_PREDICTION_ERROR_MESSAGE + "${throwable.message}"
        }
    }

    companion object {
        private const val PROMPT_LANGUAGE = "korean"
        private const val SAVE_PREDICTION_ERROR_MESSAGE = "면접 예상 질문 저장이 실패했습니다. 예외 메시지: "
    }
}
