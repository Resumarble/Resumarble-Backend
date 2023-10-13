package resumarble.core.domain.prediction.domain.constraints

enum class Job(val jobTitleKr: String, val jobTitleEn: String) {
    BACKEND_ENGINEER("백엔드 개발자", "Backend Engineer"),
    FRONTEND_ENGINEER("프론트 개발자", "Frontend Engineer"),
    FULLSTACK_ENGINEER("풀스택 개발자", "Fullstack Engineer"),
    ANDROID_DEVELOPER("안드로이드 개발자", "Android Developer"),
    IOS_DEVELOPER("iOS 개발자", "iOS Developer"),
    DEVOPS_ENGINEER("데브옵스 엔지니어", "DevOps Engineer"),
    DATA_ENGINEER("데이터 엔지니어", "Data Engineer"),
    MACHINE_LEARNING_ENGINEER("머신러닝 엔지니어", "Machine Learning Engineer"),
    PRODUCT_MANAGER("프로덕트 매니저", "Product Manager"),
    PROJECT_MANAGER("프로젝트 매니저", "Project Manager");

    companion object {
        @JvmStatic
        fun fromJobTitleEn(title: String): Job {
            return entries.firstOrNull { it.jobTitleEn == title }
                ?: throw IllegalArgumentException("Invalid job title: $title")
        }
    }
}
