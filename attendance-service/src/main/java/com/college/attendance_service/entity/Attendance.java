package com.college.attendance_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Attendance code is required")
    @Size(min = 3, max = 30, message = "Attendance code must be between 3 and 30 characters")
    @Column(name = "attendance_code", nullable = false, unique = true, length = 30)
    private String attendanceCode;

    @NotNull(message = "Student ID is required")
    @Min(value = 1, message = "Student ID must be positive")
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotNull(message = "Course ID is required")
    @Min(value = 1, message = "Course ID must be positive")
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @NotNull(message = "Attendance date is required")
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @NotBlank(message = "Attendance status is required")
    @Column(nullable = false, length = 20)
    private String status;

    private Integer semester;

    @Column(name = "academic_year", length = 20)
    private String academicYear;

    @Column(name = "attendance_type", length = 50)
    private String attendanceType;

    @Column(length = 500)
    private String remarks;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (attendanceDate == null) {
            attendanceDate = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Attendance() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAttendanceCode() { return attendanceCode; }
    public void setAttendanceCode(String attendanceCode) { this.attendanceCode = attendanceCode; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public String getAttendanceType() { return attendanceType; }
    public void setAttendanceType(String attendanceType) { this.attendanceType = attendanceType; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}