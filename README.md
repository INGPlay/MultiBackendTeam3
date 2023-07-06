# MultiBackendTeam3

## 초기화

### 프로젝트에 없는 파일 (.gitignore에 등록된 파일)

<details>
<summary><b>접기/펼치기</b></summary>

- keys.properties
```properties
## api 키를 저장하는 파일

keys.kakao.map=	{카카오맵 javascript 키}

keys.tour.info.encode= {인코딩 된 tour api}
keys.tour.info.decode= {디코딩 된 tour api}
```

<br>

- errorMessages.properties
```properties
Size.registerForm.username=유저이름은 {2}자에서 {1}자 길이의 문자로 이루어져야 합니다.
Size.registerForm.password=비밀번호는 {2}자에서 {1}자 길이의 문자로 이루어져야 합니다.
Size.registerForm.passwordCheck=비밀번호 확인은 {2}자에서 {1}자 길이의 문자로 이루어져야 합니다.
Size.registerForm.email=이메일은 {2}자에서 {1}자 길이의 문자로 이루어져야 합니다.
Size.registerForm.phone=전화번호는 {2}자에서 {1}자 길이의 숫자로 이루어져야 합니다.

NotNull.username=유저이름을 입력해주세요
NotNull.password=비밀번호를 입력해주세요
NotNull.passwrodCheck=비밀번호 확인란을 입력해주세요
NotNull.email=이메일을 입력해주세요
NotNull.phone=전화번호를 입력해주세요

Pattern.username=유저이름은 영어 소문자와 숫자로만 이루어져야 합니다.
Pattern.password=비밀번호는 영어와 숫자 특수문자(!, @, #, $, %)로만 이루어져야 합니다.
Pattern.passwordCheck=비밀번호 확인은 영어와 숫자 특수문자(!, @, #, $, %)로만 이루어져야 합니다.
Pattern.phone=전화번호를 다시 확인해주세요.

Email.email=이메일 형식에 맞지 않습니다

Size.updatePasswordForm.currentPassword=현재 비밀번호는 {2}자에서 {1}자 길이의 문자로 이루어져야 합니다.
Size.updatePasswordForm.newPassword=새 비밀번호는 {2}자에서 {1}자 길이의 문자로 이루어져야 합니다.
Size.updatePasswordForm.newPasswordCheck=새 비밀번호 확인은 {2}자에서 {1}자 길이의 문자로 이루어져야 합니다.

Pattern.currentPassword=비밀번호는 영어와 숫자 특수문자(!, @, #, $, %)로만 이루어져야 합니다.
Pattern.newPassword=비밀번호는 영어와 숫자 특수문자(!, @, #, $, %)로만 이루어져야 합니다.
Pattern.newPasswordCheck=비밀번호 확인은 영어와 숫자 특수문자(!, @, #, $, %)로만 이루어져야 합니다.
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
mybatis.type-aliases-package=com.plan.tour

# JDBC null settings
mybatis.configuration.jdbc-type-for-null=null


# JSP
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# SQL 
spring.sql.init.mode=always


spring.messages.basename=messages/keys, messages/errorMessages
spring.messages.encoding=UTF-8
```

<br>

