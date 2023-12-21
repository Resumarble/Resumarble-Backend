package resumarble.core.global.ratelimiter

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Qualifier
import kotlin.coroutines.Continuation

class RateLimiterAspect(
    @Qualifier("redisRateLimiter")
    private val rateLimiter: RateLimiter
) {
    @Around("@annotation(resumarble.core.global.ratelimiter.LimitRequestPerTime)")
    fun interceptor(joinPoint: ProceedingJoinPoint) {
        val continuation = joinPoint.args.last() as Continuation<*>
        val limitRequestPerTime = getLimitRequestPerTimeAnnotationFromMethod(joinPoint)
        // LimitRequestPerTime 커스텀 어노테이션이 설정되지 않은 타겟 메서드는 분당 호출 제한을 체크하지 않고 바로 실행
        if (limitRequestPerTime == null) {
            joinPoint.proceed()
            return
        }
        // LimitRequestPerTime 커스텀 어노테이션을 설정한 타겟 메서드만 분당 호출 제한을 체크
        val uniqueKey = getUniqueKeyFromMethodParameter(joinPoint)
        rateLimiter.tryApiCall(
            composeKeyWithUniqueKey(limitRequestPerTime.prefix, uniqueKey, "ApiCounter"),
            limitRequestPerTime,
            joinPoint
        )
    }

    private fun getLimitRequestPerTimeAnnotationFromMethod(joinPoint: ProceedingJoinPoint): LimitRequestPerTime? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        return method.getAnnotation(LimitRequestPerTime::class.java)
    }

    private fun getUniqueKeyFromMethodParameter(joinPoint: ProceedingJoinPoint): Long {
        val parameters = joinPoint.args.toList()
        return parameters[0] as Long
    }

    private fun composeKeyWithUniqueKey(prefix: String, uniqueId: Long, suffix: String): String {
        return "$prefix:$uniqueId:$suffix"
    }
}
