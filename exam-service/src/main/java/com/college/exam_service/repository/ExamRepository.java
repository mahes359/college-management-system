package com.college.exam_service.repository;

import com.college.exam_service.entity.Exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository
        extends JpaRepository<Exam, Long> {

    boolean existsByExamCode(String examCode);

    boolean existsByCourseIdAndExamType(
            Long courseId,
            String examType
    );
}