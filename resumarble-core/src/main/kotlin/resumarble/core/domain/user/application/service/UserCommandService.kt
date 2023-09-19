package resumarble.core.domain.user.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import resumarble.core.domain.user.application.JoinUserCommand
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
        findUserPort.existsUserByAccount(command.account).takeIf { !it }
            ?: throw DuplicateUserException(command.account)
        joinUserPort.join(command.toDomain())
    }
}
