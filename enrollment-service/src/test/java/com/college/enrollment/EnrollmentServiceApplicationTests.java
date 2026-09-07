package com.college.enrollment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.college.enrollment.service.RemoteSoapService;

@SpringBootTest
@ActiveProfiles("test")
class EnrollmentServiceApplicationTests {

    @MockitoBean
    private RemoteSoapService remoteSoapService;

	@Test
	void contextLoads() {
	}

}
