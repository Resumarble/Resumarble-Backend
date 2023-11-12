package resumarble.api.prediction

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.response.Response
import resumarble.api.swagger.SwaggerQuestionAnswerWebPort
import resumarble.core.domain.prediction.application.service.QuestionAnswerCommandService

@RestController
class QuestionAnswerController(
    private val questionAnswerCommandService: QuestionAnswerCommandService
) : SwaggerQuestionAnswerWebPort {

    @DeleteMapping("/question-answers/{qaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun deleteQuestionAnswer(@PathVariable qaId: Long): Response<Unit> {
        questionAnswerCommandService.deleteQuestionAnswer(qaId)
        return Response.ok()
    }
}
