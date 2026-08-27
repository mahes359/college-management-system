package com.college.faculty.repository;

import com.college.faculty.entity.Faculty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository
        extends MongoRepository<Faculty, String> {

    boolean existsByEmployeeNumber(String employeeNumber);

    boolean existsByEmail(String email);
}