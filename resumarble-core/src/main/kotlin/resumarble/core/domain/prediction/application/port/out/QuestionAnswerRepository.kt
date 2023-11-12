package resumarble.core.domain.prediction.application.port.out

import resumarble.core.domain.prediction.domain.QuestionAndAnswer

interface QuestionAnswerRepository {

    fun delete(qaId: Long)
    fun findQuestionAnswer(qaId: Long): QuestionAndAnswer?
}
