package com.college.enrollment.service;

import com.college.enrollment.entity.Enrollment;
import com.college.enrollment.exception.CourseServiceException;
import com.college.enrollment.exception.DuplicateEnrollmentException;
import com.college.enrollment.exception.EnrollmentDatabaseException;
import com.college.enrollment.exception.EnrollmentNotFoundException;
import com.college.enrollment.exception.EnrollmentValidationException;
import com.college.enrollment.exception.StudentServiceException;
import com.college.enrollment.repository.EnrollmentRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final Validator validator;
    private final RemoteSoapService remoteSoapService;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            Validator validator,
            RemoteSoapService remoteSoapService) {

        this.enrollmentRepository = enrollmentRepository;
        this.validator = validator;
        this.remoteSoapService = remoteSoapService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    public Enrollment createEnrollment(
            Enrollment enrollment) {

        validateEnrollment(enrollment);

        normalize(enrollment);

        if (enrollmentRepository
                .existsByEnrollmentCode(
                        enrollment.getEnrollmentCode())) {

            throw new DuplicateEnrollmentException(
                    "Enrollment code already exists: "
                            + enrollment.getEnrollmentCode()
            );
        }

        if (enrollmentRepository
                .existsByStudentIdAndCourseId(
                        enrollment.getStudentId(),
                        enrollment.getCourseId())) {

            throw new DuplicateEnrollmentException(
                    "Student is already enrolled in this course"
            );
        }

        // Distributed validation
        remoteSoapService.validateStudentExists(
                enrollment.getStudentId()
        );

        remoteSoapService.validateCourseExists(
                enrollment.getCourseId()
        );

        try {

            return enrollmentRepository.save(
                    enrollment
            );

        } catch (DataIntegrityViolationException exception) {

            throw new DuplicateEnrollmentException(
                    "Duplicate enrollment data"
            );

        } catch (DataAccessException exception) {

            throw new EnrollmentDatabaseException(
                    "Unable to save enrollment",
                    exception
            );
        }
    }

    // =====================================================
    // GET
    // =====================================================

    public Enrollment getById(Long id) {

        validateId(id);

        try {

            return enrollmentRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new EnrollmentNotFoundException(
                                    "Enrollment not found with id: "
                                            + id
                            )
                    );

        } catch (
                EnrollmentNotFoundException exception) {

            throw exception;

        } catch (DataAccessException exception) {

            throw new EnrollmentDatabaseException(
                    "Unable to retrieve enrollment",
                    exception
            );
        }
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<Enrollment> getAllEnrollments() {

        try {

            return enrollmentRepository.findAll();

        } catch (DataAccessException exception) {

            throw new EnrollmentDatabaseException(
                    "Unable to retrieve enrollments",
                    exception
            );
        }
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public Enrollment updateEnrollment(
            Long id,
            Enrollment updatedEnrollment) {

        validateId(id);

        validateEnrollment(
                updatedEnrollment
        );

        normalize(updatedEnrollment);

        Enrollment existing =
                getById(id);

        if (!existing.getEnrollmentCode()
                .equalsIgnoreCase(
                        updatedEnrollment
                                .getEnrollmentCode()
                )
                && enrollmentRepository
                .existsByEnrollmentCode(
                        updatedEnrollment
                                .getEnrollmentCode()
                )) {

            throw new DuplicateEnrollmentException(
                    "Enrollment code already exists: "
                            + updatedEnrollment
                            .getEnrollmentCode()
            );
        }

        boolean studentChanged =
                !existing.getStudentId()
                        .equals(
                                updatedEnrollment
                                        .getStudentId()
                        );

        boolean courseChanged =
                !existing.getCourseId()
                        .equals(
                                updatedEnrollment
                                        .getCourseId()
                        );

        if (studentChanged || courseChanged) {

            if (enrollmentRepository
                    .existsByStudentIdAndCourseId(
                            updatedEnrollment.getStudentId(),
                            updatedEnrollment.getCourseId()
                    )) {

                throw new DuplicateEnrollmentException(
                        "Student is already enrolled in this course"
                );
            }

            remoteSoapService.validateStudentExists(
                    updatedEnrollment.getStudentId()
            );

            remoteSoapService.validateCourseExists(
                    updatedEnrollment.getCourseId()
            );
        }

        existing.setEnrollmentCode(
                updatedEnrollment.getEnrollmentCode()
        );

        existing.setStudentId(
                updatedEnrollment.getStudentId()
        );

        existing.setCourseId(
                updatedEnrollment.getCourseId()
        );

        existing.setSemester(
                updatedEnrollment.getSemester()
        );

        existing.setStatus(
                updatedEnrollment.getStatus()
        );

        try {

            return enrollmentRepository.save(
                    existing
            );

        } catch (DataIntegrityViolationException exception) {

            throw new DuplicateEnrollmentException(
                    "Duplicate enrollment data"
            );

        } catch (DataAccessException exception) {

            throw new EnrollmentDatabaseException(
                    "Unable to update enrollment",
                    exception
            );
        }
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void deleteEnrollment(Long id) {

        validateId(id);

        Enrollment enrollment =
                getById(id);

        try {

            enrollmentRepository.delete(
                    enrollment
            );

        } catch (DataAccessException exception) {

            throw new EnrollmentDatabaseException(
                    "Unable to delete enrollment",
                    exception
            );
        }
    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private void validateEnrollment(
            Enrollment enrollment) {

        if (enrollment == null) {

            throw new EnrollmentValidationException(
                    "Enrollment data cannot be null"
            );
        }

        Set<ConstraintViolation<Enrollment>> violations =
                validator.validate(enrollment);

        if (!violations.isEmpty()) {

            StringBuilder message =
                    new StringBuilder(
                            "Validation failed: "
                    );

            for (
                    ConstraintViolation<Enrollment> violation
                    : violations) {

                message
                        .append(
                                violation.getPropertyPath()
                        )
                        .append(": ")
                        .append(
                                violation.getMessage()
                        )
                        .append("; ");
            }

            throw new EnrollmentValidationException(
                    message.toString()
            );
        }
    }

    private void validateId(Long id) {

        if (id == null || id <= 0) {

            throw new EnrollmentValidationException(
                    "Enrollment ID must be a positive number"
            );
        }
    }

    private void normalize(Enrollment enrollment) {

        enrollment.setEnrollmentCode(
                enrollment
                        .getEnrollmentCode()
                        .trim()
                        .toUpperCase()
        );

        enrollment.setStatus(
                enrollment
                        .getStatus()
                        .trim()
                        .toUpperCase()
        );
    }
}