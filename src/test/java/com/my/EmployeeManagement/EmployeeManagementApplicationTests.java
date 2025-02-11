package com.my.EmployeeManagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@ComponentScan(basePackages = "com.my")
class EmployeeManagementApplicationTests {

    @Test
    void contextLoads() {
    }
}
