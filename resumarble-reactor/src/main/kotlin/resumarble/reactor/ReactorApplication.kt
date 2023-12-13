package resumarble.reactor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound

@ConfigurationPropertiesScan
@SpringBootApplication
class ReactorApplication

fun main(args: Array<String>) {
    BlockHound.install()
    runApplication<ReactorApplication>(*args)
}
