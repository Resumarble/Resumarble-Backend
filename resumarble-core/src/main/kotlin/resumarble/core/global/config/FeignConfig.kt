package resumarble.core.global.config

import feign.Logger
import feign.Retryer
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableFeignClients("resumarble.core.domain.gpt.client")
@Import(FeignClientsConfiguration::class)
class FeignConfig {

    @Bean
    fun log() = Logger.Level.FULL

    @Bean
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
