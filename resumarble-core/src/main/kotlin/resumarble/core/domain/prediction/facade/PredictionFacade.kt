package resumarble.core.domain.prediction.facade

import resumarble.core.domain.prediction.application.port.`in`.SavePredictionUseCase
import resumarble.core.global.annotation.Facade

@Facade
class PredictionFacade(
    private val savePredictionUseCase: SavePredictionUseCase
) {

    fun savePrediction(command: SavePredictionCommand) {
        savePredictionUseCase.savePrediction(command.toDomain())
    }
}
