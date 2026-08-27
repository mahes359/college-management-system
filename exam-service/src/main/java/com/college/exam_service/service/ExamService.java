package com.college.exam_service.service;

import com.college.exam_service.entity.Exam;
import com.college.exam_service.exception.DuplicateExamException;
import com.college.exam_service.exception.ExamDatabaseException;
import com.college.exam_service.exception.ExamNotFoundException;
import com.college.exam_service.exception.ExamValidationException;
import com.college.exam_service.repository.ExamRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final Validator validator;
    private final RemoteCourseService remoteCourseService;

    public ExamService(
            ExamRepository examRepository,
            Validator validator,
            RemoteCourseService remoteCourseService) {

        this.examRepository = examRepository;
        this.validator = validator;
        this.remoteCourseService = remoteCourseService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    public Exam createExam(Exam exam) {

        validateExam(exam);

        normalize(exam);

        if (examRepository.existsByExamCode(
                exam.getExamCode())) {

            throw new DuplicateExamException(
                    "Exam code already exists: "
                            + exam.getExamCode()
            );
        }

        /*
         * Validate course through another microservice.
         */
        remoteCourseService.validateCourseExists(
                exam.getCourseId()
        );

        try {

            return examRepository.save(exam);

        } catch (DataIntegrityViolationException exception) {

            throw new DuplicateExamException(
                    "Exam code already exists: "
                            + exam.getExamCode()
            );

        } catch (DataAccessException exception) {

            throw new ExamDatabaseException(
                    "Unable to save exam",
                    exception
            );
        }
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public Exam getById(Long id) {

        validateId(id);

        try {

            return examRepository.findById(id)
                    .orElseThrow(() ->
                            new ExamNotFoundException(
                                    "Exam not found with id: "
                                            + id
                            )
                    );

        } catch (ExamNotFoundException exception) {

            throw exception;

        } catch (DataAccessException exception) {

            throw new ExamDatabaseException(
                    "Unable to retrieve exam",
                    exception
            );
        }
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<Exam> getAllExams() {

        try {

            return examRepository.findAll();

        } catch (DataAccessException exception) {

            throw new ExamDatabaseException(
                    "Unable to retrieve exams",
                    exception
            );
        }
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public Exam updateExam(
            Long id,
            Exam updatedExam) {

        validateId(id);

        validateExam(updatedExam);

        normalize(updatedExam);

        Exam existing =
                getById(id);

        if (!existing.getExamCode()
                .equalsIgnoreCase(
                        updatedExam.getExamCode()
                )
                && examRepository.existsByExamCode(
                updatedExam.getExamCode()
        )) {

            throw new DuplicateExamException(
                    "Exam code already exists: "
                            + updatedExam.getExamCode()
            );
        }

        boolean courseChanged =
                !existing.getCourseId()
                        .equals(
                                updatedExam.getCourseId()
                        );

        if (courseChanged) {

            remoteCourseService.validateCourseExists(
                    updatedExam.getCourseId()
            );
        }

        existing.setExamCode(
                updatedExam.getExamCode()
        );

        existing.setCourseId(
                updatedExam.getCourseId()
        );

        existing.setExamType(
                updatedExam.getExamType()
        );

        existing.setExamDate(
                updatedExam.getExamDate()
        );

        existing.setSemester(
                updatedExam.getSemester()
        );

        existing.setTotalMarks(
                updatedExam.getTotalMarks()
        );

        try {

            return examRepository.save(existing);

        } catch (DataIntegrityViolationException exception) {

            throw new DuplicateExamException(
                    "Duplicate exam data"
            );

        } catch (DataAccessException exception) {

            throw new ExamDatabaseException(
                    "Unable to update exam",
                    exception
            );
        }
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void deleteExam(Long id) {

        validateId(id);

        Exam exam =
                getById(id);

        try {

            examRepository.delete(exam);

        } catch (DataAccessException exception) {

            throw new ExamDatabaseException(
                    "Unable to delete exam",
                    exception
            );
        }
    }

    // =====================================================
    // VALIDATION
    // =====================================================

    private void validateExam(Exam exam) {

        if (exam == null) {

            throw new ExamValidationException(
                    "Exam data cannot be null"
            );
        }

        Set<ConstraintViolation<Exam>> violations =
                validator.validate(exam);

        if (!violations.isEmpty()) {

            StringBuilder message =
                    new StringBuilder(
                            "Validation failed: "
                    );

            for (
                    ConstraintViolation<Exam> violation
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

            throw new ExamValidationException(
                    message.toString()
            );
        }
    }

    private void validateId(Long id) {

        if (id == null || id <= 0) {

            throw new ExamValidationException(
                    "Exam ID must be a positive number"
            );
        }
    }

    // =====================================================
    // NORMALIZE
    // =====================================================

    private void normalize(Exam exam) {

        exam.setExamCode(
                exam.getExamCode()
                        .trim()
                        .toUpperCase()
        );

        exam.setExamType(
                exam.getExamType()
                        .trim()
                        .toUpperCase()
        );

        exam.setExamDate(
                exam.getExamDate()
                        .trim()
        );
    }
}