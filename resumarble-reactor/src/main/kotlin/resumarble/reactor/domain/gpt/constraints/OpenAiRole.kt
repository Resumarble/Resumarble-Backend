package resumarble.reactor.domain.gpt.constraints

enum class OpenAiRole(
    val value: String
) {
    SYSTEM("system"),
    USER("user")
}
