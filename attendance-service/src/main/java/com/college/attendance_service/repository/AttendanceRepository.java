package com.college.attendance_service.repository;

import com.college.attendance_service.entity.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    boolean existsByAttendanceCode(String attendanceCode);

    boolean existsByStudentIdAndCourseIdAndAttendanceDate(
            Long studentId,
            Long courseId,
            java.time.LocalDate attendanceDate
    );
}