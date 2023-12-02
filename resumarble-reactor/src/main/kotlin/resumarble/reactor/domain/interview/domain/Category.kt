package resumarble.reactor.domain.interview.domain

enum class Category(
    val value: String
) {
    INTRODUCTION("introduction"),
    TECHNOLOGY_STACK("technology stack"),
    PROJECT_EXPERIENCE("project experience"),
    CAREER_HISTORY("career history");

    companion object {
        @JvmStatic
        fun fromValue(value: String): Category {
            return entries.first { it.value == value }
        }
    }
}
