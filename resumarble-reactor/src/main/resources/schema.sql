CREATE TABLE IF NOT EXISTS interview_question
(
    id         bigint      NOT NULL AUTO_INCREMENT,
    user_id    bigint      NOT NULL,
    job        varchar(50) NOT NULL,
    category   varchar(50) NOT NULL,
    question   TEXT        NOT NULL,
    answer     TEXT        NOT NULL,
    is_deleted tinyint(1)  NOT NULL DEFAULT 0,
    created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  AUTO_INCREMENT = 1;
