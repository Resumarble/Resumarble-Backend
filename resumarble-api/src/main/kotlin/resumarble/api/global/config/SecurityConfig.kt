package resumarble.api.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import resumarble.api.global.jwt.JwtAuthenticationEntryPoint
import resumarble.api.global.jwt.JwtAuthorizationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthorizationFilter: JwtAuthorizationFilter,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf { it.disable() }
            .sessionManagement { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/jobs/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/resumes/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/test").permitAll()
                    .requestMatchers("/users/**").permitAll()
                    .anyRequest().permitAll()
            }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(jwtAuthenticationEntryPoint) }
            .build()
}
