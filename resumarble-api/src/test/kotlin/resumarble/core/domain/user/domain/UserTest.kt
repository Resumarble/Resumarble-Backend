package resumarble.core.domain.user.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import resumarble.core.domain.user.domain.password.Password

class UserTest : StringSpec() {

    init {
        "회원을 생성한다." {
            val email = "test@gmail.com"
            val password = "password"
            val nickname = "nickname"
            val user = User(
                email = email,
                password = Password(password),
                nickname = nickname
            )

            user.email shouldBe email
            user.password shouldBe Password(password)
            user.nickname shouldBe nickname
        }
    }
}
