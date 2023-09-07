package resumarble.api.user

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.print.print
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import resumarble.api.advice.GlobalExceptionHandler
import resumarble.api.presentation.user.UserController
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase
import resumarble.core.global.error.DuplicateUserException
import resumarble.fixture.UserFixture

class UserControllerTest : DescribeSpec() {

    init {
        val joinUserUseCase = mockk<JoinUserUseCase>()
        val objectMapper = ObjectMapper()
        val sut = MockMvcBuilders.standaloneSetup(UserController(joinUserUseCase))
            .setControllerAdvice(GlobalExceptionHandler()).build()

        describe("UserController") {
            val request = UserFixture.joinUserRequest()
            context("회원가입 요청시") {
                every { joinUserUseCase.join(any()) } just runs
                it("회원가입이 성공한다.") {
                    sut.perform(
                        post("/users/join")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                        .andDo { print() }
                        .andExpect { status().isOk() }
                }
            }
            context("중복 가입인 경우") {
                every { joinUserUseCase.join(any()) } throws DuplicateUserException(request.email)
                it("중복 가입 예외가 발생한다.") {
                    sut.perform(
                        post("/users/join")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                        .andDo { print() }
                        .andExpect { status().isConflict() }
                }
            }
        }
    }
}
