package resumarble.core.domain.gpt

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.QuestionAndAnswer
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.domain.prediction.facade.SavePredictionCommand
import resumarble.core.domain.resume.facade.InterviewQuestion
import resumarble.core.domain.resume.facade.InterviewQuestionCommand
import resumarble.core.domain.resume.facade.InterviewQuestionResponse
import resumarble.core.global.annotation.Mapper

@Mapper
class OpenAiMapper(
    private val objectMapper: ObjectMapper
) {
    fun promptAndContentToChatCompletionRequest(
        prompt: String,
        content: String
    ): ChatCompletionRequest {
        return ChatCompletionRequest(
            messages = listOf(
                ChatCompletionMessage(
                    role = OpenAiRole.SYSTEM.value,
                    content = prompt
                ),
                ChatCompletionMessage(
                    role = OpenAiRole.USER.value,
                    content = content
                )
            )
        )
    }

    fun completionToInterviewQuestionResponse(completion: ChatCompletionMessageResponse): InterviewQuestionResponse {
        return objectMapper.readValue<InterviewQuestionResponse>(
            completion.questionAndAnswer
        )
    }

    fun completionToSavePredictionCommand(
        command: InterviewQuestionCommand,
        response: InterviewQuestionResponse
    ): SavePredictionCommand {
        return SavePredictionCommand(
            userId = 1L,
            job = Job.fromJobTitleEn(command.job),
            category = Category.fromValue(command.category),
            questionAndAnswerList = convertToQuestionAndAnswer(response.interviews)
        )
    }

    private fun convertToQuestionAndAnswer(interviews: List<InterviewQuestion>): List<QuestionAndAnswer> {
        return interviews.map {
            QuestionAndAnswer(
                question = Question(it.question),
                answer = Answer(it.bestAnswer)
            )
        }
    }
}
