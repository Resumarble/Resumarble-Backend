package resumarble.core.domain.resume

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.verify
import resumarble.core.domain.gpt.application.ChatCompletionReader
import resumarble.core.domain.prediction.facade.PredictionFacade
import resumarble.core.domain.prediction.mapper.PredictionMapper
import resumarble.core.domain.prompt.application.PromptService
import resumarble.core.domain.resume.facade.InterviewQuestionFacade
import resumarble.core.global.error.CompletionFailedException

class InterviewQuestionFacadeTest : BehaviorSpec() {

    init {
        mockkObject(PredictionMapper)
        val promptService = mockk<PromptService>()
        val chatCompletionReader = mockk<ChatCompletionReader>()
        val predictionFacade = mockk<PredictionFacade>()
        val sut =
            InterviewQuestionFacade(promptService, chatCompletionReader, predictionFacade)

        afterEach {
            clearAllMocks()
        }

        Given("면접 예상 질문을 요청할 경우") {
            val promptResponse = ResumeFixture.promptResponse()
            val completionResult = ResumeFixture.interviewQuestionResponse()

            When("정상적으로 처리되면") {
                every { promptService.getPrompt(any()) } returns promptResponse
                every { chatCompletionReader.readChatCompletion(any(), any()) } returns completionResult
                every {
                    PredictionMapper.completionToSavePredictionCommand(
                        any(),
                        any()
                    )
                } returns ResumeFixture.savePredictionCommand()
                every { predictionFacade.savePrediction(any()) } just runs

                Then("면접 예상 질문을 생성한다.") {
                    sut.generateInterviewQuestion(ResumeFixture.interviewQuestionCommand())
                    verify(exactly = 1) {
                        promptService.getPrompt(any())
                        chatCompletionReader.readChatCompletion(any(), any())
                        predictionFacade.savePrediction(any())
                    }
                }
            }
        }

        Given("면접 예상 질문을 요청할 때") {
            val promptResponse = ResumeFixture.promptResponse()

            When("내용이 비었으면") {
                every { promptService.getPrompt(any()) } returns promptResponse
                every { chatCompletionReader.readChatCompletion(any(), any()) } throws CompletionFailedException()

                Then("예외가 발생한다.") {
                    shouldThrow<CompletionFailedException> {
                        sut.generateInterviewQuestion(ResumeFixture.interviewQuestionCommand())
                    }
                    verify(exactly = 1) {
                        promptService.getPrompt(any())
                        chatCompletionReader.readChatCompletion(any(), any())
                    }
                }
            }
        }
    }
}
