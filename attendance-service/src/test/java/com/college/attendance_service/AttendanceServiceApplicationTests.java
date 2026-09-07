package com.college.attendance_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.college.attendance_service.service.RemoteStudentService;

@SpringBootTest
@ActiveProfiles("test")
class AttendanceServiceApplicationTests {

    @MockitoBean
    private RemoteStudentService remoteStudentService;

	@Test
	void contextLoads() {
	}

}
