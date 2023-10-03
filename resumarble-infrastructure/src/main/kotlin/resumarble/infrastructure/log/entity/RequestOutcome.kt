package resumarble.infrastructure.log.entity

enum class RequestOutcome(
    val value: String
) {
    SUCCESS("성공"),
    FAILED("실패")
}
