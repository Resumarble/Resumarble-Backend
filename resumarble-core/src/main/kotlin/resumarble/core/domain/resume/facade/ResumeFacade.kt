package resumarble.core.domain.resume.facade

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.global.annotation.Facade

@Facade
class ResumeFacade(
    private val promptService: PromptService,
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper,
    private val objectMapper: ObjectMapper
) {

    fun generateInterviewQuestion(command: InterviewQuestionCommand): InterviewQuestionResponse {
        val promptResponse = promptService.getPrompt(PromptType.INTERVIEW_QUESTION)
        val requestForm =
            promptResponse.createRequestForm(command.job, command.career, command.category, command.career, "korean")
        val completionRequest =
            openAiMapper.promptAndContentToChatCompletionRequest(requestForm, promptResponse.content)
        return objectMapper.readValue<InterviewQuestionResponse>(
            openAiService.requestChatCompletion(completionRequest).choices[0].message.content
        )
    }
}
