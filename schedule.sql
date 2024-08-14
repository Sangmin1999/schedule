CREATE TABLE IF NOT EXISTS schedule
(
    sd_id int primary key auto_increment comment '고유id',
    sd_username varchar(100) not null comment '이름',
    contents varchar(100) not null comment '할일',
    sd_passward varchar(20) not null comment '비밀번호',
    sd_regdata DATETIME null default current_timestamp comment '등록일',
    sd_modifydate Datetime null default current_timestamp comment '수정일'
);