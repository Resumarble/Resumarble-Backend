package resumarble.api.user

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.print.print
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import resumarble.api.global.advice.GlobalExceptionHandler
import resumarble.core.domain.user.application.port.`in`.LoginUserUseCase
import resumarble.core.domain.user.application.port.`in`.LogoutUserUseCase
import resumarble.core.global.error.UnidentifiedUserException
import resumarble.core.global.error.UserNotFoundException
import resumarble.core.global.jwt.LoginToken
import resumarble.fixture.UserFixture

class LoginControllerTest : DescribeSpec() {

    init {
        val loginUserUseCase = mockk<LoginUserUseCase>()
        val logoutUserUseCase = mockk<LogoutUserUseCase>()
        val objectMapper = ObjectMapper()
        val sut = MockMvcBuilders.standaloneSetup(LoginController(loginUserUseCase, logoutUserUseCase))
            .setControllerAdvice(GlobalExceptionHandler()).build()

        describe("LoginController") {
            val loginRequest = UserFixture.loginUserRequest()
            context("로그인 요청시") {
                val loginToken = LoginToken(
                    "Bearer access",
                    "Bearer refresh"
                )
                every { loginUserUseCase.login(any()) } returns loginToken
                it("로그인이 성공한다.") {
                    sut.perform(
                        MockMvcRequestBuilders.post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest))
                    )
                        .andDo { print() }
                        .andExpect {
                            MockMvcResultMatchers.status().isOk()
                            MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(loginToken))
                        }
                }
            }
            context("존재하지 않는 이메일로 로그인 요청시") {
                every { loginUserUseCase.login(any()) } throws UserNotFoundException()
                it("로그인이 실패한다.") {
                    sut.perform(
                        MockMvcRequestBuilders.post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest))
                    )
                        .andDo { print() }
                        .andExpect { MockMvcResultMatchers.status().isNotFound() }
                }
            }
            context("잘못된 패스워드로 로그인 요청시") {
                every { loginUserUseCase.login(any()) } throws UnidentifiedUserException()
                it("로그인이 실패한다.") {
                    sut.perform(
                        MockMvcRequestBuilders.post("/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest))
                    )
                        .andDo { print() }
                        .andExpect { MockMvcResultMatchers.status().isUnauthorized() }
                }
            }

            context("로그아웃 요청시") {
                it("로그아웃이 성공한다.") {
                    every { logoutUserUseCase.logout(any()) } just runs
                    sut.perform(
                        MockMvcRequestBuilders.post("/users/logout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer access")
                            .content(objectMapper.writeValueAsString(loginRequest))
                    )
                        .andDo { print() }
                        .andExpect { MockMvcResultMatchers.status().isOk() }
                }
                it("로그아웃이 실패한다.") {
                    every { logoutUserUseCase.logout(any()) } throws UserNotFoundException()
                    sut.perform(
                        MockMvcRequestBuilders.post("/users/logout")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer access")
                            .content(objectMapper.writeValueAsString(loginRequest))
                    )
                        .andDo { print() }
                        .andExpect { MockMvcResultMatchers.status().isBadRequest() }
                }
            }
        }
    }
}
