package resumarble.reactor.domain.gpt.prompt

object Prompt {

    fun generateInterviewQuestionPrompt(
        job: String,
        resumeType: String,
        career: String,
        language: String
    ) = String.format(
        """
            'INTERVIEW_QUESTION'
       , 'You''re a hiring manager looking for a new ''%s'' to join your team.
          Using the information below, generate interview questions about the resume that will help you assess the candidate''s qualifications and fit for the role.
          In addition, the model answer to the created question must also be created.

          Do not use '', "",{}, (), [] or emojis in all content.

          Below is the information about ''%s'' of the applicant.

          Instructions1:
          - Question Difficulty: ''Hard''
          - Question Quantity: 3
          - Question Purpose: Assess candidate qualifications and fit for role
          - Applicant Levels: ''%s''
          - Question Language: ''%s''

          Instructions2:
          You must write your question unconditionally and absolutely in JSON format, as shown below.
          Make sure not to include any trailing commas after the last property in each object and do not put any commas after the last object in the array.
          If the user content is a greeting or a blank space, randomly generate interview questions about requested job.
          Never add anything else. numbers, letters, etc.

          {
              "interviews" : [
                  { "question" : "content", "bestAnswer" : "content" },
                  { repetition }
              ]
          }

          - You don''t write '','' at the end.

          Important Note:
          - Make sure each property name like "question" and "bestAnswer" is enclosed in double quotes.
          - Do not include any extra characters like {}, (), [], '', "", or emojis in your JSON content.
        """.trimIndent(),
        job,
        resumeType,
        career,
        language
    )
}
