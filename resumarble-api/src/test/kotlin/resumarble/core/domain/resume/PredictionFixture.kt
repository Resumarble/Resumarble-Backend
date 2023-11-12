package resumarble.core.domain.resume

import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.QuestionAndAnswer
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.domain.prediction.facade.SavePredictionCommand

object PredictionFixture {

    fun savePredictionCommand(): SavePredictionCommand {
        return SavePredictionCommand(
            userId = 0L,
            job = Job.BACKEND_ENGINEER,
            category = Category.CAREER_HISTORY,
            listOf(
                QuestionAndAnswer(
                    question = Question("What is your career?"),
                    answer = Answer("3 years")
                )
            )
        )
    }
}
