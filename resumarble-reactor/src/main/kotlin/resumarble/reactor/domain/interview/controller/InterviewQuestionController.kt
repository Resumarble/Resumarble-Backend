package resumarble.reactor.domain.interview.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.reactor.domain.interview.application.InterviewQuestion
import resumarble.reactor.domain.interview.application.InterviewQuestionFacade

@RestController
@RequestMapping("/interview-questions")
class InterviewQuestionController(
    private val interviewQuestionFacade: InterviewQuestionFacade
) {

    @PostMapping("/{userId}")
    suspend fun createInterviewQuestion(
        @RequestBody request: InterviewQuestionRequest,
        @PathVariable userId: Long
    ): List<InterviewQuestion> {
        return interviewQuestionFacade.generateInterviewQuestion(request.toCommandList(userId))
    }
}
