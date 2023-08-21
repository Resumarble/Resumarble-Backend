package resumarble.api.presentation.resume

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.core.domain.gpt.ChatCompletionMessage
import resumarble.core.domain.gpt.ChatCompletionRequest
import resumarble.core.domain.gpt.OpenAiRole
import resumarble.core.domain.gpt.application.OpenAiService

@RestController
@RequestMapping("/resumes")
class ResumeController(
    private val openAiService: OpenAiService
) {

    @PostMapping("/interview-questions")
    fun createResume(
        @RequestBody request: InterviewQuestionRequest
    ): String {
        val response = openAiService.requestOpenAiChatCompletion(
            ChatCompletionRequest(
                messages =
                mutableListOf(
                    ChatCompletionMessage(
                        role = OpenAiRole.SYSTEM.value,
                        content = "Hello? What is Your name?"
                    )
                )
            )
        )
        return response.choices[0].message.content
    }
}
