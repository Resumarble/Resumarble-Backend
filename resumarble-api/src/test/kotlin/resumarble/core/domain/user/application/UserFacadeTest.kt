package resumarble.core.domain.user.application

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.domain.PageRequest
import resumarble.core.domain.prediction.application.port.`in`.FindInterviewQuestionUseCase
import resumarble.core.domain.prediction.application.port.`in`.PredictionResponse
import resumarble.core.domain.prediction.application.port.`in`.QuestionAndAnswerResponse
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
                PredictionResponse(
                    predictionId = 1L,
                    job = "개발자",
                    category = "개발",
                    QuestionAndAnswerResponse(
                        question = "질문",
                        answer = "답변"
                    ),
                    createdDate = LocalDateTime.now()
                ),
                PredictionResponse(
                    predictionId = 2L,
                    job = "개발자",
                    category = "개발",
                    QuestionAndAnswerResponse(
                        question = "질문",
                        answer = "답변"
                    ),
                    createdDate = LocalDateTime.now()
                )
            )
            every { findInterviewQuestionUseCase.getInterviewQuestionByUserId(any(), any()) } returns predictions
            `when`("존재하는 유저인 경우") {
                sut.getMyPredictions(userId, page)
                then("마이페이지가 조회된다.") {
                    verify(exactly = 1) { findInterviewQuestionUseCase.getInterviewQuestionByUserId(userId, page) }
                }
            }
        }
    }
}
