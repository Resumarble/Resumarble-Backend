package resumarble.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.AntPathMatcher

@Configuration
class AntMatcherConfig {

    @Bean
    fun antPathMatcher(): AntPathMatcher {
        return AntPathMatcher()
    }
}
