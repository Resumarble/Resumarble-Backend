package resumarble.core.domain.prediction.application.port.`in`

data class DeletePredictionCommand(
    val interviewQuestionId: Long,

    val userId: Long
) {
    companion object {
        @JvmStatic
        fun from(
            interviewQuestionId: Long,
            userId: Long
        ) = DeletePredictionCommand(
            interviewQuestionId,
            userId
        )
    }
}
