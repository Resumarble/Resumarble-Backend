package resumarble.core.domain.user.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import resumarble.core.domain.user.constraints.JwtProvider
import resumarble.core.domain.user.domain.password.Password
import resumarble.core.domain.user.domain.password.sha256Encrypt

class UserTest : StringSpec() {

    init {
        "회원을 생성한다." {
            val account = "test"
            val password = "password"
            val user = User(
                account = account,
                password = Password(password),
                provider = JwtProvider.RESUMARBLE
            )

            user.account shouldBe account
            user.password shouldBe Password(password)
        }

        "패스워드가 암호화된다." {
            val password = "password"
            val encryptedPassword = Password(password)

            encryptedPassword.value shouldBe sha256Encrypt(password)
        }
    }
}
