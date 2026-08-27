package com.college.course.endpoint;

import com.college.course.entity.Course;
import com.college.course.service.CourseService;
import com.college.course.soap.*;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseEndpoint {

    private static final String NAMESPACE =
            "http://college.com/course";

    private final CourseService courseService;

    public CourseEndpoint(
            CourseService courseService) {

        this.courseService = courseService;
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "createCourseRequest"
    )
    @ResponsePayload
    public CreateCourseResponse createCourse(
            @RequestPayload CreateCourseRequest request) {

        Course course = new Course();

        course.setCourseCode(
                request.getCourseCode()
        );

        course.setCourseName(
                request.getCourseName()
        );

        course.setDescription(
                request.getDescription()
        );

        course.setDepartment(
                request.getDepartment()
        );

        course.setCredits(
                request.getCredits()
        );

        course.setSemester(
                request.getSemester()
        );

        Course savedCourse =
                courseService.createCourse(course);

        CreateCourseResponse response =
                new CreateCourseResponse();

        response.setCourse(
                toSoapCourse(savedCourse)
        );

        return response;
    }

    // =====================================================
    // GET
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getCourseRequest"
    )
    @ResponsePayload
    public GetCourseResponse getCourse(
            @RequestPayload GetCourseRequest request) {

        Course course =
                courseService.getById(
                        request.getCourseId()
                );

        GetCourseResponse response =
                new GetCourseResponse();

        response.setCourse(
                toSoapCourse(course)
        );

        return response;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "getAllCoursesRequest"
    )
    @ResponsePayload
    public GetAllCoursesResponse getAllCourses(
            @RequestPayload GetAllCoursesRequest request) {

        List<Course> courses =
                courseService.getAllCourses();

        GetAllCoursesResponse response =
                new GetAllCoursesResponse();

        for (Course course : courses) {

            response.getCourses().add(
                    toSoapCourse(course)
            );
        }

        return response;
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "updateCourseRequest"
    )
    @ResponsePayload
    public UpdateCourseResponse updateCourse(
            @RequestPayload UpdateCourseRequest request) {

        Course course = new Course();

        course.setCourseCode(
                request.getCourseCode()
        );

        course.setCourseName(
                request.getCourseName()
        );

        course.setDescription(
                request.getDescription()
        );

        course.setDepartment(
                request.getDepartment()
        );

        course.setCredits(
                request.getCredits()
        );

        course.setSemester(
                request.getSemester()
        );

        Course updatedCourse =
                courseService.updateCourse(
                        request.getCourseId(),
                        course
                );

        UpdateCourseResponse response =
                new UpdateCourseResponse();

        response.setCourse(
                toSoapCourse(updatedCourse)
        );

        return response;
    }

    // =====================================================
    // DELETE
    // =====================================================

    @PayloadRoot(
            namespace = NAMESPACE,
            localPart = "deleteCourseRequest"
    )
    @ResponsePayload
    public DeleteCourseResponse deleteCourse(
            @RequestPayload DeleteCourseRequest request) {

        courseService.deleteCourse(
                request.getCourseId()
        );

        DeleteCourseResponse response =
                new DeleteCourseResponse();

        response.setSuccess(true);

        response.setMessage(
                "Course deleted successfully"
        );

        return response;
    }

    // =====================================================
    // ENTITY -> SOAP
    // =====================================================

    private com.college.course.soap.Course
    toSoapCourse(Course course) {

        com.college.course.soap.Course soapCourse =
                new com.college.course.soap.Course();

        soapCourse.setId(course.getId());

        soapCourse.setCourseCode(
                course.getCourseCode()
        );

        soapCourse.setCourseName(
                course.getCourseName()
        );

        soapCourse.setDescription(
                course.getDescription()
        );

        soapCourse.setDepartment(
                course.getDepartment()
        );

        soapCourse.setCredits(
                course.getCredits()
        );

        soapCourse.setSemester(
                course.getSemester()
        );

        return soapCourse;
    }
}