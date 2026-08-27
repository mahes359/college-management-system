package com.college.exam_service.endpoint;

import com.college.exam_service.entity.Exam;
import com.college.exam_service.service.ExamService;
import com.college.exam_service.soap.*;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ExamEndpoint {

    private static final String NAMESPACE =
            "http://college.com/exam";

    private final ExamService examService;

    public ExamEndpoint(
            ExamService examService) {

        this.examService = examService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "createExamRequest"
    )
    @ResponsePayload
    public CreateExamResponse createExam(
            @RequestPayload CreateExamRequest request) {

        Exam exam = new Exam();

        exam.setExamCode(
                request.getExamCode()
        );

        exam.setCourseId(
                request.getCourseId()
        );

        exam.setExamType(
                request.getExamType()
        );

        exam.setExamDate(
                request.getExamDate()
        );

        exam.setSemester(
                request.getSemester()
        );

        exam.setTotalMarks(
                request.getTotalMarks()
        );

        Exam saved =
                examService.createExam(exam);

        CreateExamResponse response =
                new CreateExamResponse();

        response.setExam(
                toSoapExam(saved)
        );

        return response;
    }

    // =====================================================
    // GET
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getExamRequest"
    )
    @ResponsePayload
    public GetExamResponse getExam(
            @RequestPayload GetExamRequest request) {

        Exam exam =
                examService.getById(
                        request.getExamId()
                );

        GetExamResponse response =
                new GetExamResponse();

        response.setExam(
                toSoapExam(exam)
        );

        return response;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getAllExamsRequest"
    )
    @ResponsePayload
    public GetAllExamsResponse getAllExams(
            @RequestPayload GetAllExamsRequest request) {

        List<Exam> exams =
                examService.getAllExams();

        GetAllExamsResponse response =
                new GetAllExamsResponse();

        for (Exam exam : exams) {

            response.getExams().add(
                    toSoapExam(exam)
            );
        }

        return response;
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "updateExamRequest"
    )
    @ResponsePayload
    public UpdateExamResponse updateExam(
            @RequestPayload UpdateExamRequest request) {

        Exam exam = new Exam();

        exam.setExamCode(
                request.getExamCode()
        );

        exam.setCourseId(
                request.getCourseId()
        );

        exam.setExamType(
                request.getExamType()
        );

        exam.setExamDate(
                request.getExamDate()
        );

        exam.setSemester(
                request.getSemester()
        );

        exam.setTotalMarks(
                request.getTotalMarks()
        );

        Exam updated =
                examService.updateExam(
                        request.getExamId(),
                        exam
                );

        UpdateExamResponse response =
                new UpdateExamResponse();

        response.setExam(
                toSoapExam(updated)
        );

        return response;
    }

    // =====================================================
    // DELETE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "deleteExamRequest"
    )
    @ResponsePayload
    public DeleteExamResponse deleteExam(
            @RequestPayload DeleteExamRequest request) {

        examService.deleteExam(
                request.getExamId()
        );

        DeleteExamResponse response =
                new DeleteExamResponse();

        response.setSuccess(true);

        response.setMessage(
                "Exam deleted successfully"
        );

        return response;
    }

    // =====================================================
    // ENTITY -> SOAP
    // =====================================================

    private com.college.exam_service.soap.Exam
    toSoapExam(Exam exam) {

        com.college.exam_service.soap.Exam soapExam =
                new com.college.exam_service.soap.Exam();

        soapExam.setId(
                exam.getId()
        );

        soapExam.setExamCode(
                exam.getExamCode()
        );

        soapExam.setCourseId(
                exam.getCourseId()
        );

        soapExam.setExamType(
                exam.getExamType()
        );

        soapExam.setExamDate(
                exam.getExamDate()
        );

        soapExam.setSemester(
                exam.getSemester()
        );

        soapExam.setTotalMarks(
                exam.getTotalMarks()
        );

        return soapExam;
    }
}