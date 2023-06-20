package multi.backend.project.pathMap.service;

import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.pathmap.PathInfoResponse;
import multi.backend.project.pathMap.domain.pathmap.paging.PathThreadPageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@SpringBootTest
class PathMapServiceTest {

    @Autowired
    PathMapService pathMapService;
}