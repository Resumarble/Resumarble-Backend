package resumarble.core.domain.feedback.application

object FeedbackPrompt {

    fun createFeedbackPrompt(command: FeedbackCommand): String {
        return String.format(PROMPT, command.question, command.answer)
    }

    private const val PROMPT = """
            Based on the provided question and the self-introduction content, your task is to analyze the response and offer feedback. 

            Do not use ', "",{}, (), [] or emojis in all content.
            
            question: '%s'
            self-introduction content: '%s'
            
            Instructions1:

            Focus on the following aspects: 

            1. Content Relevance: Assess whether the self-introduction content appropriately addresses the question. Does it stay on topic and fully answer the question? 

            2. Grammatical Accuracy: Evaluate the grammatical correctness of the response. Identify any errors or areas where the language can be improved for clarity and professionalism. 

            3. Overall Coherence and Structure: Consider the overall flow and organization of the self-introduction. Is the information logically presented and easy to follow? 

            4. Tone and Style: Reflect on the tone and style of the writing. Is it suitable for a professional setting? Does it positively reflect the candidate's personality and professionalism?
             
            5. Language: Korean
            
            Instructions2:
            You must write your question unconditionally and absolutely in JSON format, as shown below.
          Make sure not to include any trailing commas after the last property in each object and do not put any commas after the last object in the array.
          If the user content is a greeting or a blank space, randomly generate interview questions about requested job.
          Never add anything else. numbers, letters, etc.
          
          {    
              "feedback" : [ 
                        "content",        
                        "content",        
                        "content"    
              ] 
          } 
          Important Note:
          - Do not include any extra characters like {}, (), [], '', "", or emojis in your JSON content.')
          - Your detailed and constructive feedback will assist the candidate in refining their self-introduction for a more effective and professional presentation."
        """
}
