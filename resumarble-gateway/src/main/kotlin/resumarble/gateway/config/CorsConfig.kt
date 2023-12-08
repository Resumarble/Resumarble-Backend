package resumarble.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val corsConfiguration = CorsConfiguration()

        corsConfiguration.allowCredentials = true
        corsConfiguration.addAllowedOriginPattern(BACK_LOCALHOST)
        corsConfiguration.addAllowedOriginPattern(FRONT_LOCALHOST)
        corsConfiguration.addAllowedOriginPattern(FRONT_PROD)
        corsConfiguration.addAllowedOriginPattern(FRONT_PROD_WWW)
        corsConfiguration.addAllowedOriginPattern(ALL)
        corsConfiguration.addAllowedHeader(EXPOSED_HEADER)
        source.registerCorsConfiguration(SOURCE_PATTERN, corsConfiguration)

        return CorsFilter(source)
    }

    companion object {
        private const val ALL = "*"
        private const val EXPOSED_HEADER = "Access-Control-Allow-Origin"
        private const val SOURCE_PATTERN = "/**"
        private const val FRONT_LOCALHOST = "http://localhost:3000"
        private const val BACK_LOCALHOST = "http://localhost:8080"
        private const val FRONT_PROD = "https://resumarble.site"
        private const val FRONT_PROD_WWW = "https://www.resumarble.site"
    }
}
