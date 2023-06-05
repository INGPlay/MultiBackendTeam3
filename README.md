# MultiBackendTeam3

resources/messages 경로에 api키를 저장하는 keys.properties가 빠져있음
(.gitignore에 포함됨)

아래와 같은 양식으로 resource/messages 경로에 작성하면 됨
```properties
# keys.properties
## api 키를 저장하는 파일

keys.kakao.map=	{카카오맵 javascript 키}

keys.tour.info.encode= {인코딩 된 tour api}
keys.tour.info.decode= {디코딩 된 tour api}
```