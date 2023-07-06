package com.plan.tour.review.service;

import lombok.extern.slf4j.Slf4j;
import com.plan.tour.review.vo.ReviewVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class reviewserviceimpltest {

    @Resource(name = "reviewService")
    private ReviewServiceImpl service;

    @Test
    public void getUserId(){
        System.out.println("테스트1번 실행");
        int n = 0;
        if(service == null){
            System.out.println("null");
        }else {
            n = service.getUserId("seoin");
        }
        System.out.println("GetUserID 호출"+n);

        }

    @Test
    public void getCommentCount(){
        System.out.println("테스트2번 실행");
        int n = 0;
        if(service == null){
            System.out.println("null");
        }else {
            n = service.getTotalRecoment(0);
        }
        System.out.println("totalCount"+n+"개");

    }

    @Test
    public void test_Recomments(){
        System.out.println("테스트3번 실행");
        int n =0;
        ReviewVO vo = service.selectReviewOne(1,"1");

        System.out.println("totalCount"+n+"개");
    }

    @Test
    public void test_updateReview(){
        System.out.println("테스트4번 실행");
        reviewVO rvo = service.selectReviewOne(21,"1");
        System.out.println(rvo);
    }
    }





