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
import resumarble.core.global.util.loggingStopWatch

@Facade
class InterviewQuestionFacade(
    private val promptService: PromptService,
    private val chatCompletionReader: ChatCompletionReader,
    private val predictionFacade: PredictionFacade
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

        val completionResult = loggingStopWatch { chatCompletionReader.readChatCompletion(command, promptResponse) }

        predictionFacade.savePrediction(PredictionMapper.completionToSavePredictionCommand(command, completionResult))

        return completionResult
    }
}
