package multi.backend.project.review.Service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Transactional
@SpringBootTest
public
class reviewServiceImplTest {
    @Autowired reviewServiceImpl test;

    @Test
    public void getUserId(){
        System.out.println("테스트1번 실행");
        test.getUserId("seoin");
        System.out.println("GetUserID 호출");

        }
    }
