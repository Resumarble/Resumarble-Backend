package resumarble.api.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
            ).info(getInfo())
            .servers(getServers())
    }

    // swagger 설명 info 작업
    private fun getInfo(): Info {
        return Info()
            .title(SWAGGER_TITLE)
            .description(SWAGGER_DESCRIPTION)
            .version(API_VERSION)
    }

    // swagger에 server 종류 추가
    private fun getServers(): List<Server> {
        return listOf(
            Server().url("http://localhost:8080").description("localhost")
                .url("http://49.50.173.88:8080").description("dev server")
        )
    }

    companion object {
        private const val SWAGGER_TITLE = "Resumarble API 명세서 "
        private const val SWAGGER_DESCRIPTION = ""
        private const val API_VERSION = "1.0.0"
    }
}
