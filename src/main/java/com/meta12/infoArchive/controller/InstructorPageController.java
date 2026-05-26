package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.InstructorCourseCreateDto;
import com.meta12.infoArchive.dto.PasswordChangeDto;
import com.meta12.infoArchive.entity.Enrollment;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.EnrollmentRepository;
import com.meta12.infoArchive.service.InstructorCourseService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InstructorPageController {

    private final UserService userService;
    private final InstructorCourseService instructorCourseService;
    private final EnrollmentRepository enrollmentRepository;

    @GetMapping("/instructor")
    public String instructorDashboard() {
        return "instructor/instructor-dashboard";
    }

    @GetMapping("/instructor/my-info")
    public String instructorMyInfo(
            Authentication authentication,
            Model model
    ) {
        User user = userService.getLoginUser(authentication);

        model.addAttribute("user", user);

        return "instructor/my-info";
    }

    @PostMapping("/instructor/phone/change")
    public String changeInstructorPhone(
            Authentication authentication,
            @RequestParam String phone
    ) {
        try {
            userService.changeMyPhone(authentication, phone);
            return "redirect:/instructor/my-info?phoneSuccess=true";

        } catch (IllegalArgumentException e) {
            return "redirect:/instructor/my-info?phoneError=true";
        }
    }

    @GetMapping("/instructor/courses")
    public String instructorCourses(
            Authentication authentication,
            Model model
    ) {
        List<Product> courses = instructorCourseService.getMyCourses(authentication);

        model.addAttribute("courses", courses);
        model.addAttribute("totalCount", courses.size());

        long approvedCount = courses.stream()
                .filter(course -> course.getStatus() != null
                        && course.getStatus() == ProductStatus.APPROVED)
                .count();

        long waitingCount = courses.stream()
                .filter(course -> course.getStatus() != null
                        && course.getStatus() == ProductStatus.WAITING)
                .count();

        long rejectedCount = courses.stream()
                .filter(course -> course.getStatus() != null
                        && course.getStatus() == ProductStatus.REJECTED)
                .count();

        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("waitingCount", waitingCount);
        model.addAttribute("rejectedCount", rejectedCount);

        return "instructor/courses";
    }

    @GetMapping("/instructor/course-new")
    public String instructorCourseNew() {
        return "instructor/course-new";
    }

    @PostMapping("/instructor/course-new")
    public String instructorCourseCreate(
            Authentication authentication,
            InstructorCourseCreateDto dto,
            @RequestParam(value = "thumbnailFile", required = false) MultipartFile thumbnailFile
    ) {
        instructorCourseService.createCourse(authentication, dto, thumbnailFile);

        return "redirect:/instructor/courses?courseSuccess=true";
    }

    @GetMapping("/instructor/students")
    public String instructorStudents(
            Authentication authentication,
            Model model
    ) {
        String instructorName = authentication.getName();

        List<Enrollment> enrollmentList =
                enrollmentRepository.findByProduct_InstructorNameOrderByEnrolledAtDesc(instructorName);

        long totalStudentCount = enrollmentList.stream()
                .filter(enrollment -> enrollment.getUser() != null)
                .map(enrollment -> enrollment.getUser().getId())
                .distinct()
                .count();

        long studyingCount = enrollmentList.stream()
                .filter(enrollment -> !Boolean.TRUE.equals(enrollment.getCompleted()))
                .count();

        long completedCount = enrollmentList.stream()
                .filter(enrollment -> Boolean.TRUE.equals(enrollment.getCompleted()))
                .count();

        int averageProgress = 0;

        if (!enrollmentList.isEmpty()) {
            averageProgress = (int) Math.round(
                    enrollmentList.stream()
                            .mapToInt(enrollment ->
                                    enrollment.getProgressRate() != null
                                            ? enrollment.getProgressRate()
                                            : 0
                            )
                            .average()
                            .orElse(0)
            );
        }

        model.addAttribute("enrollmentList", enrollmentList);
        model.addAttribute("totalStudentCount", totalStudentCount);
        model.addAttribute("studyingCount", studyingCount);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("averageProgress", averageProgress);

        return "instructor/students";
    }

    @GetMapping("/instructor/settlement")
    public String instructorSettlement() {
        return "instructor/settlement";
    }

    @PostMapping("/instructor/password/change")
    public String changeInstructorPassword(
            Authentication authentication,
            PasswordChangeDto passwordChangeDto
    ) {
        try {
            userService.changeMyPassword(authentication, passwordChangeDto);
            return "redirect:/instructor/my-info?passwordSuccess=true";

        } catch (IllegalArgumentException e) {
            return "redirect:/instructor/my-info?passwordError=true";
        }
    }

    @GetMapping("/instructor/courses/{id}")
    public String instructorCourseDetail(
            Authentication authentication,
            @PathVariable Long id,
            Model model
    ) {
        Product course = instructorCourseService.getMyCourse(authentication, id);

        model.addAttribute("course", course);

        return "instructor/course-detail";
    }

    @GetMapping("/instructor/courses/{id}/edit")
    public String instructorCourseEdit(
            Authentication authentication,
            @PathVariable Long id,
            Model model
    ) {
        Product course = instructorCourseService.getMyCourse(authentication, id);

        model.addAttribute("course", course);

        return "instructor/course-edit";
    }

    @PostMapping("/instructor/courses/{id}/edit")
    public String instructorCourseUpdate(
            Authentication authentication,
            @PathVariable Long id,
            InstructorCourseCreateDto dto,
            @RequestParam(value = "thumbnailFile", required = false) MultipartFile thumbnailFile
    ) {
        instructorCourseService.updateCourse(authentication, id, dto, thumbnailFile);

        return "redirect:/instructor/courses/" + id + "?updateSuccess=true";
    }

    @PostMapping("/instructor/courses/{id}/delete")
    public String instructorCourseDelete(
            Authentication authentication,
            @PathVariable Long id
    ) {
        instructorCourseService.deleteCourse(authentication, id);

        return "redirect:/instructor/courses?deleteSuccess=true";
    }
}