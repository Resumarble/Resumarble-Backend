package resumarble.core.domain.log.application

import org.springframework.context.ApplicationEventPublisher
import resumarble.core.domain.gpt.application.UserRequestLogEvent
import resumarble.core.global.annotation.Publisher

@Publisher
class UserRequestLogPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun publish(event: UserRequestLogEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}
