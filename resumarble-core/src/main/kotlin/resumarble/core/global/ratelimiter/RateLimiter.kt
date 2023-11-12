package resumarble.core.global.ratelimiter

import org.aspectj.lang.ProceedingJoinPoint

interface RateLimiter {
    @Throws(Throwable::class)
    fun tryApiCall(key: String, limitRequestPerTime: LimitRequestPerTime, proceedingJoinPoint: ProceedingJoinPoint)
}
