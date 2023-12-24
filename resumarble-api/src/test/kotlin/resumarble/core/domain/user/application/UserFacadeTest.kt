package resumarble.core.domain.user.application

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.domain.PageRequest
import resumarble.core.domain.prediction.application.port.`in`.FindInterviewQuestionResponse
import resumarble.core.domain.prediction.application.port.`in`.FindInterviewQuestionUseCase
import resumarble.core.domain.user.application.service.UserFacade
import java.time.LocalDateTime

class UserFacadeTest : BehaviorSpec() {
    init {
        val page = PageRequest.of(0, 10)
        val findInterviewQuestionUseCase = mockk<FindInterviewQuestionUseCase>()
        val sut = UserFacade(findInterviewQuestionUseCase)
        given("마이페이지를 조회할 때") {
            val userId = 1L
            val predictions = listOf(
                FindInterviewQuestionResponse(
                    interviewQuestionId = 1L,
                    job = "개발자",
                    category = "개발",
                    question = "질문",
                    answer = "답변",
                    createdDate = LocalDateTime.now()
                ),
                FindInterviewQuestionResponse(
                    interviewQuestionId = 2L,
                    job = "개발자",
                    category = "개발",
                    question = "질문",
                    answer = "답변",
                    createdDate = LocalDateTime.now()
                )
            )
            every { findInterviewQuestionUseCase.getInterviewQuestionByUserId(any(), any()) } returns Pair(
                predictions,
                false
            )
            `when`("존재하는 유저인 경우") {
                sut.getMyInterviewQuestionList(userId, page)
                then("마이페이지가 조회된다.") {
                    verify(exactly = 1) { findInterviewQuestionUseCase.getInterviewQuestionByUserId(userId, page) }
                }
            }
        }
    }
}
