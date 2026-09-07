package com.college.attendance_service.service;

import com.college.attendance_service.entity.Attendance;
import com.college.attendance_service.exception.AttendanceDatabaseException;
import com.college.attendance_service.exception.AttendanceNotFoundException;
import com.college.attendance_service.exception.AttendanceValidationException;
import com.college.attendance_service.exception.DuplicateAttendanceException;
import com.college.attendance_service.repository.AttendanceRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final Validator validator;
    private final RemoteStudentService remoteStudentService;

    public AttendanceService(AttendanceRepository attendanceRepository, Validator validator, RemoteStudentService remoteStudentService) {
        this.attendanceRepository = attendanceRepository;
        this.validator = validator;
        this.remoteStudentService = remoteStudentService;
    }

    public Attendance createAttendance(Attendance attendance) {
        validateAttendance(attendance);
        normalize(attendance);

        if (attendanceRepository.existsByAttendanceCode(attendance.getAttendanceCode())) {
            throw new DuplicateAttendanceException("Attendance code already exists: " + attendance.getAttendanceCode());
        }

        if (attendanceRepository.existsByStudentIdAndCourseIdAndAttendanceDate(
                attendance.getStudentId(), attendance.getCourseId(), attendance.getAttendanceDate())) {
            throw new DuplicateAttendanceException("Attendance already exists for this student, course and date");
        }

        remoteStudentService.validateStudentExists(attendance.getStudentId());

        try {
            return attendanceRepository.save(attendance);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateAttendanceException("Duplicate attendance data");
        } catch (DataAccessException exception) {
            throw new AttendanceDatabaseException("Unable to save attendance", exception);
        }
    }

    public Attendance getById(Long id) {
        validateId(id);
        try {
            return attendanceRepository.findById(id)
                    .orElseThrow(() -> new AttendanceNotFoundException("Attendance not found with id: " + id));
        } catch (AttendanceNotFoundException exception) {
            throw exception;
        } catch (DataAccessException exception) {
            throw new AttendanceDatabaseException("Unable to retrieve attendance", exception);
        }
    }

    public List<Attendance> getAllAttendance() {
        try {
            return attendanceRepository.findAll();
        } catch (DataAccessException exception) {
            throw new AttendanceDatabaseException("Unable to retrieve attendance records", exception);
        }
    }

    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
        validateId(id);
        validateAttendance(updatedAttendance);
        normalize(updatedAttendance);

        Attendance existing = getById(id);

        boolean identifyingFieldsChanged = !existing.getStudentId().equals(updatedAttendance.getStudentId())
                || !existing.getCourseId().equals(updatedAttendance.getCourseId())
                || !existing.getAttendanceDate().equals(updatedAttendance.getAttendanceDate());

        if (identifyingFieldsChanged) {
            if (attendanceRepository.existsByStudentIdAndCourseIdAndAttendanceDate(
                    updatedAttendance.getStudentId(), updatedAttendance.getCourseId(), updatedAttendance.getAttendanceDate())) {
                throw new DuplicateAttendanceException("Attendance already exists for this student, course and date");
            }
            remoteStudentService.validateStudentExists(updatedAttendance.getStudentId());
        }

        if (!existing.getAttendanceCode().equalsIgnoreCase(updatedAttendance.getAttendanceCode())
                && attendanceRepository.existsByAttendanceCode(updatedAttendance.getAttendanceCode())) {
            throw new DuplicateAttendanceException("Attendance code already exists: " + updatedAttendance.getAttendanceCode());
        }

        existing.setAttendanceCode(updatedAttendance.getAttendanceCode());
        existing.setStudentId(updatedAttendance.getStudentId());
        existing.setCourseId(updatedAttendance.getCourseId());
        existing.setAttendanceDate(updatedAttendance.getAttendanceDate());
        existing.setStatus(updatedAttendance.getStatus());
        existing.setSemester(updatedAttendance.getSemester());
        existing.setAcademicYear(updatedAttendance.getAcademicYear());
        existing.setAttendanceType(updatedAttendance.getAttendanceType());
        existing.setRemarks(updatedAttendance.getRemarks());

        try {
            return attendanceRepository.save(existing);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateAttendanceException("Duplicate attendance data");
        } catch (DataAccessException exception) {
            throw new AttendanceDatabaseException("Unable to update attendance", exception);
        }
    }

    public void deleteAttendance(Long id) {
        validateId(id);
        Attendance attendance = getById(id);
        try {
            attendanceRepository.delete(attendance);
        } catch (DataAccessException exception) {
            throw new AttendanceDatabaseException("Unable to delete attendance", exception);
        }
    }

    private void validateAttendance(Attendance attendance) {
        if (attendance == null) {
            throw new AttendanceValidationException("Attendance data cannot be null");
        }
        Set<ConstraintViolation<Attendance>> violations = validator.validate(attendance);
        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<Attendance> violation : violations) {
                message.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            }
            throw new AttendanceValidationException(message.toString());
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new AttendanceValidationException("Attendance ID must be positive");
        }
    }

    private void normalize(Attendance attendance) {
        if (attendance.getAttendanceCode() != null) {
            attendance.setAttendanceCode(attendance.getAttendanceCode().trim().toUpperCase());
        }
        if (attendance.getStatus() != null) {
            attendance.setStatus(attendance.getStatus().trim().toUpperCase());
        }
        if (attendance.getAcademicYear() != null) {
            attendance.setAcademicYear(attendance.getAcademicYear().trim());
        }
        if (attendance.getAttendanceType() != null) {
            attendance.setAttendanceType(attendance.getAttendanceType().trim());
        }
        if (attendance.getRemarks() != null) {
            attendance.setRemarks(attendance.getRemarks().trim());
        }
        if (!attendance.getStatus().equals("PRESENT") && !attendance.getStatus().equals("ABSENT") 
            && !attendance.getStatus().equals("LATE") && !attendance.getStatus().equals("EXCUSED")) {
            throw new AttendanceValidationException("Attendance status must be PRESENT, ABSENT, LATE, or EXCUSED");
        }
    }
}