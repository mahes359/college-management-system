package com.college.attendance_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Attendance code is required")
    @Size(
            min = 3,
            max = 30,
            message = "Attendance code must be between 3 and 30 characters"
    )
    @Column(
            name = "attendance_code",
            nullable = false,
            unique = true,
            length = 30
    )
    private String attendanceCode;

    @NotNull(message = "Student ID is required")
    @Min(value = 1, message = "Student ID must be positive")
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotNull(message = "Course ID is required")
    @Min(value = 1, message = "Course ID must be positive")
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @NotBlank(message = "Attendance date is required")
    @Size(max = 20, message = "Attendance date cannot exceed 20 characters")
    @Column(name = "attendance_date", nullable = false, length = 20)
    private String attendanceDate;

    @NotBlank(message = "Attendance status is required")
    @Column(nullable = false, length = 10)
    private String status;

    public Attendance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttendanceCode() {
        return attendanceCode;
    }

    public void setAttendanceCode(String attendanceCode) {
        this.attendanceCode = attendanceCode;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}