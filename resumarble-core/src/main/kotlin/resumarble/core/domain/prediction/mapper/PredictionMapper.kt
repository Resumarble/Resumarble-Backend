package resumarble.core.domain.prediction.mapper

import resumarble.core.domain.prediction.domain.Answer
import resumarble.core.domain.prediction.domain.Question
import resumarble.core.domain.prediction.domain.constraints.Category
import resumarble.core.domain.prediction.domain.constraints.Job
import resumarble.core.domain.prediction.facade.QuestionAndAnswer
import resumarble.core.domain.prediction.facade.SavePredictionCommand
import resumarble.core.domain.resume.facade.InterviewQuestionCommand
import resumarble.core.domain.resume.facade.Prediction
import resumarble.core.global.annotation.Mapper

@Mapper
object PredictionMapper {

    fun completionToSavePredictionCommand(
        command: InterviewQuestionCommand,
        interviews: List<Prediction>
    ): SavePredictionCommand {
        return SavePredictionCommand(
            userId = command.userId,
            job = Job.fromJobTitleEn(command.job),
            category = Category.fromValue(command.category),
            questionAndAnswerList = convertToQuestionAndAnswer(interviews)
        )
    }

    private fun convertToQuestionAndAnswer(interviews: List<Prediction>): List<QuestionAndAnswer> {
        return interviews.map {
            QuestionAndAnswer(
                question = Question(it.question),
                answer = Answer(it.bestAnswer)
            )
        }
    }
}
