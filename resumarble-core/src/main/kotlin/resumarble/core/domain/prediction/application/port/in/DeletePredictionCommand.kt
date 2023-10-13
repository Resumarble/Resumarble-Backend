package resumarble.core.domain.prediction.application.port.`in`

data class DeletePredictionCommand(
    val predictionId: Long,

    val userId: Long
) {
    companion object {
        @JvmStatic
        fun from(
            predictionId: Long,
            userId: Long
        ) = DeletePredictionCommand(
            predictionId,
            userId
        )
    }
}
