package resumarble.core.domain.prompt.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Comment
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import resumarble.core.global.domain.BaseEntity

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE prompt SET is_deleted = true WHERE id = ?")
@Entity
class Prompt(

    @Comment(value = "삭제 여부")
    @Column(nullable = false)
    @ColumnDefault("false")
    val isDeleted: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Comment(value = "타입")
    @Column(
        length = 30,
        nullable = false
    )
    val promptType: PromptType,

    @Lob
    @Comment(value = "내용")
    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    val content: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity() {

    fun createRequestForm(
        job: String,
        resumeType: String,
        questionDifficult: String,
        career: String,
        language: String
    ) = content.format(job, resumeType, questionDifficult, career, language)
}
