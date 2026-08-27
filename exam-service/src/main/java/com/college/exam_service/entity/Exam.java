package com.college.exam_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Exam code is required")
    @Size(
            min = 2,
            max = 30,
            message = "Exam code must be between 2 and 30 characters"
    )
    @Column(
            name = "exam_code",
            nullable = false,
            unique = true,
            length = 30
    )
    private String examCode;

    @NotNull(message = "Course ID is required")
    @Min(
            value = 1,
            message = "Course ID must be positive"
    )
    @Column(
            name = "course_id",
            nullable = false
    )
    private Long courseId;

    @NotBlank(message = "Exam type is required")
    @Size(
            max = 30,
            message = "Exam type cannot exceed 30 characters"
    )
    @Column(
            name = "exam_type",
            nullable = false,
            length = 30
    )
    private String examType;

    @NotBlank(message = "Exam date is required")
    @Size(
            max = 20,
            message = "Exam date cannot exceed 20 characters"
    )
    @Column(
            name = "exam_date",
            nullable = false,
            length = 20
    )
    private String examDate;

    @NotNull(message = "Semester is required")
    @Min(
            value = 1,
            message = "Semester must be at least 1"
    )
    @Max(
            value = 8,
            message = "Semester cannot exceed 8"
    )
    @Column(nullable = false)
    private Integer semester;

    @NotNull(message = "Total marks are required")
    @Min(
            value = 1,
            message = "Total marks must be at least 1"
    )
    @Max(
            value = 1000,
            message = "Total marks cannot exceed 1000"
    )
    @Column(
            name = "total_marks",
            nullable = false
    )
    private Integer totalMarks;

    public Exam() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }
}