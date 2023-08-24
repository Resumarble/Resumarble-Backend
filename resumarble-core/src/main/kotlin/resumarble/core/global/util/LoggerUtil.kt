package resumarble.core.global.util

import org.slf4j.LoggerFactory

object LoggerUtil {
    inline fun <reified T> T.logger(): org.slf4j.Logger = LoggerFactory.getLogger(T::class.java)
}
