package resumarble.reactor.global.annotation

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Transactional(readOnly = true)
annotation class Reader
