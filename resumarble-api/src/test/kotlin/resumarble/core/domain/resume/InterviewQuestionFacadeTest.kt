package resumarble.core.domain.resume

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.gpt.application.OpenAiService
import resumarble.core.domain.prediction.facade.PredictionFacade
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.resume.facade.InterviewQuestionFacade
import resumarble.core.global.error.CompletionFailedException

class InterviewQuestionFacadeTest : BehaviorSpec() {

    init {
        val promptService = mockk<PromptService>()
        val openAiService = mockk<OpenAiService>()
        val openAiMapper = mockk<OpenAiMapper>()
        val predictionFacade = mockk<PredictionFacade>()
        val sut = InterviewQuestionFacade(promptService, openAiService, openAiMapper, predictionFacade)

        afterEach {
            clearAllMocks()
        }

        Given("이력서를 기반으로 면접 예상 질문을 생성하는 경우") {
            val promptResponse = ResumeFixture.promptResponse()
            val completionRequest = ChatCompletionFixture.chatCompletionRequest()
            val completionResponse = ChatCompletionFixture.chatCompletionMessageResponse()
            val response = ResumeFixture.interviewQuestionResponse()
            val savePredictionCommand = ResumeFixture.savePredictionCommand()

            When("정상적인 요청일 때") {
                every { promptService.getPrompt(any()) } returns promptResponse
                every {
                    openAiMapper.promptAndContentToChatCompletionRequest(
                        any(),
                        any()
                    )
                } returns completionRequest
                every { openAiService.requestChatCompletion(any()) } returns completionResponse
                every { openAiMapper.completionToInterviewQuestionResponse(any()) } returns response
                every { openAiMapper.completionToSavePredictionCommand(any(), any()) } returns savePredictionCommand
                every { predictionFacade.savePrediction(any()) } just runs
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

        Given("면접 예상 질문을 요청할 때") {
            val promptResponse = ResumeFixture.promptResponse()
            val completionRequest = ChatCompletionFixture.chatCompletionRequest()

            When("내용이 비었으면") {
                every { promptService.getPrompt(any()) } returns promptResponse
                every {
                    openAiMapper.promptAndContentToChatCompletionRequest(
                        any(),
                        any()
                    )
                } returns completionRequest
                every { openAiService.requestChatCompletion(any()) } throws CompletionFailedException()

                Then("예외가 발생한다.") {
                    shouldThrow<CompletionFailedException> {
                        sut.generateInterviewQuestion(ResumeFixture.interviewQuestionCommand())
                    }
                    verify(exactly = 1) {
                        promptService.getPrompt(any())
                        openAiMapper.promptAndContentToChatCompletionRequest(any(), any())
                        openAiService.requestChatCompletion(any())
                    }
                }
            }
        }
    }
}
