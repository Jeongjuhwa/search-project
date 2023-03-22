CREATE TABLE IF NOT EXISTS keywords
(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    word        VARCHAR(30)     NOT NULL COMMENT '검색어',
    word_count  BIGINT          NOT NULL COMMENT '검색 횟수',
    PRIMARY KEY (id)
    );


create unique index keywords_uindex
    on keywords (word);



