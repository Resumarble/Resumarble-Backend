package resumarble.reactor.domain.interview.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import org.springframework.stereotype.Service
import resumarble.reactor.domain.gpt.application.ChatCompletionReader

@Service
class InterviewQuestionFacade(
    private val chatCompletionReader: ChatCompletionReader
) {
    suspend fun generateInterviewQuestions(
        command: List<InterviewQuestionCommand>
    ): List<InterviewQuestion> {
        return supervisorScope {
            command.map { command ->
                async(Dispatchers.Default) {
                    generateInterviewQuestion(command)
                }
            }.awaitAll().flatten()
        }
    }

    private suspend fun generateInterviewQuestion(command: InterviewQuestionCommand): List<InterviewQuestion> {
        return coroutineScope {
            async(Dispatchers.IO) { chatCompletionReader.readChatCompletion(command) }
        }.await()
    }
}
