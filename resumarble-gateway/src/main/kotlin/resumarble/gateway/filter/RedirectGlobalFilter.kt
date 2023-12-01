package resumarble.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.net.URI

@Component
class RedirectGlobalFilter : GlobalFilter {
    override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void>? {
        val request = exchange?.request
        if ("/resumarble" == request?.uri?.path) {
            val response = exchange.response
            response.statusCode = HttpStatus.PERMANENT_REDIRECT
            response.headers.location = URI.create("https://www.resumarble.site")
            return Mono.empty()
        }
        return chain!!.filter(exchange)
    }
}
