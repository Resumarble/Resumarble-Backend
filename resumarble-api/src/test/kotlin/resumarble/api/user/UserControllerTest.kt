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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import resumarble.api.global.advice.GlobalExceptionHandler
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase
import resumarble.core.domain.user.application.port.`in`.LoginUserUseCase
import resumarble.core.global.error.DuplicateUserException
import resumarble.core.global.error.UnidentifiedUserException
import resumarble.core.global.error.UserNotFoundException
import resumarble.core.global.jwt.LoginToken
import resumarble.fixture.UserFixture

class UserControllerTest : DescribeSpec() {

    init {
        val joinUserUseCase = mockk<JoinUserUseCase>()
        val loginUserUseCase = mockk<LoginUserUseCase>()
        val objectMapper = ObjectMapper()
        val sut = MockMvcBuilders.standaloneSetup(UserController(joinUserUseCase, loginUserUseCase))
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

            context("로그인 요청시") {
                val loginRequest = UserFixture.loginUserRequest()
                val loginToken = LoginToken(
                    "Bearer access",
                    "Bearer refresh"
                )
                every { loginUserUseCase.login(any()) } returns loginToken
                it("로그인이 성공한다.") {
                    sut.perform(
                        post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest))
                    )
                        .andDo { print() }
                        .andExpect {
                            status().isOk()
                            content().json(objectMapper.writeValueAsString(loginToken))
                        }
                }
            }

            context("존재하지 않는 이메일로 로그인 요청시") {
                every { loginUserUseCase.login(any()) } throws UserNotFoundException()
                it("로그인이 실패한다.") {
                    sut.perform(
                        post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                        .andDo { print() }
                        .andExpect { status().isNotFound() }
                }
            }
            context("잘못된 패스워드로 로그인 요청시") {
                every { loginUserUseCase.login(any()) } throws UnidentifiedUserException()
                it("로그인이 실패한다.") {
                    sut.perform(
                        post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                        .andDo { print() }
                        .andExpect { status().isUnauthorized() }
                }
            }
        }
    }
}
