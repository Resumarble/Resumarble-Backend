package resumarble.core.domain.prediction.facade

import jakarta.annotation.PreDestroy
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import resumarble.core.domain.prediction.application.port.`in`.SavePredictionUseCase
import resumarble.core.global.annotation.Facade
import resumarble.core.global.util.loggingErrorMarking

@Facade
class PredictionFacade(
    private val savePredictionUseCase: SavePredictionUseCase
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun savePrediction(command: SavePredictionCommand) {
        scope.launch(handler) {
            savePredictionUseCase.savePrediction(command.toDomain())
        }
    }

    val handler = CoroutineExceptionHandler { _, throwable ->
        loggingErrorMarking {
            SAVE_PREDICTION_ERROR_MESSAGE + "${throwable.message}"
        }
    }

    @PreDestroy
    fun cleanUp() {
        scope.cancel()
    }

    companion object {
        private const val SAVE_PREDICTION_ERROR_MESSAGE = "면접 예상 질문 저장이 실패했습니다. 예외 메시지: "
    }
}
