package resumarble.reactor.global.config

import feign.Retryer
import feign.codec.Decoder
import feign.codec.Encoder
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary

@Configuration
@EnableFeignClients("resumarble.reactor.domain.gpt.adapter.out")
@Import(FeignClientsConfiguration::class)
class FeignConfig {

    @Bean
    fun decoder(): Decoder {
        return JacksonDecoder()
    }

    @Bean
    fun encoder(): Encoder {
        return JacksonEncoder()
    }

    @Bean
    @Primary
    fun retry() = Retryer.Default(
        INITIAL_BACKOFF_PERIOD,
        MAX_BACKOFF_PERIOD,
        MAX_RETRY_ATTEMPTS
    )

    companion object {
        private const val INITIAL_BACKOFF_PERIOD: Long = 1000 * 5
        private const val MAX_BACKOFF_PERIOD: Long = 1000 * 5
        private const val MAX_RETRY_ATTEMPTS = 3
    }
}
