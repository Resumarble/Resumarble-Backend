package resumarble.core.global.config

import feign.Retryer
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableFeignClients
@Import(FeignClientsConfiguration::class)
class FeignConfig {

    @Bean
    fun retry() = Retryer.Default(
        INITIAL_BACKOFF_PERIOD,
        MAX_BACKOFF_PERIOD,
        MAX_RETRY_ATTEMPTS
    )

    companion object {
        const val INITIAL_BACKOFF_PERIOD: Long = 1000 * 5
        const val MAX_BACKOFF_PERIOD: Long = 1000 * 5
        const val MAX_RETRY_ATTEMPTS = 3
    }
}
