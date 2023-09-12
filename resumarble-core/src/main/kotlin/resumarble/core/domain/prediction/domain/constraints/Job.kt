package resumarble.core.domain.prediction.domain.constraints

enum class Job(val jobTitleKr: String, val jobTitleEn: String) {
    BACKEND_ENGINEER("백엔드 개발자", "Backend Developer"),
    FRONTEND_ENGINEER("프론트엔드 개발자", "Frontend Developer"),
    ANDROID_DEVELOPER("안드로이드 개발자", "Android Developer"),
    IOS_DEVELOPER("iOS 개발자", "iOS Developer"),
    DEVOPS_ENGINEER("데브옵스 엔지니어", "DevOps Engineer"),
    DATA_ENGINEER("데이터 엔지니어", "Data Engineer"),
    DATA_SCIENTIST("데이터 사이언티스트", "Data Scientist"),
    MACHINE_LEARNING_ENGINEER("머신러닝 엔지니어", "Machine Learning Engineer"),
    PRODUCT_MANAGER("프로덕트 매니저", "Product Manager"),
    PROJECT_MANAGER("프로젝트 매니저", "Project Manager")
}
