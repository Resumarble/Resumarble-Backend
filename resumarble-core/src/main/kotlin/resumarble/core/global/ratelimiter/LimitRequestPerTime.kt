package resumarble.core.global.ratelimiter

import java.util.concurrent.TimeUnit

annotation class LimitRequestPerTime(
    /**
     * 분당호출 제한시킬 unique key prefix
     */
    val prefix: String = "",

    /**
     * 호출 제한 시간
     */
    val ttl: Int = 1,

    /**
     * 호출 제한 시간 단위
     */
    val ttlTimeUnit: TimeUnit = TimeUnit.SECONDS,

    /**
     * 분당 호출제한 카운트
     */
    val count: Int = 1
)
