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

- schema.sql
```sql

-- ...

```

<br>

## 경로
ㄴ controller : Root 경로에서 테스트하기 위한 컨트롤러 하나 있음  
ㄴ pathMap : 경로 맵 페이지 관련  
ㄴ security : 로그인 및 관리자 페이지 관련  
