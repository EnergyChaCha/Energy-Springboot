-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS trend
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 생성된 데이터베이스 사용
USE trend;

create table IF NOT EXISTS trend.admin
(
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    admin_id     varchar(100) null,
    admin_pw     varchar(100) null,
    branch       varchar(100) null,
    layout       tinyint      null,
    name         varchar(100) null
);

create table IF NOT EXISTS trend.book
(
    id                   int auto_increment
        primary key,
    created_time         datetime(6)  null,
    updated_time         datetime(6)  null,
    category_id          varchar(50)  null,
    category_name        varchar(100) null,
    contents             text         null,
    product_id           int          null,
    product_name         varchar(255) null,
    sale_price           int          null,
    search_keyword       varchar(255) null,
    total_click_count    int          null,
    total_order_amount   int          null,
    total_order_count    int          null,
    total_purchase_count int          null
);

create table IF NOT EXISTS trend.book_click
(
    id           int auto_increment
        primary key,
    created_time datetime(6) null,
    updated_time datetime(6) null,
    count        int         null,
    book_id      int         null,
    constraint FKi5f80n3polapytnh6my34vd9m
        foreign key (book_id) references trend.book (id)
);

create table IF NOT EXISTS trend.box
(
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    name         varchar(100) null,
    admin_id     int          null,
    constraint FKbfgh7scfjbvhouj4n5g708t05
        foreign key (admin_id) references trend.admin (id)
);

create table IF NOT EXISTS trend.box_book
(
    id           int auto_increment
        primary key,
    created_time datetime(6) null,
    updated_time datetime(6) null,
    book_id      int         null,
    box_id       int         null,
    constraint FK1mxtl6892ojl5qevn46wmiip5
        foreign key (book_id) references trend.book (id),
    constraint FKgua71qmyj0ycj4aocri26m959
        foreign key (box_id) references trend.box (id)
);

create table IF NOT EXISTS trend.daily_recommend
(
    id           int auto_increment
        primary key,
    created_time datetime(6) null,
    updated_time datetime(6) null,
    book_id      int         null,
    constraint FKe0botldy11bmwkn9freitqdwg
        foreign key (book_id) references trend.book (id)
);

create table IF NOT EXISTS trend.platform
(
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    name         varchar(100) null
);

create table IF NOT EXISTS trend.tag
(
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    topic        varchar(255) null,
    book_id      int          null,
    constraint FK23a8a47o1u9njpmica5ujvpql
        foreign key (book_id) references trend.book (id)
);

create table IF NOT EXISTS trend.thema_code
(
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    description  varchar(200) null,
    note         varchar(200) null,
    rerank_score double       null,
    value        varchar(100) null,
    book_id      int          null,
    constraint FKrmvqtc8v5jnv4utqkahg2rvq0
        foreign key (book_id) references trend.book (id)
);

create table IF NOT EXISTS trend.trend_category
(
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    code         tinyint      null,
    name         varchar(100) null
);

create table IF NOT EXISTS trend.keyword
(
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    click_count  int          null,
    name         varchar(255) null,
    ranking      int          null,
    selected     bit          null,
    category_id  int          null,
    constraint FKkh74d5y871fcn8oh38qc0ky4h
        foreign key (category_id) references trend.trend_category (id)
);

create table IF NOT EXISTS trend.origin_data
(
    id           int auto_increment
        primary key,
    created_time datetime(6) null,
    updated_time datetime(6) null,
    contents     json        null,
    category_id  int         null,
    platform_id  int         null,
    constraint FKarh8i5tw15xv2qnqef3ib2c46
        foreign key (category_id) references trend.trend_category (id),
    constraint FKsgr09xjc0vfuhlpx7b0cwjjae
        foreign key (platform_id) references trend.platform (id)
);

create table IF NOT EXISTS trend.recommend_keyword
(
    id                 int auto_increment
        primary key,
    created_time       datetime(6) null,
    updated_time       datetime(6) null,
    daily_recommend_id int         null,
    keyword_id         int         null,
    constraint FKop1iygtye9107694bfly3puc9
        foreign key (keyword_id) references trend.keyword (id),
    constraint FKpds726af2r9ql2g3f0wu2rfu
        foreign key (daily_recommend_id) references trend.daily_recommend (id)
);

