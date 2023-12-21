package resumarble.reactor.global.coroutinecontext

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class UserContext(val userId: String) : AbstractCoroutineContextElement(UserContext) {
    companion object Key : CoroutineContext.Key<UserContext>
}
