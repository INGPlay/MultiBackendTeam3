drop table testtable;
drop table area_small;
drop table area_large;
Drop SEQUENCE large_id_seq;
Drop SEQUENCE small_id_seq;

CREATE TABLE testtable (
    test_id NUMBER(10) NOT NULL,
    test_name VARCHAR2(10) NOT NULL,
    test_date DATE NOT NULL
    );

CREATE TABLE AREA_LARGE (
    large_id NUMBER,
    large_code VARCHAR2(5) NOT NULL,
    large_name VARCHAR2(30) NOT NULL,
    CONSTRAINT area_large_pk PRIMARY KEY(large_id),
    CONSTRAINT area_large_uniq UNIQUE(large_code, large_name)
);

CREATE TABLE AREA_SMALL(
    small_id NUMBER,
    small_code VARCHAR2(5) NOT NULL,
    small_name VARCHAR2(30) NOT NULL,
    large_id_fk NUMBER NOT NULL,
    CONSTRAINT area_small_pk PRIMARY KEY (small_id),
    CONSTRAINT area_small_fk FOREIGN KEY (large_id_fk) REFERENCES AREA_LARGE (large_id) ON DELETE CASCADE
);


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

/*create table MemberUser(
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

-- Member & review join view
  create view review_vi as
    select m.user_name,r.*
        from MemberUser m join Review r
            on m.user_id = r.user_id;

-- sequence
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

  */