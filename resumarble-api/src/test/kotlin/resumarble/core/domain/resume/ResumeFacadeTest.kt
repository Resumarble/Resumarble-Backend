package resumarble.core.domain.resume

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.resume.facade.ResumeFacade

class ResumeFacadeTest : BehaviorSpec({
    val promptService = mockk<PromptService>()
    val openAiService = mockk<OpenAiService>()
    val openAiMapper = mockk<OpenAiMapper>()
    val sut = ResumeFacade(promptService, openAiService, openAiMapper)

    Given("이력서를 기반으로 면접 예상 질문을 생성하는 경우") {
        val promptResponse = ResumeFixture.promptResponse()
        val completionRequest = ChatCompletionFixture.chatCompletionRequest()
        val completionResponse = ChatCompletionFixture.chatCompletionMessageResponse()
        val response = ResumeFixture.interviewQuestionResponse()

        When("정상적인 요청일 때") {
            every { promptService.getPrompt(any()) } returns promptResponse
            every { openAiMapper.promptAndContentToChatCompletionRequest(any(), any()) } returns completionRequest
            every { openAiService.requestChatCompletion(any()) } returns completionResponse
            every { openAiMapper.completionToInterviewQuestionResponse(any()) } returns response
            Then("면접 예상 질문이 생성된다.") {
                sut.generateInterviewQuestion(ResumeFixture.interviewQuestionCommand())

                verify(exactly = 1) {
                    promptService.getPrompt(any())
                    openAiMapper.promptAndContentToChatCompletionRequest(any(), any())
                    openAiService.requestChatCompletion(any())
                    openAiMapper.completionToInterviewQuestionResponse(any())
                }
            }
        }
    }
})
