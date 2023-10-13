package resumarble.core.domain.gpt.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import resumarble.core.domain.resume.ChatCompletionFixture
import resumarble.core.global.error.CompletionFailedException

class OpenAiServiceTest : StringSpec() {

    private val sut = mockk<OpenAiService>()

    init {
        "ChatCompletion을 요청하면 성공한다." {
            // given
            val response = ChatCompletionFixture.chatCompletionMessageResponse()
            // when
            every { sut.requestChatCompletion(any()) } returns response

            val actual = sut.requestChatCompletion(ChatCompletionFixture.chatCompletionRequest())

            // then
            actual.questionAndAnswer shouldBe response.questionAndAnswer
            actual shouldBeSameInstanceAs response
        }

        "ChatCompletion 요청이 실패하면 예외가 발생한다." {
            // when
            every { sut.requestChatCompletion(any()) } throws CompletionFailedException()

            // then
            shouldThrow<CompletionFailedException> {
                sut.requestChatCompletion(ChatCompletionFixture.chatCompletionRequest())
            }
        }
    }
}
