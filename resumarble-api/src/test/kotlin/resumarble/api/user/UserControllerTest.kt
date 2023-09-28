package resumarble.api.user

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.print.print
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import resumarble.api.global.advice.GlobalExceptionHandler
import resumarble.core.domain.prediction.application.port.`in`.FindPredictionUseCase
import resumarble.core.domain.prediction.application.port.`in`.PredictionResponse
import resumarble.core.domain.prediction.application.port.`in`.QuestionAndAnswerResponse
import resumarble.core.domain.user.application.DuplicateAccountCommand
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase
import resumarble.core.domain.user.application.service.MyPageResponse
import resumarble.core.domain.user.application.service.UserFacade
import resumarble.core.global.error.DuplicateUserException
import resumarble.fixture.UserFixture

class UserControllerTest : DescribeSpec() {

    init {
        val joinUserUseCase = mockk<JoinUserUseCase>()
        val findPredictionUseCase = mockk<FindPredictionUseCase>()
        val userFacade = UserFacade(findPredictionUseCase)
        val objectMapper = ObjectMapper()
        val sut = MockMvcBuilders.standaloneSetup(UserController(joinUserUseCase, userFacade))
            .setControllerAdvice(GlobalExceptionHandler()).build()

        describe("UserController 회원가입") {
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
                every { joinUserUseCase.join(any()) } throws DuplicateUserException(request.account)
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

        describe("UserController 아이디 중복 확인") {
            val request = UserFixture.duplicateAccountRequest()
            context("아이디 중복 확인 요청시") {
                every { joinUserUseCase.duplicateAccount(any()) } just runs
                it("중복이 아닐 경우 200을 반환한다..") {
                    sut.perform(
                        post("/users/duplicate-account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                        .andDo { print() }
                        .andExpect { status().isOk() }
                    verify(exactly = 1) {
                        joinUserUseCase.duplicateAccount(DuplicateAccountCommand(request.account))
                    }
                }
            }
            clearMocks(joinUserUseCase)
            context("중복인 아이디로 요청시") {
                every { joinUserUseCase.duplicateAccount(any()) } throws DuplicateUserException(request.account)
                it("중복일 경우 409를 반환한다.") {
                    sut.perform(
                        post("/users/duplicate-account")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                        .andDo { print() }
                        .andExpect { status().isConflict() }
                    verify(exactly = 1) {
                        joinUserUseCase.duplicateAccount(DuplicateAccountCommand(request.account))
                    }
                }
            }
        }

        describe("UserController 마이페이지") {
            val userId = 1L
            val response = MyPageResponse(
                listOf(
                    PredictionResponse(
                        userId,
                        questionAndAnswer = listOf(
                            QuestionAndAnswerResponse(
                                question = "질문1",
                                answer = "답변1"
                            ),
                            QuestionAndAnswerResponse(
                                question = "질문2",
                                answer = "답변2"
                            ),
                            QuestionAndAnswerResponse(
                                question = "질문3",
                                answer = "답변3"
                            )
                        )
                    )
                )
            )
        }
    }
}
