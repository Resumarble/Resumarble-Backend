package resumarble.reactor.domain.interview.facade

import com.navercorp.fixturemonkey.FixtureMonkeyBuilder
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.minSizeExp
import com.navercorp.fixturemonkey.kotlin.setExp
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import resumarble.reactor.domain.gpt.application.ChatCompletionReader
import resumarble.reactor.domain.interview.application.InterviewQuestionCommand
import resumarble.reactor.domain.interview.application.InterviewQuestionFacade
import resumarble.reactor.domain.interview.application.InterviewQuestionWriter
import resumarble.reactor.domain.interview.application.PredictionResponse

class InterviewQuestionFacadeTest : BehaviorSpec() {

    private val chatCompletionReader: ChatCompletionReader = mockk<ChatCompletionReader>()
    private val interviewQuestionWriter: InterviewQuestionWriter = mockk<InterviewQuestionWriter>()

    private val sut: InterviewQuestionFacade = InterviewQuestionFacade(
        chatCompletionReader,
        interviewQuestionWriter
    )

    private val fixtureMonkey = FixtureMonkeyBuilder().plugin(KotlinPlugin()).build()

    init {
        Given("면접 예상 질문을 요청할 경우") {
            val command = fixtureMonkey.giveMeBuilder<InterviewQuestionCommand>()
                .minSizeExp(InterviewQuestionCommand::userId, 1)
                .setExp(InterviewQuestionCommand::career, "3 years")
                .setExp(InterviewQuestionCommand::job, "Backend Developer")
                .setExp(InterviewQuestionCommand::category, "introduction")
                .sample()
            val commands = listOf(command)

            val predictionResponse = fixtureMonkey.giveMeBuilder<PredictionResponse>()
                .setExp(PredictionResponse::question, "question")
                .setExp(PredictionResponse::bestAnswer, "answer")
                .sample()
            val predictionResponses = listOf(predictionResponse)

            When("정상적으로 처리되면") {
                coEvery { chatCompletionReader.readChatCompletion(any()) } returns predictionResponses
                coEvery { interviewQuestionWriter.save(any()) } just runs
                Then("면접 예상 질문을 생성한다.") {
                    runBlocking {
                        sut.generateInterviewQuestions(commands)
                        coVerify(exactly = 1) {
                            chatCompletionReader.readChatCompletion(any())
                            interviewQuestionWriter.save(any())
                        }
                    }
                }
            }
        }
    }
}
