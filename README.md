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

-- ...

```

<br>

## 경로
ㄴ controller : Root 경로에서 테스트하기 위한 컨트롤러 하나 있음  
ㄴ pathMap : 경로 맵 페이지 관련  
ㄴ security : 로그인 및 관리자 페이지 관련  
