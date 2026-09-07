package com.college.enrollment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Enrollment code is required")
    @Size(min = 3, max = 30, message = "Enrollment code must be between 3 and 30 characters")
    @Column(name = "enrollment_code", nullable = false, unique = true, length = 30)
    private String enrollmentCode;

    @NotNull(message = "Student ID is required")
    @Min(value = 1, message = "Student ID must be positive")
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotNull(message = "Course ID is required")
    @Min(value = 1, message = "Course ID must be positive")
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 8, message = "Semester cannot exceed 8")
    @Column(nullable = false)
    private Integer semester;

    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status cannot exceed 20 characters")
    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    @Column(name = "academic_year", length = 20)
    private String academicYear;

    @Column(name = "enrollment_type", length = 50)
    private String enrollmentType;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (enrollmentDate == null) {
            enrollmentDate = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Enrollment() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEnrollmentCode() { return enrollmentCode; }
    public void setEnrollmentCode(String enrollmentCode) { this.enrollmentCode = enrollmentCode; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public String getEnrollmentType() { return enrollmentType; }
    public void setEnrollmentType(String enrollmentType) { this.enrollmentType = enrollmentType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}