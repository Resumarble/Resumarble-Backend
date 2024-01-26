package resumarble.core.domain.feedback.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.gpt.application.ChatCompletionReader

@Service
@Transactional
class FeedbackService(
    private val chatCompletionReader: ChatCompletionReader
) {

    suspend fun requestFeedbacks(commands: List<FeedbackCommand>): List<FeedbackResponse> {
        return supervisorScope {
            val deffereds = commands.map { command ->
                async(Dispatchers.IO) {
                    requestFeedback(command)
                }
            }
            deffereds.awaitAll()
                .flatten()
        }
    }

    suspend fun requestFeedback(command: FeedbackCommand): List<FeedbackResponse> {
        val prompt = FeedbackPrompt.createFeedbackPrompt(command)
        return chatCompletionReader.readChatCompletionFeedback(command, prompt)
    }
}
