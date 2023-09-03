package resumarble.core.domain.resume.facade

import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.global.annotation.Facade
import resumarble.core.global.util.loggingStopWatch

@Facade
class InterviewQuestionFacade(
    private val promptService: PromptService,
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper
) {

    fun generateInterviewQuestion(command: InterviewQuestionCommand): InterviewQuestionResponse {
        return loggingStopWatch {
            val promptResponse = promptService.getPrompt(PromptType.INTERVIEW_QUESTION)
            val completionRequestForm = command.toRequestForm(promptResponse, PROMPT_LANGUAGE)

            val completionRequest =
                openAiMapper.promptAndContentToChatCompletionRequest(completionRequestForm, promptResponse.content)

            openAiMapper.completionToInterviewQuestionResponse(
                openAiService.requestChatCompletion(completionRequest)
            )
        }
    }

    companion object {
        private const val PROMPT_LANGUAGE = "korean"
    }
}
