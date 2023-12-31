package resumarble

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["resumarble.core", "resumarble.api", "resumarble.infrastructure"]
)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
