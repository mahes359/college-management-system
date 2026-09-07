package com.college.course.service;

import com.college.course.entity.Course;
import com.college.course.exception.CourseDatabaseException;
import com.college.course.exception.CourseNotFoundException;
import com.college.course.exception.CourseValidationException;
import com.college.course.exception.DuplicateCourseException;
import com.college.course.repository.CourseRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final Validator validator;

    public CourseService(CourseRepository courseRepository, Validator validator) {
        this.courseRepository = courseRepository;
        this.validator = validator;
    }

    public Course createCourse(Course course) {
        validateCourse(course);
        normalizeCourse(course);
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new DuplicateCourseException("Course code already exists: " + course.getCourseCode());
        }
        try {
            return courseRepository.save(course);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateCourseException("Course code already exists: " + course.getCourseCode());
        } catch (DataAccessException exception) {
            throw new CourseDatabaseException("Unable to save course", exception);
        }
    }

    public Course getById(Long id) {
        validateId(id);
        try {
            return courseRepository.findById(id)
                    .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
        } catch (CourseNotFoundException exception) {
            throw exception;
        } catch (DataAccessException exception) {
            throw new CourseDatabaseException("Unable to retrieve course", exception);
        }
    }

    public List<Course> getAllCourses() {
        try {
            return courseRepository.findAll();
        } catch (DataAccessException exception) {
            throw new CourseDatabaseException("Unable to retrieve courses", exception);
        }
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        validateId(id);
        validateCourse(updatedCourse);
        normalizeCourse(updatedCourse);
        Course existingCourse = getById(id);

        if (!existingCourse.getCourseCode().equalsIgnoreCase(updatedCourse.getCourseCode())
                && courseRepository.existsByCourseCode(updatedCourse.getCourseCode())) {
            throw new DuplicateCourseException("Course code already exists: " + updatedCourse.getCourseCode());
        }

        existingCourse.setCourseCode(updatedCourse.getCourseCode());
        existingCourse.setCourseName(updatedCourse.getCourseName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setDepartment(updatedCourse.getDepartment());
        existingCourse.setProgram(updatedCourse.getProgram());
        existingCourse.setCredits(updatedCourse.getCredits());
        existingCourse.setSemester(updatedCourse.getSemester());
        existingCourse.setAcademicYear(updatedCourse.getAcademicYear());
        existingCourse.setTheoryPractical(updatedCourse.getTheoryPractical());
        existingCourse.setCourseType(updatedCourse.getCourseType());
        existingCourse.setCapacity(updatedCourse.getCapacity());
        existingCourse.setPrerequisites(updatedCourse.getPrerequisites());
        existingCourse.setAssignedFaculty(updatedCourse.getAssignedFaculty());
        existingCourse.setCourseStatus(updatedCourse.getCourseStatus());

        try {
            return courseRepository.save(existingCourse);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateCourseException("Course code already exists: " + updatedCourse.getCourseCode());
        } catch (DataAccessException exception) {
            throw new CourseDatabaseException("Unable to update course", exception);
        }
    }

    public void deleteCourse(Long id) {
        validateId(id);
        Course course = getById(id);
        try {
            courseRepository.delete(course);
        } catch (DataAccessException exception) {
            throw new CourseDatabaseException("Unable to delete course", exception);
        }
    }

    private void validateCourse(Course course) {
        if (course == null) {
            throw new CourseValidationException("Course data cannot be null");
        }
        Set<ConstraintViolation<Course>> violations = validator.validate(course);
        if (!violations.isEmpty()) {
            StringBuilder error = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<Course> violation : violations) {
                error.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            }
            throw new CourseValidationException(error.toString());
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new CourseValidationException("Course ID must be a positive number");
        }
    }

    private void normalizeCourse(Course course) {
        if (course.getCourseCode() != null) {
            course.setCourseCode(course.getCourseCode().trim().toUpperCase());
        }
        if (course.getCourseName() != null) {
            course.setCourseName(course.getCourseName().trim());
        }
        if (course.getDepartment() != null) {
            course.setDepartment(course.getDepartment().trim());
        }
        if (course.getDescription() != null) {
            course.setDescription(course.getDescription().trim());
        }
    }
}