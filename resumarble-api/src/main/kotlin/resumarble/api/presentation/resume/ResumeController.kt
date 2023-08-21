package resumarble.api.presentation.resume

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.core.domain.gpt.ChatCompletionsMessage
import resumarble.core.domain.gpt.ChatCompletionsRequest
import resumarble.core.domain.gpt.OpenAiRole
import resumarble.core.domain.gpt.application.OpenAiService

@RestController
@RequestMapping("/resumes")
class ResumeController(
    private val openAiService: OpenAiService
) {

    @PostMapping
    fun createResume(): String {
        val response = openAiService.requestOpenAiChatCompletion(
            ChatCompletionsRequest(
                messages =
                mutableListOf(
                    ChatCompletionsMessage(
                        role = OpenAiRole.SYSTEM.value,
                        content = "Hello? What is Your name?"
                    )
                )
            )
        )
        return response.choices[0].message.content
    }
}
