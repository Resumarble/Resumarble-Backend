DROP TABLE IF EXISTS job;
CREATE TABLE job
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    job_title_kr VARCHAR(255) NOT NULL,
    job_title_en VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4,
  COLLATE = utf8mb4_bin;