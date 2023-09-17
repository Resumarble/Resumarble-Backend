package resumarble.core.domain.resume.facade

import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.prediction.facade.PredictionFacade
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.global.annotation.Facade

@Facade
class InterviewQuestionFacade(
    private val promptService: PromptService,
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper,
    private val predictionFacade: PredictionFacade
) {

    fun generateInterviewQuestion(command: InterviewQuestionCommand): InterviewQuestionResponse {
        val promptResponse = promptService.getPrompt(PromptType.INTERVIEW_QUESTION)
        val completionRequestForm = command.toRequestForm(promptResponse, PROMPT_LANGUAGE)

        val completionRequest =
            openAiMapper.promptAndContentToChatCompletionRequest(completionRequestForm, promptResponse.content)

        val completionResult = openAiMapper.completionToInterviewQuestionResponse(
            openAiService.requestChatCompletion(completionRequest)
        )

        predictionFacade.savePrediction(
            command.job,
            command.category,
            completionResult
        )

        return completionResult
    }

    companion object {
        private const val PROMPT_LANGUAGE = "korean"
    }
}
