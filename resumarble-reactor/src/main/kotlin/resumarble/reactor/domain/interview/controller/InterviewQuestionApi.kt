package resumarble.reactor.domain.interview.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import resumarble.reactor.domain.interview.application.InterviewQuestionFacade
import resumarble.reactor.domain.interview.application.PredictionResponse

@RestController
@RequestMapping("/interview-questions")
class InterviewQuestionApi(
    private val interviewQuestionFacade: InterviewQuestionFacade
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    suspend fun createInterviewQuestion(
        @RequestBody request: InterviewQuestionRequest
    ): Flow<List<PredictionResponse>> {
        return interviewQuestionFacade.generateInterviewQuestions(request.toCommandList(0))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    suspend fun readInterviewQuestion(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestHeader(X_AUTHORIZATION_ID) userId: String
    ) = interviewQuestionFacade.getInterviewQuestionsWithNextPageIndicator(
        userId.toLong(),
        PageRequest.of(page, DEFAULT_PAGE_SIZE)
    )

    @DeleteMapping("/{interviewQuestionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteInterviewQuestion(
        @PathVariable interviewQuestionId: Long,
        @RequestHeader(X_AUTHORIZATION_ID) userId: String
    ) = interviewQuestionFacade.deleteInterviewQuestion(interviewQuestionId, userId.toLong())

    companion object {
        private const val X_AUTHORIZATION_ID = "X-Authorization-Id"
        private const val DEFAULT_PAGE_SIZE = 10
    }
}
