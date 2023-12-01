package resumarble.reactor.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Value("\${external.openai.url}")
    private lateinit var url: String

    @Value("\${external.openai.token}")
    private lateinit var token: String

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .build()
    }
}
