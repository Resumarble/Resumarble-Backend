package resumarble.core.domain.resume.facade

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import resumarble.core.domain.gpt.application.ChatCompletionReader
import resumarble.core.domain.prediction.facade.PredictionFacade
import resumarble.core.domain.prediction.mapper.PredictionMapper
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.prompt.domain.PromptType
import resumarble.core.global.annotation.Facade
import resumarble.core.global.ratelimiter.LimitRequestPerTime
import resumarble.core.global.util.loggingStopWatch
import java.util.concurrent.TimeUnit

@Facade
class InterviewQuestionFacade(
    private val promptService: PromptService,
    private val chatCompletionReader: ChatCompletionReader,
    private val predictionFacade: PredictionFacade
) {

    @LimitRequestPerTime(prefix = "generateInterviewQuestions", ttl = 5, count = 1, ttlTimeUnit = TimeUnit.SECONDS)
    suspend fun generateInterviewQuestions(
        userId: Long,
        commands: List<InterviewQuestionCommand>
    ): List<InterviewQuestion> {
        return coroutineScope {
            val deferreds = commands.map { command ->
                async(Dispatchers.IO) {
                    generateInterviewQuestion(command)
                }
            }
            deferreds.awaitAll()
                .flatten()
        }
    }

    fun generateInterviewQuestion(command: InterviewQuestionCommand): List<InterviewQuestion> {
        val promptResponse = promptService.getPrompt(PromptType.INTERVIEW_QUESTION)

        val completionResult = loggingStopWatch { chatCompletionReader.readChatCompletion(command, promptResponse) }

        if (completionResult.isNotEmpty()) {
            predictionFacade.savePrediction(
                PredictionMapper.completionToSavePredictionCommand(
                    command,
                    completionResult
                )
            )
        }
        return completionResult
    }
}
