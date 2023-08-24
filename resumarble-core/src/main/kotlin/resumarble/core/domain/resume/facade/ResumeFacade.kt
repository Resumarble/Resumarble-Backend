package resumarble.core.domain.resume.facade

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.global.annotation.Facade
import resumarble.core.global.util.LoggerUtil.logger
import java.time.Duration
import java.time.LocalDateTime

@Facade
class ResumeFacade(
    private val promptService: PromptService,
    private val openAiService: OpenAiService,
    private val openAiMapper: OpenAiMapper,
    private val objectMapper: ObjectMapper
) {

    val logger = logger()

    fun generateInterviewQuestion(command: InterviewQuestionCommand): InterviewQuestionResponse {
        return loggingStopWatch {
            val promptResponse = promptService.getPrompt(PromptType.INTERVIEW_QUESTION)
            val requestForm =
                promptResponse.createRequestForm(
                    command.job,
                    command.career,
                    command.category,
                    command.career,
                    "korean"
                )
            val completionRequest =
                openAiMapper.promptAndContentToChatCompletionRequest(requestForm, promptResponse.content)
            objectMapper.readValue<InterviewQuestionResponse>(
                openAiService.requestChatCompletion(completionRequest).choices[0].message.content
            )
        }
    }

    fun <T> loggingStopWatch(function: () -> T): T {
        val startAt = LocalDateTime.now()
        logger.info("Start at $startAt")

        val result = function.invoke()

        val endAt = LocalDateTime.now()

        logger.info("End at $endAt")
        logger.info("Logic Duration : ${Duration.between(startAt, endAt).toMillis()}ms")

        return result
    }
}
