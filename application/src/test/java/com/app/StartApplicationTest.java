package com.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.service.MyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartApplicationTest {

    @Autowired
    private MyService myService;

    @Test
    public void contextLoads() {
        assertThat(myService).isNotNull();
    }

}
