package resumarble.api.prediction

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.jwt.JwtUserDetails
import resumarble.api.global.response.Response
import resumarble.api.swagger.SwaggerPredictionWebPort
import resumarble.core.domain.prediction.application.port.`in`.DeletePredictionCommand
import resumarble.core.domain.prediction.application.port.`in`.DeletePredictionUseCase
import resumarble.core.domain.prediction.application.port.`in`.FindPredictionUseCase
import resumarble.core.domain.prediction.application.port.`in`.PredictionResponse

@RestController
@RequestMapping("/predictions")
class PredictionController(
    private val findPredictionUseCase: FindPredictionUseCase,
    private val deletePredictionUseCase: DeletePredictionUseCase
) : SwaggerPredictionWebPort {

    @GetMapping("/{userId}")
    override fun getPredictionByUserId(@PathVariable userId: Long): Response<List<PredictionResponse>> {
        return Response.ok(findPredictionUseCase.getPredictionByUserId(userId))
    }

    @DeleteMapping("/{predictionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePrediction(
        @PathVariable predictionId: Long,
        @AuthenticationPrincipal user: JwtUserDetails
    ): Response<Unit> {
        deletePredictionUseCase.deletePrediction(DeletePredictionCommand.from(predictionId, user.userId))
        return Response.ok()
    }
}
