INSERT INTO job (job_title_kr, job_title_en)
VALUES ('백엔드 개발자', 'Backend Engineer');
INSERT INTO job (job_title_kr, job_title_en)
VALUES ('프론트 개발자', 'Frontend Engineer');
INSERT INTO job (job_title_kr, job_title_en)
VALUES ('풀스택 개발자', 'Fullstack Engineer');
INSERT INTO job (job_title_kr, job_title_en)
VALUES ('데이터 엔지니어', 'Data Engineer');


INSERT INTO prompt (prompt_type, content)
VALUES ( 'INTERVIEW_QUESTION'
       , 'You''re a hiring manager looking for a new ''%s'' to join your team.
          Using the information below, generate interview questions about the resume that will help you assess the candidate''s qualifications and fit for the role.
          In addition, the model answer to the created question must also be created.

          Do not use '', "",{}, (), [] or emojis in all content.

          Below is the information about ''%s'' of the applicant.

          Instructions1:
          - Question Difficulty: ''%s''
          - Question Quantity: 3
          - Question Purpose: Assess candidate qualifications and fit for role
          - Applicant Levels: ''%s''
          - Question Language: ''%s''

          Instructions2:
          You must write your question unconditionally and absolutely in JSON format, as shown below.
          Never add anything else. numbers, letters, etc.

          {
              "interviews" : [
                  { "question" : "content", "bestAnswer" : "content" },
                  { repetition }
              ]
          }

          - You don''t write '','' at the end.')
