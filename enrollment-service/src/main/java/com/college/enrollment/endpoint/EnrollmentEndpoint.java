package com.college.enrollment.endpoint;

import com.college.enrollment.entity.Enrollment;
import com.college.enrollment.service.EnrollmentService;
import com.college.enrollment.soap.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class EnrollmentEndpoint {

    private static final String NAMESPACE = "http://college.com/enrollment";
    private final EnrollmentService enrollmentService;

    public EnrollmentEndpoint(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createEnrollmentRequest")
    @ResponsePayload
    public CreateEnrollmentResponse createEnrollment(@RequestPayload CreateEnrollmentRequest request) {
        Enrollment enrollment = new Enrollment();
        mapRequestToEnrollment(request.getEnrollmentCode(), request.getStudentId(), request.getCourseId(),
                request.getSemester(), request.getStatus(), request.getEnrollmentDate(),
                request.getAcademicYear(), request.getEnrollmentType(), enrollment);

        Enrollment saved = enrollmentService.createEnrollment(enrollment);
        CreateEnrollmentResponse response = new CreateEnrollmentResponse();
        response.setEnrollment(toSoapEnrollment(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getEnrollmentRequest")
    @ResponsePayload
    public GetEnrollmentResponse getEnrollment(@RequestPayload GetEnrollmentRequest request) {
        Enrollment enrollment = enrollmentService.getById(request.getEnrollmentId());
        GetEnrollmentResponse response = new GetEnrollmentResponse();
        response.setEnrollment(toSoapEnrollment(enrollment));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getAllEnrollmentsRequest")
    @ResponsePayload
    public GetAllEnrollmentsResponse getAllEnrollments(@RequestPayload GetAllEnrollmentsRequest request) {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        GetAllEnrollmentsResponse response = new GetAllEnrollmentsResponse();
        for (Enrollment enrollment : enrollments) {
            response.getEnrollments().add(toSoapEnrollment(enrollment));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateEnrollmentRequest")
    @ResponsePayload
    public UpdateEnrollmentResponse updateEnrollment(@RequestPayload UpdateEnrollmentRequest request) {
        Enrollment enrollment = new Enrollment();
        mapRequestToEnrollment(request.getEnrollmentCode(), request.getStudentId(), request.getCourseId(),
                request.getSemester(), request.getStatus(), request.getEnrollmentDate(),
                request.getAcademicYear(), request.getEnrollmentType(), enrollment);

        Enrollment updated = enrollmentService.updateEnrollment(request.getEnrollmentId(), enrollment);
        UpdateEnrollmentResponse response = new UpdateEnrollmentResponse();
        response.setEnrollment(toSoapEnrollment(updated));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteEnrollmentRequest")
    @ResponsePayload
    public DeleteEnrollmentResponse deleteEnrollment(@RequestPayload DeleteEnrollmentRequest request) {
        enrollmentService.deleteEnrollment(request.getEnrollmentId());
        DeleteEnrollmentResponse response = new DeleteEnrollmentResponse();
        response.setSuccess(true);
        response.setMessage("Enrollment deleted successfully");
        return response;
    }

    private void mapRequestToEnrollment(String enrollmentCode, Long studentId, Long courseId,
                                        Integer semester, String status, java.time.LocalDate enrollmentDate,
                                        String academicYear, String enrollmentType, Enrollment enrollment) {
        enrollment.setEnrollmentCode(enrollmentCode);
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setSemester(semester);
        enrollment.setStatus(status);
        enrollment.setEnrollmentDate(enrollmentDate);
        enrollment.setAcademicYear(academicYear);
        enrollment.setEnrollmentType(enrollmentType);
    }

    private com.college.enrollment.soap.Enrollment toSoapEnrollment(Enrollment enrollment) {
        com.college.enrollment.soap.Enrollment result = new com.college.enrollment.soap.Enrollment();
        result.setId(enrollment.getId());
        result.setEnrollmentCode(enrollment.getEnrollmentCode());
        result.setStudentId(enrollment.getStudentId());
        result.setCourseId(enrollment.getCourseId());
        result.setSemester(enrollment.getSemester());
        result.setStatus(enrollment.getStatus());
        result.setEnrollmentDate(enrollment.getEnrollmentDate());
        result.setAcademicYear(enrollment.getAcademicYear());
        result.setEnrollmentType(enrollment.getEnrollmentType());
        return result;
    }
}