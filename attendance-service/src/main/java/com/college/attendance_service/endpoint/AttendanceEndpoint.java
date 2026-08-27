package com.college.attendance_service.endpoint;

import com.college.attendance_service.entity.Attendance;
import com.college.attendance_service.service.AttendanceService;
import com.college.attendance_service.soap.*;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class AttendanceEndpoint {

    private static final String NAMESPACE =
            "http://college.com/attendance";

    private final AttendanceService attendanceService;

    public AttendanceEndpoint(
            AttendanceService attendanceService) {

        this.attendanceService = attendanceService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "createAttendanceRequest"
    )
    @ResponsePayload
    public CreateAttendanceResponse createAttendance(
            @RequestPayload CreateAttendanceRequest request) {

        Attendance attendance =
                new Attendance();

        attendance.setAttendanceCode(
                request.getAttendanceCode()
        );

        attendance.setStudentId(
                request.getStudentId()
        );

        attendance.setCourseId(
                request.getCourseId()
        );

        attendance.setAttendanceDate(
                request.getAttendanceDate()
        );

        attendance.setStatus(
                request.getStatus()
        );

        Attendance saved =
                attendanceService.createAttendance(
                        attendance
                );

        CreateAttendanceResponse response =
                new CreateAttendanceResponse();

        response.setAttendance(
                toSoapAttendance(saved)
        );

        return response;
    }

    // =====================================================
    // GET
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getAttendanceRequest"
    )
    @ResponsePayload
    public GetAttendanceResponse getAttendance(
            @RequestPayload GetAttendanceRequest request) {

        Attendance attendance =
                attendanceService.getById(
                        request.getAttendanceId()
                );

        GetAttendanceResponse response =
                new GetAttendanceResponse();

        response.setAttendance(
                toSoapAttendance(attendance)
        );

        return response;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getAllAttendanceRequest"
    )
    @ResponsePayload
    public GetAllAttendanceResponse getAllAttendance(
            @RequestPayload GetAllAttendanceRequest request) {

        List<Attendance> records =
                attendanceService.getAllAttendance();

        GetAllAttendanceResponse response =
                new GetAllAttendanceResponse();

        for (Attendance attendance : records) {

            response.getAttendance().add(
                    toSoapAttendance(attendance)
            );
        }

        return response;
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "updateAttendanceRequest"
    )
    @ResponsePayload
    public UpdateAttendanceResponse updateAttendance(
            @RequestPayload UpdateAttendanceRequest request) {

        Attendance attendance =
                new Attendance();

        attendance.setAttendanceCode(
                request.getAttendanceCode()
        );

        attendance.setStudentId(
                request.getStudentId()
        );

        attendance.setCourseId(
                request.getCourseId()
        );

        attendance.setAttendanceDate(
                request.getAttendanceDate()
        );

        attendance.setStatus(
                request.getStatus()
        );

        Attendance updated =
                attendanceService.updateAttendance(
                        request.getAttendanceId(),
                        attendance
                );

        UpdateAttendanceResponse response =
                new UpdateAttendanceResponse();

        response.setAttendance(
                toSoapAttendance(updated)
        );

        return response;
    }

    // =====================================================
    // DELETE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "deleteAttendanceRequest"
    )
    @ResponsePayload
    public DeleteAttendanceResponse deleteAttendance(
            @RequestPayload DeleteAttendanceRequest request) {

        attendanceService.deleteAttendance(
                request.getAttendanceId()
        );

        DeleteAttendanceResponse response =
                new DeleteAttendanceResponse();

        response.setSuccess(true);

        response.setMessage(
                "Attendance deleted successfully"
        );

        return response;
    }

    // =====================================================
    // ENTITY -> SOAP
    // =====================================================

    private com.college.attendance_service.soap.Attendance
    toSoapAttendance(Attendance attendance) {

        com.college.attendance_service.soap.Attendance result =
                new com.college.attendance_service.soap.Attendance();

        result.setId(
                attendance.getId()
        );

        result.setAttendanceCode(
                attendance.getAttendanceCode()
        );

        result.setStudentId(
                attendance.getStudentId()
        );

        result.setCourseId(
                attendance.getCourseId()
        );

        result.setAttendanceDate(
                attendance.getAttendanceDate()
        );

        result.setStatus(
                attendance.getStatus()
        );

        return result;
    }
}