package resumarble.core.domain.user.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.user.application.port.JoinUserCommand
import resumarble.core.domain.user.application.port.`in`.JoinUserUseCase
import resumarble.core.domain.user.application.port.out.FindUserPort
import resumarble.core.domain.user.application.port.out.JoinUserPort
import resumarble.core.global.error.DuplicateUserException

@Service
class UserCommandService(
    private val joinUserPort: JoinUserPort,
    private val findUserPort: FindUserPort
) : JoinUserUseCase {

    @Transactional
    override fun join(command: JoinUserCommand) {
        findUserPort.existsUserByEmail(command.email).takeIf { !it } ?: throw DuplicateUserException(command.email)
        joinUserPort.join(command.toDomain())
    }
}
