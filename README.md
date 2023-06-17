# MultiBackendTeam3

## 초기화

### 프로젝트에 없는 파일 (.gitignore에 등록된 파일)

- keys.properties
```properties
## api 키를 저장하는 파일

keys.kakao.map=	{카카오맵 javascript 키}

keys.tour.info.encode= {인코딩 된 tour api}
keys.tour.info.decode= {디코딩 된 tour api}
```

<br>

- resources/application.properties
```properites
# Oracle Connection Settings
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:{SID} 
spring.datasource.username={유저이름}
spring.datasource.password={유저비밀번호}

# Mapper Location
mybatis.mapper-locations=classpath:/mapper/**/*.xml

# Domain Aliases
mybatis.type-aliases-package=multi.backend.project

# JDBC null settings
mybatis.configuration.jdbc-type-for-null=null


# JSP
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# SQL 
spring.sql.init.mode=always


spring.messages.basename=messages/keys
spring.messages.encoding=UTF-8
```

<br>

- data.sql
```sql
INSERT INTO testtable(test_id, test_name, test_date)
    VALUES(100, 'hongildong', sysdate);

-- pathmap 테스트용 계정
INSERT INTO memberuser(user_id, user_name, user_pwd, user_email, user_phone, user_role)
    VALUES(memberuser_sequence.nextval, '나', '1234', 'abcd@abcd.com', '010-1111-1111', 'ROLE_USER');
```

<br>