create table IF NOT EXISTS trend.trend_source
(
    id             int auto_increment
        primary key,
    created_time   datetime(6) null,
    updated_time   datetime(6) null,
    keyword_id     int         null,
    origin_data_id int         null,
    constraint FKrck4xkxy61jqeojgw6ptlw5dg
        foreign key (keyword_id) references trend.keyword (id),
    constraint FKskr0lxk4l37j095yja5jn3vcp
        foreign key (origin_data_id) references trend.origin_data (id)
);

create table  IF NOT EXISTS trend.question
(    
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    question_text VARCHAR(255) null
);

create table  IF NOT EXISTS trend.book_question_map
(    
    id           int auto_increment
        primary key,
    
    book_id     int         null,
    question_id int         null,
    recommend_cnt int         null,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    constraint FKxj2o5q1m8d9c7r4g0y6f3h8i
        foreign key (book_id) references trend.book (id),
    constraint FK8d4s2v9c1m7r3x6n5t0q2p1l
        foreign key (question_id) references trend.question (id)
);

create table IF NOT EXISTS trend.keyword_click
(
    id           int auto_increment
        primary key,
    created_time datetime(6)  null,
    updated_time datetime(6)  null,
    click_count  int          null,
    keyword_name varchar(255) null,
    category_id  int          null,
    constraint FKk86c9x42ex3urirtmaf4gfcey
        foreign key (category_id) references trend.trend_category (id)
);





-- 컬럼 디폴트 값 변경
ALTER TABLE trend.keyword
MODIFY COLUMN ranking INT DEFAULT 0,
MODIFY COLUMN click_count INT DEFAULT 0,
MODIFY COLUMN selected BIT DEFAULT 1;

-- 컬럼 추가
-- 프로시저가 존재하는지 확인하고 있다면 삭제
DROP PROCEDURE IF EXISTS AddUriColumnIfNotExists;

DELIMITER $$

CREATE PROCEDURE AddUriColumnIfNotExists()
BEGIN
    DECLARE column_count INT;

    -- 컬럼이 존재하는지 확인
    SELECT COUNT(*)
    INTO column_count
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = 'trend' AND TABLE_NAME = 'origin_data' AND COLUMN_NAME = 'uri';

    -- 컬럼이 존재하지 않으면 추가
    IF column_count = 0 THEN
        ALTER TABLE `trend`.`origin_data`
            ADD COLUMN `uri` VARCHAR(255) NULL; -- 여기에 원하는 컬럼 타입 및 옵션을 추가할 수 있습니다.
    END IF;
END$$

DELIMITER ;

CALL AddUriColumnIfNotExists();

-- 컬럼 길이 늘리기
ALTER TABLE trend.book
    MODIFY COLUMN contents MEDIUMTEXT;




-- [커스텀 페이지 컬럼 추가]
-- 프로시저가 존재하는지 확인하고 있다면 삭제
-- 프로시저가 존재하는지 확인하고 있다면 삭제
DROP PROCEDURE IF EXISTS AddCustomColumnIfNotExists;

DELIMITER $$

CREATE PROCEDURE AddCustomColumnIfNotExists()
BEGIN
    DECLARE column_count INT;

    -- 컬럼이 존재하는지 확인
    SELECT COUNT(*)
    INTO column_count
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = 'trend' AND TABLE_NAME = 'admin' AND COLUMN_NAME = 'custom_name';

    -- 컬럼이 존재하지 않으면 추가
    IF column_count = 0 THEN
        ALTER TABLE `trend`.`admin`
            ADD COLUMN `custom_name` VARCHAR(100) DEFAULT '커스텀 페이지' NULL;
        ALTER TABLE `trend`.`admin`
            ADD COLUMN `custom_contents` TEXT NULL;
    END IF;
END$$

DELIMITER ;

CALL AddCustomColumnIfNotExists();
-- [커스텀 페이지 컬럼 추가 종료]

