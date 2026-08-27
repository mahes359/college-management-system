package com.college.student.endpoint;

import com.college.student.entity.Student;
import com.college.student.service.StudentService;
import com.college.student.soap.*;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class StudentEndpoint {

    private static final String NAMESPACE =
            "http://college.com/student";

    private final StudentService studentService;

    public StudentEndpoint(
            StudentService studentService) {

        this.studentService = studentService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "createStudentRequest"
    )
    @ResponsePayload
    public CreateStudentResponse createStudent(
            @RequestPayload CreateStudentRequest request) {

        Student student = new Student();

        student.setStudentNumber(
                request.getStudentNumber()
        );

        student.setFirstName(
                request.getFirstName()
        );

        student.setLastName(
                request.getLastName()
        );

        student.setEmail(
                request.getEmail()
        );

        student.setPhone(
                request.getPhone()
        );

        student.setDepartment(
                request.getDepartment()
        );

        student.setYear(
                request.getYear()
        );

        Student savedStudent =
                studentService.createStudent(student);

        CreateStudentResponse response =
                new CreateStudentResponse();

        response.setStudent(
                toSoapStudent(savedStudent)
        );

        return response;
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getStudentRequest"
    )
    @ResponsePayload
    public GetStudentResponse getStudent(
            @RequestPayload GetStudentRequest request) {

        Student student =
                studentService.getById(
                        request.getStudentId()
                );

        GetStudentResponse response =
                new GetStudentResponse();

        response.setStudent(
                toSoapStudent(student)
        );

        return response;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getAllStudentsRequest"
    )
    @ResponsePayload
    public GetAllStudentsResponse getAllStudents(
            @RequestPayload GetAllStudentsRequest request) {

        List<Student> students =
                studentService.getAllStudents();

        GetAllStudentsResponse response =
                new GetAllStudentsResponse();

        for (Student student : students) {

            response.getStudents().add(
                    toSoapStudent(student)
            );
        }

        return response;
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "updateStudentRequest"
    )
    @ResponsePayload
    public UpdateStudentResponse updateStudent(
            @RequestPayload UpdateStudentRequest request) {

        Student student = new Student();

        student.setStudentNumber(
                request.getStudentNumber()
        );

        student.setFirstName(
                request.getFirstName()
        );

        student.setLastName(
                request.getLastName()
        );

        student.setEmail(
                request.getEmail()
        );

        student.setPhone(
                request.getPhone()
        );

        student.setDepartment(
                request.getDepartment()
        );

        student.setYear(
                request.getYear()
        );

        Student updatedStudent =
                studentService.updateStudent(
                        request.getStudentId(),
                        student
                );

        UpdateStudentResponse response =
                new UpdateStudentResponse();

        response.setStudent(
                toSoapStudent(updatedStudent)
        );

        return response;
    }

    // =====================================================
    // DELETE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "deleteStudentRequest"
    )
    @ResponsePayload
    public DeleteStudentResponse deleteStudent(
            @RequestPayload DeleteStudentRequest request) {

        studentService.deleteStudent(
                request.getStudentId()
        );

        DeleteStudentResponse response =
                new DeleteStudentResponse();

        response.setSuccess(true);

        response.setMessage(
                "Student deleted successfully"
        );

        return response;
    }

    // =====================================================
    // ENTITY -> SOAP OBJECT
    // =====================================================

    private com.college.student.soap.Student
    toSoapStudent(Student student) {

        com.college.student.soap.Student soapStudent =
                new com.college.student.soap.Student();

        soapStudent.setId(student.getId());

        soapStudent.setStudentNumber(
                student.getStudentNumber()
        );

        soapStudent.setFirstName(
                student.getFirstName()
        );

        soapStudent.setLastName(
                student.getLastName()
        );

        soapStudent.setEmail(
                student.getEmail()
        );

        soapStudent.setPhone(
                student.getPhone()
        );

        soapStudent.setDepartment(
                student.getDepartment()
        );

        soapStudent.setYear(
                student.getYear()
        );

        return soapStudent;
    }
}