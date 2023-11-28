package resumarble.reactor.domain.interview.application

import org.springframework.stereotype.Service
import resumarble.reactor.domain.gpt.application.ChatCompletionReader

@Service
class InterviewQuestionFacade(
    private val chatCompletionReader: ChatCompletionReader
) {
    suspend fun generateInterviewQuestion(
        command: List<InterviewQuestionCommand>
    ): List<InterviewQuestion> = chatCompletionReader.readChatCompletion(command[0])
}
