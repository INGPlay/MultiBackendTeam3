package com.plan.tour.mapper;

import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.domain.TestDto;
import com.plan.tour.pathMap.mapper.TestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
class TestMapperTest {

    @Autowired private TestMapper testMapper;


    @Test
    void test(){
        List<TestDto> testDtos = testMapper.testMapper();

        testDtos.forEach(d -> {
            log.info("내용 : {}, {}, {}", d.getTestId(), d.getTestName(), d.getTestDate());
        });

        String s = testMapper.showDate();

        log.info(s);
    }
}