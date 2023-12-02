package resumarble.reactor.domain.interview.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import resumarble.reactor.domain.gpt.application.ChatCompletionReader
import resumarble.reactor.domain.interview.domain.Category
import resumarble.reactor.global.annotation.Facade

@Facade
class InterviewQuestionFacade(
    private val chatCompletionReader: ChatCompletionReader,
    private val interviewQuestionWriter: InterviewQuestionWriter
) {
    suspend fun generateInterviewQuestions(
        command: List<InterviewQuestionCommand>
    ): List<PredictionResponse> {
        return supervisorScope {
            command.map { command ->
                async(Dispatchers.Default) {
                    generateInterviewQuestion(command)
                }
            }.awaitAll().flatten()
        }
    }

    private suspend fun generateInterviewQuestion(command: InterviewQuestionCommand): List<PredictionResponse> {
        return coroutineScope {
            val completionResult = async(Dispatchers.IO) {
                chatCompletionReader.readChatCompletion(command)
            }

            val interviewQuestionCommands = completionResult.await().map {
                it.toCommand(command.userId, Category.fromValue(command.category))
            }

            launch(Dispatchers.IO) {
                interviewQuestionWriter.save(interviewQuestionCommands)
            }

            completionResult.await()
        }
    }
}
