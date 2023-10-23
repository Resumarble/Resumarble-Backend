package resumarble.core.domain.log.application

import org.springframework.context.ApplicationEventPublisher
import resumarble.core.global.annotation.Publisher

@Publisher
class UserRequestLogPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun publish(command: UserRequestLogCommand) {
        applicationEventPublisher.publishEvent(command)
    }
}
