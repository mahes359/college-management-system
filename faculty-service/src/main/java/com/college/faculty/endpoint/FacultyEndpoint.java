package com.college.faculty.endpoint;

import com.college.faculty.entity.Faculty;
import com.college.faculty.service.FacultyService;
import com.college.faculty.soap.*;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class FacultyEndpoint {

    private static final String NAMESPACE =
            "http://college.com/faculty";

    private final FacultyService facultyService;

    public FacultyEndpoint(
            FacultyService facultyService) {

        this.facultyService = facultyService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "createFacultyRequest"
    )
    @ResponsePayload
    public CreateFacultyResponse createFaculty(
            @RequestPayload CreateFacultyRequest request) {

        Faculty faculty = new Faculty();

        faculty.setEmployeeNumber(
                request.getEmployeeNumber()
        );

        faculty.setFirstName(
                request.getFirstName()
        );

        faculty.setLastName(
                request.getLastName()
        );

        faculty.setEmail(
                request.getEmail()
        );

        faculty.setPhone(
                request.getPhone()
        );

        faculty.setDepartment(
                request.getDepartment()
        );

        faculty.setDesignation(
                request.getDesignation()
        );

        Faculty savedFaculty =
                facultyService.createFaculty(faculty);

        CreateFacultyResponse response =
                new CreateFacultyResponse();

        response.setFaculty(
                toSoapFaculty(savedFaculty)
        );

        return response;
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getFacultyRequest"
    )
    @ResponsePayload
    public GetFacultyResponse getFaculty(
            @RequestPayload GetFacultyRequest request) {

        Faculty faculty =
                facultyService.getById(
                        request.getFacultyId()
                );

        GetFacultyResponse response =
                new GetFacultyResponse();

        response.setFaculty(
                toSoapFaculty(faculty)
        );

        return response;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getAllFacultyRequest"
    )
    @ResponsePayload
    public GetAllFacultyResponse getAllFaculty(
            @RequestPayload GetAllFacultyRequest request) {

        List<Faculty> faculties =
                facultyService.getAllFaculty();

        GetAllFacultyResponse response =
                new GetAllFacultyResponse();

        for (Faculty faculty : faculties) {

            response.getFaculty().add(
                    toSoapFaculty(faculty)
            );
        }

        return response;
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "updateFacultyRequest"
    )
    @ResponsePayload
    public UpdateFacultyResponse updateFaculty(
            @RequestPayload UpdateFacultyRequest request) {

        Faculty faculty = new Faculty();

        faculty.setEmployeeNumber(
                request.getEmployeeNumber()
        );

        faculty.setFirstName(
                request.getFirstName()
        );

        faculty.setLastName(
                request.getLastName()
        );

        faculty.setEmail(
                request.getEmail()
        );

        faculty.setPhone(
                request.getPhone()
        );

        faculty.setDepartment(
                request.getDepartment()
        );

        faculty.setDesignation(
                request.getDesignation()
        );

        Faculty updatedFaculty =
                facultyService.updateFaculty(
                        request.getFacultyId(),
                        faculty
                );

        UpdateFacultyResponse response =
                new UpdateFacultyResponse();

        response.setFaculty(
                toSoapFaculty(updatedFaculty)
        );

        return response;
    }

    // =====================================================
    // DELETE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "deleteFacultyRequest"
    )
    @ResponsePayload
    public DeleteFacultyResponse deleteFaculty(
            @RequestPayload DeleteFacultyRequest request) {

        facultyService.deleteFaculty(
                request.getFacultyId()
        );

        DeleteFacultyResponse response =
                new DeleteFacultyResponse();

        response.setSuccess(true);

        response.setMessage(
                "Faculty deleted successfully"
        );

        return response;
    }

    // =====================================================
    // ENTITY -> SOAP
    // =====================================================

    private com.college.faculty.soap.Faculty
    toSoapFaculty(Faculty faculty) {

        com.college.faculty.soap.Faculty soapFaculty =
                new com.college.faculty.soap.Faculty();

        soapFaculty.setId(faculty.getId());

        soapFaculty.setEmployeeNumber(
                faculty.getEmployeeNumber()
        );

        soapFaculty.setFirstName(
                faculty.getFirstName()
        );

        soapFaculty.setLastName(
                faculty.getLastName()
        );

        soapFaculty.setEmail(
                faculty.getEmail()
        );

        soapFaculty.setPhone(
                faculty.getPhone()
        );

        soapFaculty.setDepartment(
                faculty.getDepartment()
        );

        soapFaculty.setDesignation(
                faculty.getDesignation()
        );

        return soapFaculty;
    }
}