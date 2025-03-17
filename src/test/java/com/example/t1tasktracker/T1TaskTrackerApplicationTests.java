package com.example.t1tasktracker;

import com.example.t1tasktracker.integration.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class T1TaskTrackerApplicationTests extends TestContainersConfig {

    @Test
    void contextLoads() {
    }

}