- schema.sql
```sql
--SELECT  'DROP TABLE ' || object_name || ' CASCADE CONSTRAINTS;'
--  FROM    user_objects
--WHERE   object_type = 'TABLE';

--SELECT  'DROP SEQUENCE ' || object_name || ';'
--  FROM    user_objects
--WHERE   object_type = 'SEQUENCE';

--SELECT  'DROP VIEW ' || object_name || ';'
--  FROM    user_objects
--WHERE   object_type = 'VIEW';



--DROP VIEW REVIEW_VI;

--DROP TABLE TESTTABLE CASCADE CONSTRAINTS;
--DROP TABLE AREA_LARGE CASCADE CONSTRAINTS;
--DROP TABLE AREA_SMALL CASCADE CONSTRAINTS;

DROP TABLE MEMBERUSER CASCADE CONSTRAINTS;
DROP TABLE REVIEW CASCADE CONSTRAINTS;
DROP TABLE REVIEW_COMMENT CASCADE CONSTRAINTS;
DROP TABLE PATH CASCADE CONSTRAINTS;
DROP TABLE MARK CASCADE CONSTRAINTS;
DROP TABLE FAVORITE CASCADE CONSTRAINTS;
DROP TABLE PATH_COMMENT CASCADE CONSTRAINTS;

DROP SEQUENCE LARGE_ID_SEQ;
DROP SEQUENCE SMALL_ID_SEQ;
DROP SEQUENCE MEMBERUSER_SEQUENCE;
DROP SEQUENCE REVIEW_SEQUENCE;
DROP SEQUENCE REVIEW_COMMENT_SEQUENCE;
DROP SEQUENCE PATH_SEQUENCE;
DROP SEQUENCE MARK_SEQUENCE;
DROP SEQUENCE FAVORITE_SEQUENCE;
DROP SEQUENCE PATH_COMMENT_SEQUENCE;



-- AREA_LARGE, AREA_SMALL은 초기화하지 않고 고정시킴 (API 호출 할당량이 초과될 수 있음)

--create view review_vi as
--select m.user_name,r.*
--    from MemberUser m join Review r
--        on m.user_id = r.user_id;

--CREATE TABLE testtable (
--    test_id NUMBER(10) NOT NULL,
--    test_name VARCHAR2(10) NOT NULL,
--    test_date DATE NOT NULL
--    );
--
--CREATE TABLE AREA_LARGE (
--    large_id NUMBER,
--    large_code VARCHAR2(5) NOT NULL,
--    large_name VARCHAR2(30) NOT NULL,
--    CONSTRAINT area_large_pk PRIMARY KEY(large_id),
--    CONSTRAINT area_large_uniq UNIQUE(large_code, large_name)
--);
--
--CREATE TABLE AREA_SMALL(
--    small_id NUMBER,
--    small_code VARCHAR2(5) NOT NULL,
--    small_name VARCHAR2(30) NOT NULL,
--    large_id_fk NUMBER NOT NULL,
--    CONSTRAINT area_small_pk PRIMARY KEY (small_id),
--    CONSTRAINT area_small_fk FOREIGN KEY (large_id_fk) REFERENCES AREA_LARGE (large_id) ON DELETE CASCADE
--);



-- Sequence 생성 및 삭제
CREATE SEQUENCE large_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;
--

-- Sequence 생성 및 삭제


CREATE SEQUENCE small_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;
--



-- 본 페이지 관련!!!!--------------


-- user => 예약어 MemberUser 로 대체
create table MemberUser(
    user_id Number primary key,
    user_name VARCHAR2(50) not null,
    user_pwd VARCHAR2(50) not null,
    user_email varchar2(50) not null,
    user_phone varchar2(50) not null,
    user_role varchar2(50) not null,
    constraint uniq_MemberUser UNIQUE(user_name, user_email, user_phone)
);

-- Review
create table Review(
    review_id Number primary key,
    user_id Number not null,
    review_title varchar2(50) not null,
    review_content varchar2(1000) not null,
    create_date Date not null,
    update_date Date not null,
    review_views Number not null,
    review_recommends Number not null,
    CONSTRAINT fk_userid FOREIGN key(user_id) REFERENCES MemberUser(user_id)
);


-- Review_comment
create table Review_Comment(
    comment_id Number primary key,
    review_id Number not null,
    create_date Date not null,
    update_date Date not null,
    content Varchar2(500) not null,
    comment_group Number not null,
    comment_depth Number not null,
    user_id int not null,
    constraint fk_review_id foreign key(review_id) references Review(review_id),
    constraint fk_comment_user_id foreign key(user_id) references MemberUser(user_id)
);


-- Path
create table Path(
    path_id Number primary key,
    user_id Number not null,
    create_date Date not null,
    update_date Date not null,
    path_title Varchar2(50) not null,
    path_views Number not null,
    path_recommends Number not null,
    constraint fk_path_user_id foreign key(user_id) references MemberUser(user_id)
);


-- mark
create table Mark(
    mark_id Number primary key,
    path_id Number not null,
    mark_title Varchar2(50),
    mark_addr1 varchar2(200) not null,
    mark_addr2 varchar2(50),
    mark_contentId Number not null,
    mark_contentType varchar2(30) not null,
    mark_firstImageURI varchar2(500),
    mark_firstImageURI2 varchar2(500),
    mark_posX Number not null,
    mark_posY Number not null,
    mark_tel Varchar2(15),
    mark_placeOrder Number not null,
    constraint fk_mark_path_id foreign key(path_id) references Path(path_id)
);


-- favorite
create table Favorite(
    favorite_id Number primary key,
    user_id Number not null,
    path_id Number not null,
    constraint fk_favorite_user_id foreign key(user_id) references MemberUser(user_id),
    constraint fk_favorite_path_id foreign key(path_id) references Path(path_id)
);

-- path_comment
create table path_comment(
    comment_id Number primary key,
    path_id Number not null,
    create_date Date not null,
    update_date Date not null,
    content Varchar2(500) not null,
    comment_group VARCHAR2(100) not null,
    comment_depth VARCHAR2(100) not null,
    user_id int not null,
    constraint fk_PC_path_id foreign key(path_id) references Path(path_id),
    constraint fk_PC_user_id foreign key(user_id) references MemberUser(user_id)
);

CREATE SEQUENCE MemberUser_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

CREATE SEQUENCE Review_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

CREATE SEQUENCE Review_Comment_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

CREATE SEQUENCE Path_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

CREATE SEQUENCE Mark_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

CREATE SEQUENCE Favorite_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

CREATE SEQUENCE Path_Comment_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;
```

<br>

## 경로
ㄴ controller : Root 경로에서 테스트하기 위한 컨트롤러 하나 있음  
ㄴ pathMap : 경로 맵 페이지 관련  
ㄴ security : 로그인 및 관리자 페이지 관련  
