package resumarble.core.global.domain

import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @CreatedDate
    private val createdDate: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    private val modifiedDate: LocalDateTime = LocalDateTime.now()
}
