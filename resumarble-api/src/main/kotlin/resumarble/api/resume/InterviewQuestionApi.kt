package resumarble.api.resume

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.api.swagger.SwaggerInterviewQuestionWebPort
import resumarble.core.domain.prediction.application.port.`in`.DeleteInterviewQuestionUseCase
import resumarble.core.domain.prediction.application.port.`in`.DeletePredictionCommand
import resumarble.core.domain.resume.facade.InterviewQuestionFacade
import resumarble.core.domain.resume.facade.Prediction

@RestController
@RequestMapping("/interview-questions")
class InterviewQuestionApi(
    private val interviewQuestionFacade: InterviewQuestionFacade,
    private val deleteInterviewQuestionUseCase: DeleteInterviewQuestionUseCase
) : SwaggerInterviewQuestionWebPort {

    @PostMapping
    override suspend fun interviewQuestions(
        @RequestBody request: InterviewQuestionRequest,
        @AuthenticationPrincipal user: JwtUserDetails?
    ): Response<List<Prediction>> {
        val userId = user?.userId ?: 0L
        val commands = request.toCommandList(userId)
        val responses = interviewQuestionFacade.generateInterviewQuestions(userId, commands)
        return Response.ok(responses)
    }

    @DeleteMapping("/{interviewQuestionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePrediction(
        @PathVariable interviewQuestionId: Long,
        @AuthenticationPrincipal user: JwtUserDetails
    ): Response<Unit> {
        deleteInterviewQuestionUseCase.deleteInterviewQuestion(
            DeletePredictionCommand.from(
                interviewQuestionId,
                user.userId
            )
        )
        return Response.ok()
    }
}
