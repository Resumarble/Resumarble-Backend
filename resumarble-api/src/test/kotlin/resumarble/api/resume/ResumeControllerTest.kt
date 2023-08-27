package resumarble.api.resume

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.print.print
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import resumarble.api.presentation.resume.ResumeController
import resumarble.core.domain.resume.ResumeFixture
import resumarble.core.domain.resume.facade.ResumeFacade

class ResumeControllerTest : DescribeSpec() {

    init {
        val resumeFacade: ResumeFacade = mockk<ResumeFacade>()
        val objectMapper = ObjectMapper()
        val sut = MockMvcBuilders.standaloneSetup(ResumeController(resumeFacade)).build()

        describe("ResumeController") {
            val request = ResumeFixture.interviewQuestionRequest()
            val response = ResumeFixture.interviewQuestionResponse()
            context("면접 예상 질문을 생성 요청하면") {
                every { resumeFacade.generateInterviewQuestion(any()) } returns response
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
        }
    }
}
