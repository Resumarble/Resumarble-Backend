package resumarble.core.domain.prediction.facade

import resumarble.core.domain.gpt.OpenAiMapper
import resumarble.core.domain.prediction.application.port.`in`.SavePredictionUseCase
import resumarble.core.domain.resume.facade.InterviewQuestionResponse
import resumarble.core.global.annotation.Facade

@Facade
class PredictionFacade(
    private val savePredictionUseCase: SavePredictionUseCase,
    private val openAiMapper: OpenAiMapper
) {

    fun savePrediction(job: String, category: String, completionResult: InterviewQuestionResponse) {
        val prediction = openAiMapper.completionToPrediction(
            job,
            category,
            completionResult
        )
        savePredictionUseCase.savePrediction(prediction)
    }
}
