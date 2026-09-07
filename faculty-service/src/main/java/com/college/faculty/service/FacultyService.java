package com.college.faculty.service;

import com.college.faculty.entity.Faculty;
import com.college.faculty.exception.DuplicateFacultyException;
import com.college.faculty.exception.FacultyNotFoundException;
import com.college.faculty.exception.FacultyValidationException;
import com.college.faculty.repository.FacultyRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final Validator validator;

    public FacultyService(FacultyRepository facultyRepository, Validator validator) {
        this.facultyRepository = facultyRepository;
        this.validator = validator;
    }

    public Faculty createFaculty(Faculty faculty) {
        validateFaculty(faculty);
        if (facultyRepository.existsByEmployeeNumber(faculty.getEmployeeNumber())) {
            throw new DuplicateFacultyException("Employee number already exists: " + faculty.getEmployeeNumber());
        }
        if (facultyRepository.existsByEmail(faculty.getEmail())) {
            throw new DuplicateFacultyException("Email already exists: " + faculty.getEmail());
        }
        try {
            return facultyRepository.save(faculty);
        } catch (DuplicateKeyException exception) {
            throw new DuplicateFacultyException("Employee number or email already exists");
        }
    }

    public Faculty getById(String id) {
        validateId(id);
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException("Faculty not found with id: " + id));
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Faculty updateFaculty(String id, Faculty updatedFaculty) {
        validateId(id);
        validateFaculty(updatedFaculty);
        Faculty existingFaculty = getById(id);

        if (!existingFaculty.getEmployeeNumber().equals(updatedFaculty.getEmployeeNumber())
                && facultyRepository.existsByEmployeeNumber(updatedFaculty.getEmployeeNumber())) {
            throw new DuplicateFacultyException("Employee number already exists: " + updatedFaculty.getEmployeeNumber());
        }

        if (!existingFaculty.getEmail().equalsIgnoreCase(updatedFaculty.getEmail())
                && facultyRepository.existsByEmail(updatedFaculty.getEmail())) {
            throw new DuplicateFacultyException("Email already exists: " + updatedFaculty.getEmail());
        }

        existingFaculty.setEmployeeNumber(updatedFaculty.getEmployeeNumber());
        existingFaculty.setFirstName(updatedFaculty.getFirstName());
        existingFaculty.setLastName(updatedFaculty.getLastName());
        existingFaculty.setEmail(updatedFaculty.getEmail());
        existingFaculty.setPhone(updatedFaculty.getPhone());
        existingFaculty.setDepartment(updatedFaculty.getDepartment());
        existingFaculty.setDesignation(updatedFaculty.getDesignation());
        existingFaculty.setQualification(updatedFaculty.getQualification());
        existingFaculty.setSpecialization(updatedFaculty.getSpecialization());
        existingFaculty.setJoiningDate(updatedFaculty.getJoiningDate());
        existingFaculty.setFacultyStatus(updatedFaculty.getFacultyStatus());
        existingFaculty.setOfficeRoom(updatedFaculty.getOfficeRoom());
        existingFaculty.setFacultyRole(updatedFaculty.getFacultyRole());
        existingFaculty.setUpdatedAt(LocalDateTime.now());

        try {
            return facultyRepository.save(existingFaculty);
        } catch (DuplicateKeyException exception) {
            throw new DuplicateFacultyException("Employee number or email already exists");
        }
    }

    public void deleteFaculty(String id) {
        validateId(id);
        Faculty faculty = getById(id);
        facultyRepository.delete(faculty);
    }

    private void validateFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new FacultyValidationException("Faculty data cannot be null");
        }
        normalizeFaculty(faculty);
        Set<ConstraintViolation<Faculty>> violations = validator.validate(faculty);
        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<Faculty> violation : violations) {
                message.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            }
            throw new FacultyValidationException(message.toString());
        }
    }

    private void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new FacultyValidationException("Faculty ID is required");
        }
    }

    private void normalizeFaculty(Faculty faculty) {
        if (faculty.getEmployeeNumber() != null) faculty.setEmployeeNumber(faculty.getEmployeeNumber().trim());
        if (faculty.getFirstName() != null) faculty.setFirstName(faculty.getFirstName().trim());
        if (faculty.getLastName() != null) faculty.setLastName(faculty.getLastName().trim());
        if (faculty.getEmail() != null) faculty.setEmail(faculty.getEmail().trim().toLowerCase());
        if (faculty.getPhone() != null) faculty.setPhone(faculty.getPhone().trim());
        if (faculty.getDepartment() != null) faculty.setDepartment(faculty.getDepartment().trim());
        if (faculty.getDesignation() != null) faculty.setDesignation(faculty.getDesignation().trim());
    }
}