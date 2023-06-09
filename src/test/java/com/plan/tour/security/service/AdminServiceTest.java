package com.plan.tour.security.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class AdminServiceTest {

    @Autowired private AdminService adminService;

    @Test
    @Transactional
    void deleteUser() {
        adminService.deleteUser(1L);
    }
}