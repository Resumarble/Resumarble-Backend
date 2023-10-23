package resumarble.core.domain.gpt.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class OpenAiMapperTest : StringSpec() {

    init {

        val sut = OpenAiMapper(ObjectMapper())
        "사용자 이력서 내용과 프롬프트로 ChatCompletionRequest를 생성한다." {
            // given
            val prompt = "prompt"
            val content = "content"

            // when
            val actual = sut.promptAndContentToChatCompletionRequest(prompt, content)

            // then
            actual.messages.size shouldBe 2
            actual.messages[0].role shouldBe "system"
            actual.messages[0].content shouldBe prompt
            actual.messages[1].role shouldBe "user"
            actual.messages[1].content shouldBe content
        }
    }
}
