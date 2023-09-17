package resumarble.core.domain.prediction.domain.constraints

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
            return values().first { it.value == value }
        }
    }
}
