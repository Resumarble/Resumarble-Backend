package resumarble.core.domain.log.constraints

enum class RequestOutcome(
    private val value: String
) {
    SUCCESS("성공"),
    FAILED("실패");
}
