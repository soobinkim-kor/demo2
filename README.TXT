-- auto-generated definition
create table user_base
(
    USR_NO          bigint auto_increment
        primary key,
    USR_ID          varchar(50)                        not null,
    USR_NM          varchar(50)                        not null,
    USR_PWD         varchar(255)                       not null,
    usr_role        varchar(10)                        not null,
    USR_EMAIL       varchar(255)                       not null,
    USR_BIRTH       date                               null,
    USR_PWD_UPD_DTM datetime                           null,
    SYS_REG_DTM     datetime default CURRENT_TIMESTAMP not null,
    SYS_UPD_DTM     datetime                           null,
    SYS_DEL_DTM     datetime                           null,
    SYS_DEL_YN      char     default 'N'               not null
);


-- auto-generated definition
create table community_comment
(
    comment_id        bigint auto_increment comment '댓글 PK'
        primary key,
    post_id           bigint                             not null comment '게시글 ID',
    author_id         bigint                             not null comment '댓글 작성자 ID',
    parent_comment_id bigint                             null comment '부모 댓글 ID (대댓글)',
    depth             int      default 0                 not null comment '댓글 깊이 (0:댓글, 1+:대댓글)',
    content           text                               not null comment '댓글 내용',
    is_deleted        char     default 'N'               not null comment '삭제 여부 (Y/N)',
    created_at        datetime default CURRENT_TIMESTAMP not null comment '생성 일시',
    updated_at        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '수정 일시',
    constraint fk_comment_parent
        foreign key (parent_comment_id) references community_comment (comment_id),
    constraint fk_comment_post
        foreign key (post_id) references community_post (post_id)
)
    comment '커뮤니티 게시글 댓글 테이블';

create index idx_comment_created
    on community_comment (created_at);

create index idx_comment_parent
    on community_comment (parent_comment_id);
create index idx_comment_post
    on community_comment (post_id);


-- auto-generated definition
create table article_base
(
    ARTICLE_ID    bigint auto_increment comment '게시글 PK'
        primary key,
    author_no     bigint                             not null comment '작성자 회원 ID',
    title         varchar(200)                       not null comment '게시글 제목',
    content       text                               not null comment '게시글 내용',
    view_count    int      default 0                 not null comment '조회수',
    like_count    int      default 0                 not null comment '좋아요 수',
    comment_count int      default 0                 not null comment '댓글 수 (캐시용)',
    is_deleted    char     default 'N'               not null comment '삭제 여부 (Y/N)',
    created_at    datetime default CURRENT_TIMESTAMP not null comment '생성 일시',
    updated_at    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '수정 일시'
)
    comment '커뮤니티 게시글 테이블';

create index idx_post_author
    on article_base (author_no);

create index idx_post_created
    on article_base (created_at);

