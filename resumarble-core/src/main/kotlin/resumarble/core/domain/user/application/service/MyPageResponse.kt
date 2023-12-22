package resumarble.core.domain.user.application.service

import resumarble.core.domain.prediction.application.port.`in`.FindInterviewQuestionResponse

data class MyPageResponse(
    val interviewQuestions: List<FindInterviewQuestionResponse>,
    val hasNext: Boolean
) {
    companion object {
        fun of(pair: Pair<List<FindInterviewQuestionResponse>, Boolean>): MyPageResponse {
            return MyPageResponse(
                pair.first,
                pair.second
            )
        }
    }
}
