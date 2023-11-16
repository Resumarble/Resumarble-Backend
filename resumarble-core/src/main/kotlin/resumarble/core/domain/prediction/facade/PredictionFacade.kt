package resumarble.core.domain.prediction.facade

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import resumarble.core.domain.prediction.application.port.`in`.SavePredictionUseCase
import resumarble.core.global.annotation.Facade
import resumarble.core.global.util.loggingErrorMarking

@Facade
class PredictionFacade(
    private val savePredictionUseCase: SavePredictionUseCase
) {
    fun savePrediction(command: SavePredictionCommand) {
        CoroutineScope(Dispatchers.IO + handler).launch {
            savePredictionUseCase.savePrediction(command.toDomain())
        }
    }

    val handler = CoroutineExceptionHandler { _, throwable ->
        loggingErrorMarking {
            SAVE_PREDICTION_ERROR_MESSAGE + throwable.message
        }
    }

    companion object {
        private const val SAVE_PREDICTION_ERROR_MESSAGE = "면접 예상 질문 저장이 실패했습니다. 예외 메시지: "
    }
}
