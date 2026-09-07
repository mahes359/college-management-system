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

    private static final String NAMESPACE = "http://college.com/attendance";
    private final AttendanceService attendanceService;

    public AttendanceEndpoint(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createAttendanceRequest")
    @ResponsePayload
    public CreateAttendanceResponse createAttendance(@RequestPayload CreateAttendanceRequest request) {
        Attendance attendance = new Attendance();
        mapRequestToAttendance(request.getAttendanceCode(), request.getStudentId(), request.getCourseId(),
                request.getAttendanceDate(), request.getStatus(), request.getSemester(),
                request.getAcademicYear(), request.getAttendanceType(), request.getRemarks(), attendance);

        Attendance saved = attendanceService.createAttendance(attendance);
        CreateAttendanceResponse response = new CreateAttendanceResponse();
        response.setAttendance(toSoapAttendance(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getAttendanceRequest")
    @ResponsePayload
    public GetAttendanceResponse getAttendance(@RequestPayload GetAttendanceRequest request) {
        Attendance attendance = attendanceService.getById(request.getAttendanceId());
        GetAttendanceResponse response = new GetAttendanceResponse();
        response.setAttendance(toSoapAttendance(attendance));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getAllAttendanceRequest")
    @ResponsePayload
    public GetAllAttendanceResponse getAllAttendance(@RequestPayload GetAllAttendanceRequest request) {
        List<Attendance> records = attendanceService.getAllAttendance();
        GetAllAttendanceResponse response = new GetAllAttendanceResponse();
        for (Attendance attendance : records) {
            response.getAttendance().add(toSoapAttendance(attendance));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateAttendanceRequest")
    @ResponsePayload
    public UpdateAttendanceResponse updateAttendance(@RequestPayload UpdateAttendanceRequest request) {
        Attendance attendance = new Attendance();
        mapRequestToAttendance(request.getAttendanceCode(), request.getStudentId(), request.getCourseId(),
                request.getAttendanceDate(), request.getStatus(), request.getSemester(),
                request.getAcademicYear(), request.getAttendanceType(), request.getRemarks(), attendance);

        Attendance updated = attendanceService.updateAttendance(request.getAttendanceId(), attendance);
        UpdateAttendanceResponse response = new UpdateAttendanceResponse();
        response.setAttendance(toSoapAttendance(updated));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteAttendanceRequest")
    @ResponsePayload
    public DeleteAttendanceResponse deleteAttendance(@RequestPayload DeleteAttendanceRequest request) {
        attendanceService.deleteAttendance(request.getAttendanceId());
        DeleteAttendanceResponse response = new DeleteAttendanceResponse();
        response.setSuccess(true);
        response.setMessage("Attendance deleted successfully");
        return response;
    }

    private void mapRequestToAttendance(String attendanceCode, Long studentId, Long courseId,
                                        java.time.LocalDate attendanceDate, String status, Integer semester,
                                        String academicYear, String attendanceType, String remarks,
                                        Attendance attendance) {
        attendance.setAttendanceCode(attendanceCode);
        attendance.setStudentId(studentId);
        attendance.setCourseId(courseId);
        attendance.setAttendanceDate(attendanceDate);
        attendance.setStatus(status);
        attendance.setSemester(semester);
        attendance.setAcademicYear(academicYear);
        attendance.setAttendanceType(attendanceType);
        attendance.setRemarks(remarks);
    }

    private com.college.attendance_service.soap.Attendance toSoapAttendance(Attendance attendance) {
        com.college.attendance_service.soap.Attendance result = new com.college.attendance_service.soap.Attendance();
        result.setId(attendance.getId());
        result.setAttendanceCode(attendance.getAttendanceCode());
        result.setStudentId(attendance.getStudentId());
        result.setCourseId(attendance.getCourseId());
        result.setAttendanceDate(attendance.getAttendanceDate());
        result.setStatus(attendance.getStatus());
        result.setSemester(attendance.getSemester());
        result.setAcademicYear(attendance.getAcademicYear());
        result.setAttendanceType(attendance.getAttendanceType());
        result.setRemarks(attendance.getRemarks());
        return result;
    }
}