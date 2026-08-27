package com.college.student.service;

import com.college.student.entity.Student;
import com.college.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(
            StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    // CREATE
    public Student createStudent(Student student) {

        return studentRepository.save(student);
    }

    // GET BY ID
    public Student getById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + id
                        )
                );
    }

    // GET ALL
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    // UPDATE
    public Student updateStudent(
            Long id,
            Student updatedStudent) {

        Student existingStudent = getById(id);

        existingStudent.setStudentNumber(
                updatedStudent.getStudentNumber()
        );

        existingStudent.setFirstName(
                updatedStudent.getFirstName()
        );

        existingStudent.setLastName(
                updatedStudent.getLastName()
        );

        existingStudent.setEmail(
                updatedStudent.getEmail()
        );

        existingStudent.setPhone(
                updatedStudent.getPhone()
        );

        existingStudent.setDepartment(
                updatedStudent.getDepartment()
        );

        existingStudent.setYear(
                updatedStudent.getYear()
        );

        return studentRepository.save(existingStudent);
    }

    // DELETE
    public void deleteStudent(Long id) {

        Student student = getById(id);

        studentRepository.delete(student);
    }
}