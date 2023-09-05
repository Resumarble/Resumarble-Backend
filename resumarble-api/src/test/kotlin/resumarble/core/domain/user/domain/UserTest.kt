package resumarble.core.domain.user.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class UserTest : StringSpec() {

    init {
        "회원을 생성한다." {
            val email = "test@gmail.com"
            val password = "password"
            val nickname = "nickname"
            val user = User(
                email = email,
                password = password,
                nickname = nickname
            )

            user.email shouldBe email
            user.password shouldBe password
            user.nickname shouldBe nickname
        }
    }
}
