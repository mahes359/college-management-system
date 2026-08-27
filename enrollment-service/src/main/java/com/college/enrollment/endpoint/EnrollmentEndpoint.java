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

    private static final String NAMESPACE =
            "http://college.com/enrollment";

    private final EnrollmentService enrollmentService;

    public EnrollmentEndpoint(
            EnrollmentService enrollmentService) {

        this.enrollmentService = enrollmentService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "createEnrollmentRequest"
    )
    @ResponsePayload
    public CreateEnrollmentResponse createEnrollment(
            @RequestPayload CreateEnrollmentRequest request) {

        Enrollment enrollment =
                new Enrollment();

        enrollment.setEnrollmentCode(
                request.getEnrollmentCode()
        );

        enrollment.setStudentId(
                request.getStudentId()
        );

        enrollment.setCourseId(
                request.getCourseId()
        );

        enrollment.setSemester(
                request.getSemester()
        );

        enrollment.setStatus(
                request.getStatus()
        );

        Enrollment saved =
                enrollmentService.createEnrollment(
                        enrollment
                );

        CreateEnrollmentResponse response =
                new CreateEnrollmentResponse();

        response.setEnrollment(
                toSoapEnrollment(saved)
        );

        return response;
    }

    // =====================================================
    // GET
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getEnrollmentRequest"
    )
    @ResponsePayload
    public GetEnrollmentResponse getEnrollment(
            @RequestPayload GetEnrollmentRequest request) {

        Enrollment enrollment =
                enrollmentService.getById(
                        request.getEnrollmentId()
                );

        GetEnrollmentResponse response =
                new GetEnrollmentResponse();

        response.setEnrollment(
                toSoapEnrollment(enrollment)
        );

        return response;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getAllEnrollmentsRequest"
    )
    @ResponsePayload
    public GetAllEnrollmentsResponse getAllEnrollments(
            @RequestPayload GetAllEnrollmentsRequest request) {

        List<Enrollment> enrollments =
                enrollmentService.getAllEnrollments();

        GetAllEnrollmentsResponse response =
                new GetAllEnrollmentsResponse();

        for (Enrollment enrollment : enrollments) {

            response.getEnrollments().add(
                    toSoapEnrollment(enrollment)
            );
        }

        return response;
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "updateEnrollmentRequest"
    )
    @ResponsePayload
    public UpdateEnrollmentResponse updateEnrollment(
            @RequestPayload UpdateEnrollmentRequest request) {

        Enrollment enrollment =
                new Enrollment();

        enrollment.setEnrollmentCode(
                request.getEnrollmentCode()
        );

        enrollment.setStudentId(
                request.getStudentId()
        );

        enrollment.setCourseId(
                request.getCourseId()
        );

        enrollment.setSemester(
                request.getSemester()
        );

        enrollment.setStatus(
                request.getStatus()
        );

        Enrollment updated =
                enrollmentService.updateEnrollment(
                        request.getEnrollmentId(),
                        enrollment
                );

        UpdateEnrollmentResponse response =
                new UpdateEnrollmentResponse();

        response.setEnrollment(
                toSoapEnrollment(updated)
        );

        return response;
    }

    // =====================================================
    // DELETE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "deleteEnrollmentRequest"
    )
    @ResponsePayload
    public DeleteEnrollmentResponse deleteEnrollment(
            @RequestPayload DeleteEnrollmentRequest request) {

        enrollmentService.deleteEnrollment(
                request.getEnrollmentId()
        );

        DeleteEnrollmentResponse response =
                new DeleteEnrollmentResponse();

        response.setSuccess(true);

        response.setMessage(
                "Enrollment deleted successfully"
        );

        return response;
    }

    // =====================================================
    // ENTITY -> SOAP
    // =====================================================

    private com.college.enrollment.soap.Enrollment
    toSoapEnrollment(
            Enrollment enrollment) {

        com.college.enrollment.soap.Enrollment result =
                new com.college.enrollment.soap.Enrollment();

        result.setId(
                enrollment.getId()
        );

        result.setEnrollmentCode(
                enrollment.getEnrollmentCode()
        );

        result.setStudentId(
                enrollment.getStudentId()
        );

        result.setCourseId(
                enrollment.getCourseId()
        );

        result.setSemester(
                enrollment.getSemester()
        );

        result.setStatus(
                enrollment.getStatus()
        );

        return result;
    }
}