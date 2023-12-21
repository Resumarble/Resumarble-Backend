package resumarble.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Gateway

fun main(args: Array<String>) {
    runApplication<Gateway>(*args)
}
