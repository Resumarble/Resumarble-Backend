package resumarble.core.global.ratelimiter

import org.aspectj.lang.ProceedingJoinPoint
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback
import org.springframework.stereotype.Component
import resumarble.core.global.error.RequestPerSecondException

@Component("redisRateLimiter")
class RedisRateLimiter(
    private val redisTemplate: RedisTemplate<String, String>
) : RateLimiter {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun tryApiCall(
        key: String,
        limitRequestPerTime: LimitRequestPerTime,
        proceedingJoinPoint: ProceedingJoinPoint
    ) {
        val previousCount = redisTemplate.opsForValue().get(key)?.toInt() ?: 0

        if (previousCount > limitRequestPerTime.count) {
            throw RequestPerSecondException()
        }

        redisTemplate.execute(object : SessionCallback<Any?> {
            override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): Any {
                try {
                    operations.multi()
                    redisTemplate.opsForValue().increment(key)
                    redisTemplate.expire(key, limitRequestPerTime.ttl.toLong(), limitRequestPerTime.ttlTimeUnit)
                } catch (e: Exception) {
                    log.error(e.message, e)
                    operations.discard()
                    throw e
                }
                return operations.exec()
            }
        })
        proceedingJoinPoint.proceed()
    }
}
