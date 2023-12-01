package resumarble.reactor.domain.interview.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.reactor.domain.interview.application.InterviewQuestion
import resumarble.reactor.domain.interview.application.InterviewQuestionFacade

@RestController
@RequestMapping("/interview-questions")
class InterviewQuestionController(
    private val interviewQuestionFacade: InterviewQuestionFacade
) {

    @PostMapping
    suspend fun createInterviewQuestion(
        @RequestBody request: InterviewQuestionRequest,
        @RequestHeader(X_AUTHORIZATION_ID, defaultValue = "0") userId: String
    ): List<InterviewQuestion> {
        return interviewQuestionFacade.generateInterviewQuestions(request.toCommandList(userId.toLong()))
    }

    companion object {
        private const val X_AUTHORIZATION_ID = "X-Authorization-id"
    }
}
