package resumarble.api.prediction

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import resumarble.api.global.response.Response
import resumarble.core.domain.prediction.domain.application.port.`in`.FindPredictionUseCase
import resumarble.core.domain.prediction.domain.application.port.`in`.PredictionResponse

@RestController
@RequestMapping("/predictions")
class PredictionController(
    private val findPredictionUseCase: FindPredictionUseCase
) {

    @GetMapping("/{userId}")
    fun getPredictionByUserId(@PathVariable userId: Long): Response<List<PredictionResponse>> {
        return Response.ok(findPredictionUseCase.getPredictionByUserId(userId))
    }
}