- schema.sql
```sql
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


---- Sequence 생성 및 삭제
CREATE SEQUENCE small_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;


 본 페이지 관련!!!!--------------
-- user => 예약어 MemberUser 로 대체
create table MemberUser(
    user_id Number primary key,
    user_name VARCHAR2(50) not null,
    user_pwd VARCHAR2(60) not null,
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
    contentid Number not null,
    filename varchar2(500),
    originFilename varchar2(500),
    filesize number(8),
    CONSTRAINT fk_userid FOREIGN key(user_id) REFERENCES MemberUser(user_id) ON DELETE CASCADE
 );
 

create table Review(
    review_id Number primary key,
    user_id Number not null,
    review_title varchar2(50) not null,
    review_content varchar2(1000) not null,
    create_date Date not null,
    update_date Date not null,
    review_views Number not null,
    review_recommends Number not null,
    contentid Number not null,
    CONSTRAINT fk_userid FOREIGN key(user_id) REFERENCES MemberUser(user_id) ON DELETE CASCADE
 );


create table place(
    contentId Number primary key,
    contentName varchar2(150)
);


-- Review_comment
create table Review_Comment(
    comment_id Number primary key,
    review_id Number not null,
    create_date Date not null,
    update_date Date not null,
    content varchar2(300) not null,
    comment_group Number not null,
    comment_depth Number not null,
    user_id int not null,
    constraint fk_review_id foreign key(review_id) references Review(review_id) on DELETE CASCADE,
    constraint fk_comment_user_id foreign key(user_id) references MemberUser(user_id) on DELETE CASCADE
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
    path_starting_area varchar2(30) not null,
    path_destination_area varchar2(30) not null,
    constraint fk_path_user_id foreign key(user_id) references MemberUser(user_id) on DELETE CASCADE
 );


---- mark
 create table Mark(
    mark_id Number primary key,
    path_id Number not null,
    mark_title Varchar2(50),
    mark_addr1 varchar2(200) not null,
    mark_addr2 varchar2(50),
    mark_contentId Number not null,
    mark_contentType varchar2(30) not null,
    mark_contentTypeId varchar2(30) not null,
    mark_firstImageURI varchar2(500),
    mark_firstImageURI2 varchar2(500),
    mark_posX Number not null,
    mark_posY Number not null,
    mark_tel Varchar2(15),
    mark_placeOrder Number not null,
    mark_area varchar2(30) not null,
    constraint fk_mark_path_id foreign key(path_id) references Path(path_id) on DELETE CASCADE
 );

---- path_comment
  create table path_comment(
     comment_id Number primary key,
     path_id Number not null,
     create_date Date not null,
     update_date Date not null,
     content Varchar2(500) not null,
     comment_group Number not null,
     comment_depth Number not null,
     user_id Number not null,
     constraint fk_PC_path_id foreign key(path_id) references Path(path_id) on DELETE CASCADE,
     constraint fk_PC_user_id foreign key(user_id) references MemberUser(user_id) on DELETE CASCADE
  );


-- favorite
create table Favorite(
    favorite_id Number primary key,
    user_id Number not null,
    path_id Number not null,
    constraint fk_favorite_user_id foreign key(user_id) references MemberUser(user_id) on DELETE CASCADE,
    constraint fk_favorite_path_id foreign key(path_id) references Path(path_id) on DELETE CASCADE
);


create table review_recommends(
    recommends Number primary key,
    review_id Number,
    user_id Number,
    CONSTRAINT fk_recommend_review_id FOREIGN key(review_id) REFERENCES review(review_id) ON DELETE CASCADE,
    CONSTRAINT fk_recommend_user_id FOREIGN key(user_id) REFERENCES MemberUser(user_id) ON DELETE CASCADE
);

 CREATE SEQUENCE recommend_id
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

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

CREATE SEQUENCE Path_Comment_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

CREATE SEQUENCE Favorite_Sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOCYCLE;

---- view 추가
create view comment_vi as
    select m.user_name,r.*
        from MemberUser m join Review_Comment r
            on m.user_id = r.user_id;

create view review_vi as
select m.*,p.contentName
    from(select m.user_name,r.*
            from MemberUser m join Review r
            on m.user_id = r.user_id) m join place p on m.CONTENTID = p.CONTENTID;
```

<br>

</details>


## 화면 설계서

### 1. 메인 페이지
![main](images/main_draw.png)

#### 1-1. 헤더 (1. 메인페이지 그림 1번)
![header1](images/header_logout_draw.png)
![header2](images/header_login_draw.png)
![header3](images/header_login_admin_draw.png)

- 1-1. 헤더 그림 1번은 각 메인 페이지, [지도 페이지](#2-지도-페이지), [리뷰 페이지](#3-리뷰-페이지), [팀 페이지](#4-팀-페이지)로 이동하는 링크를 나타낸다.
- 1-1. 헤더 그림 2번과 같이 [로그인 페이지](#5-로그인-페이지) 및 [가입 페이지](#6-가입-페이지)로 이동하는 링크를 나타낸다.
- 1-1. 헤더 그림 3번은 로그인 여부에 따라 [마이 페이지](#7-마이-페이지) 및 로그아웃 버튼을 나타냄을 보여준다.
- 1-1. 헤더 그림 4번은 어드민 계정 로그인에 따라 [관리자 페이지](#8-관리자-페이지)로 이동하는 버튼을 추가적으로 나타냄을 보여준다.

#### 1-2. 바디 (1. 메인페이지 그림 2번)
![body1](images/body_button_logout_draw.png)
![body2](images/body_button_login_draw.png)

- 메인 페이지는 영상을 반복 재생된다.
- 1-2. 바디 그림 1번과 같이 [로그인 페이지](#5-로그인-페이지) 및 [가입 페이지](#6-가입-페이지)로 이동하는 링크를 나타낸다.
- 1-2. 바디 그림 2번은 로그인 여부에 따라 로그아웃 버튼이 나타냄을 보여준다.


#### 1-3. 푸터 (1. 메인페이지 그림 3번)
![footer](images/footer_draw.png)
- 1-3. 푸터 그림 1번의 링크를 클릭하면 이 리포지토리 페이지로 이동한다.

### 2. 지도 페이지

### 3. 리뷰 페이지

### 4. 팀 페이지

### 5. 로그인 페이지

### 6. 가입 페이지

### 7. 마이 페이지

### 8. 관리자 페이지


## 경로
ㄴ controller : Root 경로에서 테스트하기 위한 컨트롤러 하나 있음  
ㄴ pathMap : 경로 맵 페이지 관련  
ㄴ security : 로그인 및 관리자 페이지 관련  
