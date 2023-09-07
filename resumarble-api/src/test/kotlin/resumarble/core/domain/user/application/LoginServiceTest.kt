package resumarble.core.domain.user.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import resumarble.core.domain.user.application.port.out.FindUserPort
import resumarble.core.domain.user.application.service.LoginService
import resumarble.core.global.error.UnidentifiedUserException
import resumarble.core.global.error.UserNotFoundException
import resumarble.core.global.jwt.JwtTokenProvider
import resumarble.core.global.jwt.LoginToken
import resumarble.fixture.UserFixture

class LoginServiceTest : BehaviorSpec() {

    init {
        val findUserPort = mockk<FindUserPort>()
        val jwtTokenProvider = mockk<JwtTokenProvider>()
        val sut = LoginService(findUserPort, jwtTokenProvider)

        given("로그인을 요청할 때") {
            val request = UserFixture.loginUserRequest()
            val loginToken = LoginToken(
                "Bearer access",
                "Bearer refresh"
            )
            `when`("올바른 이메일과 비밀번호를 입력하면") {
                every { findUserPort.findUserByEmail(any()) } returns UserFixture.user()
                every { jwtTokenProvider.createToken(any()) } returns loginToken
                then("로그인이 성공한다.") {
                    val actual = sut.login(request.toCommand())

                    verify(exactly = 1) {
                        findUserPort.findUserByEmail(request.email)
                        jwtTokenProvider.createToken(any())
                    }
                    actual.accessToken shouldBe loginToken.accessToken
                    actual.refreshToken shouldBe loginToken.refreshToken
                }
            }
            clearMocks(findUserPort, jwtTokenProvider)

            `when`("잘못된 이메일을 입력하면") {
                every { findUserPort.findUserByEmail(any()) } throws UserNotFoundException()
                then("로그인 실패 응답을 반환한다.") {
                    shouldThrow<UserNotFoundException> {
                        sut.login(request.toCommand())
                    }
                    verify(exactly = 1) {
                        findUserPort.findUserByEmail(request.email)
                    }
                }
            }
            clearMocks(findUserPort, jwtTokenProvider)
            `when`("잘못된 비밀번호를 입력하면") {
                every { findUserPort.findUserByEmail(any()) } returns UserFixture.user()
                every { jwtTokenProvider.createToken(any()) } throws UnidentifiedUserException()
                then("로그인 실패 응답을 반환한다.") {

                    shouldThrow<UnidentifiedUserException> {
                        sut.login(request.toCommand())
                    }

                    verify(exactly = 1) {
                        findUserPort.findUserByEmail(request.email)
                        jwtTokenProvider.createToken(any())
                    }
                }
            }
        }
    }
}
