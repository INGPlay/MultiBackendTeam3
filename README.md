# MultiBackendTeam3

## 초기화

- keys.properties
```properties
## api 키를 저장하는 파일

keys.kakao.map=	{카카오맵 javascript 키}

keys.tour.info.encode= {인코딩 된 tour api}
keys.tour.info.decode= {디코딩 된 tour api}
```

resources/messages 경로에 api키를 저장하는 keys.properties가 빠져있음
(.gitignore에 포함됨)

위와 같은 양식으로 resource/messages 경로에 작성하면 됨

<br>

- resources/application.properties
```properites
# Oracle Connection Settings
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:orcl 
spring.datasource.username=project
spring.datasource.password=project

# ...
```
기본적으로 이렇게 되어 있는데 각 DB 환경에 맞게 수정하기

그런다음 .gitignore 마지막 줄에 다음을 추가 

```properties
# config
**/application.properties

# Init Query
**/*.sql
```

## 경로
ㄴ controller : Root 경로에서 테스트하기 위한 컨트롤러 하나 있음  
ㄴ pathMap : 경로 맵 페이지 관련  
ㄴ security : 로그인 및 관리자 페이지 관련  
