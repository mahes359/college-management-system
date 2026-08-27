package com.college.enrollment.repository;

import com.college.enrollment.entity.Enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    boolean existsByEnrollmentCode(String enrollmentCode);

    boolean existsByStudentIdAndCourseId(
            Long studentId,
            Long courseId
    );
}