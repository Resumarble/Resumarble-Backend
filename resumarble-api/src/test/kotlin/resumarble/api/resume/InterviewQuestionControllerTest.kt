package resumarble.api.resume

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.print.print
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import resumarble.api.global.advice.GlobalExceptionHandler
import resumarble.core.domain.resume.ResumeFixture
import resumarble.core.domain.resume.facade.InterviewQuestionFacade
import resumarble.core.global.error.CompletionFailedException

class InterviewQuestionControllerTest : DescribeSpec() {

    init {
        val interviewQuestionFacade: InterviewQuestionFacade = mockk<InterviewQuestionFacade>()
        val objectMapper = ObjectMapper()
        val sut = MockMvcBuilders.standaloneSetup(InterviewQuestionController(interviewQuestionFacade))
            .setControllerAdvice(GlobalExceptionHandler()).build()

        describe("InterviewQuestionController") {
            val request = ResumeFixture.interviewQuestionRequest()
            val responseOneRequest = ResumeFixture.interviewQuestionResponse()
            val response = ResumeFixture.interviewQuestionResponse()
            context("면접 예상 질문을 생성 요청하면") {
                every { interviewQuestionFacade.generateInterviewQuestion(any()) } returns responseOneRequest
                coEvery { interviewQuestionFacade.generateInterviewQuestions(any(), any()) } returns response
                it("면접 예상 질문을 생성한다.") {
                    sut.perform(
                        post("/resumes/interview-questions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andDo { print() }
                        .andExpect {
                            status().isOk
                            content().json(objectMapper.writeValueAsString(response))
                        }
                }
            }
            context("면접 예상 질문 생성에 실패하면") {
                every { interviewQuestionFacade.generateInterviewQuestion(any()) } throws CompletionFailedException()
                it("400을 반환한다.") {
                    sut.perform(
                        post("/resumes/interview-questions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andDo { print() }
                        .andExpect {
                            status().isBadRequest
                        }
                }
            }
        }
    }
}
