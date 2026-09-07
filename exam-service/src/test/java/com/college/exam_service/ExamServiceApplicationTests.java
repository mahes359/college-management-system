package com.college.exam_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.college.exam_service.service.RemoteCourseService;

@SpringBootTest
@ActiveProfiles("test")
class ExamServiceApplicationTests {

    @MockitoBean
    private RemoteCourseService remoteCourseService;

	@Test
	void contextLoads() {
	}

}
