package resumarble.core.domain.user.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import resumarble.core.domain.user.application.port.LogoutUserCommand
import resumarble.core.domain.user.application.port.out.FindUserPort
import resumarble.core.domain.user.application.service.LoginService
import resumarble.core.global.error.UnidentifiedUserException
import resumarble.core.global.error.UserNotFoundException
import resumarble.core.global.jwt.JwtTokenProvider
import resumarble.core.global.jwt.JwtVerifier
import resumarble.core.global.jwt.LoginToken
import resumarble.fixture.UserFixture

class LoginServiceTest : BehaviorSpec() {

    init {
        val findUserPort = mockk<FindUserPort>()
        val jwtTokenProvider = mockk<JwtTokenProvider>()
        val jwtVerifier = mockk<JwtVerifier>()
        val sut = LoginService(findUserPort, jwtTokenProvider, jwtVerifier)

        given("로그인을 요청할 때") {
            val request = UserFixture.loginUserRequest()
            val loginToken = LoginToken(
                "Bearer access",
                "Bearer refresh"
            )
            `when`("올바른 이메일과 비밀번호를 입력하면") {
                every { findUserPort.findUserByAccount(any()) } returns UserFixture.user()
                every { jwtTokenProvider.createToken(any()) } returns loginToken
                then("로그인이 성공한다.") {
                    val actual = sut.login(request.toCommand())

                    verify(exactly = 1) {
                        findUserPort.findUserByAccount(request.account)
                        jwtTokenProvider.createToken(any())
                    }
                    actual.accessToken shouldBe loginToken.accessToken
                    actual.refreshToken shouldBe loginToken.refreshToken
                }
            }
            clearMocks(findUserPort, jwtTokenProvider)

            `when`("잘못된 이메일을 입력하면") {
                every { findUserPort.findUserByAccount(any()) } throws UserNotFoundException()
                then("로그인 실패 응답을 반환한다.") {
                    shouldThrow<UserNotFoundException> {
                        sut.login(request.toCommand())
                    }
                    verify(exactly = 1) {
                        findUserPort.findUserByAccount(request.account)
                    }
                }
            }
            clearMocks(findUserPort, jwtTokenProvider)
            `when`("잘못된 비밀번호를 입력하면") {
                every { findUserPort.findUserByAccount(any()) } returns UserFixture.user()
                every { jwtTokenProvider.createToken(any()) } throws UnidentifiedUserException()
                then("로그인 실패 응답을 반환한다.") {

                    shouldThrow<UnidentifiedUserException> {
                        sut.login(request.toCommand())
                    }

                    verify(exactly = 1) {
                        findUserPort.findUserByAccount(request.account)
                        jwtTokenProvider.createToken(any())
                    }
                }
            }
        }

        given("로그아웃을 요청할 때") {
            val command = LogoutUserCommand.of(1L)
            val user = UserFixture.user()
            `when`("로그인 중인 유저일 경우") {
                every { findUserPort.findUserById(any()) } returns user
                every { jwtVerifier.expireRefreshToken(any()) } just runs
                then("로그아웃이 성공한다.") {
                    sut.logout(command)
                    verify(exactly = 1) {
                        findUserPort.findUserById(command.userId)
                        jwtVerifier.expireRefreshToken(user.account)
                    }
                }
            }
            clearMocks(findUserPort, jwtVerifier)
            `when`("로그인 중이 아닌 유저일 경우") {
                every { findUserPort.findUserById(any()) } throws UserNotFoundException()
                then("로그아웃이 실패한다.") {
                    shouldThrow<UserNotFoundException> {
                        sut.logout(command)
                    }
                    verify(exactly = 1) {
                        findUserPort.findUserById(command.userId)
                    }
                }
            }
        }
    }
}
