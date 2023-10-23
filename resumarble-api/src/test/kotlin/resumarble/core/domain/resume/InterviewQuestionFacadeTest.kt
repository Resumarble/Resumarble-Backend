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
import resumarble.core.domain.log.application.UserRequestLogWriter
import resumarble.core.domain.prediction.facade.PredictionFacade
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.resume.facade.InterviewQuestionFacade
import resumarble.core.global.error.CompletionFailedException

class InterviewQuestionFacadeTest : BehaviorSpec() {

    init {
        val promptService = mockk<PromptService>()
        val openAiService = mockk<OpenAiService>()
        val openAiMapper = mockk<OpenAiMapper>()
        val userRequestLogWriter = mockk<UserRequestLogWriter>()
        val predictionFacade = mockk<PredictionFacade>()
        val sut =
            InterviewQuestionFacade(promptService, openAiService, openAiMapper, predictionFacade, userRequestLogWriter)

        afterEach {
            clearAllMocks()
        }

        Given("면접 예상 질문을 요청할 경우") {
            val promptResponse = ResumeFixture.promptResponse()
            val completionRequest = ChatCompletionFixture.chatCompletionRequest()
            val chatCompletionResponse = ChatCompletionFixture.chatCompletionMessageResponse()
            val completionResult = ResumeFixture.interviewQuestionResponse()
            val predictionCommand = PredictionFixture.savePredictionCommand()

            When("정상적으로 처리되면") {
                every { promptService.getPrompt(any()) } returns promptResponse
                every {
                    openAiMapper.promptAndContentToChatCompletionRequest(
                        any(),
                        any()
                    )
                } returns completionRequest
                every {
                    openAiMapper.completionToInterviewQuestionResponse(
                        any()
                    )
                } returns completionResult

                every { openAiMapper.completionToSavePredictionCommand(any(), any()) } returns predictionCommand
            }
            every { openAiService.requestChatCompletion(any()) } returns chatCompletionResponse
            every { userRequestLogWriter.saveUserRequestLog(any()) } just runs
            every { predictionFacade.savePrediction(any()) } just runs

            Then("면접 예상 질문을 생성한다.") {
                sut.generateInterviewQuestion(ResumeFixture.interviewQuestionCommand())
                verify(exactly = 1) {
                    promptService.getPrompt(any())
                    openAiMapper.promptAndContentToChatCompletionRequest(any(), any())
                    openAiService.requestChatCompletion(any())
                    userRequestLogWriter.saveUserRequestLog(any())
                    predictionFacade.savePrediction(any())
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
                every { openAiService.requestChatCompletion(any()) } throws CompletionFailedException(
                    userId = 1L,
                    userContent = "request failed"
                )

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
