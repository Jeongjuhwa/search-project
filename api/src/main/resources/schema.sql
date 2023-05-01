CREATE TABLE IF NOT EXISTS keywords
(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    word        VARCHAR(30)     NOT NULL COMMENT '검색어',
    word_count  BIGINT          NOT NULL COMMENT '검색 횟수',
    PRIMARY KEY (id)
    );


create unique index keywords_uindex
    on keywords (word);

Create TABLE IF NOT EXISTS teams
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL COMMENT '팀명',
    PRIMARY KEY (id)
    );

Create TABLE IF NOT EXISTS members
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    teams_id BIGINT NULL comment '팀 식별자',
    NAME VARCHAR(20) NOT NULL COMMENT '이름',
    PRIMARY KEY (id),
    FOREIGN KEY (teams_id) REFERENCES teams
);


