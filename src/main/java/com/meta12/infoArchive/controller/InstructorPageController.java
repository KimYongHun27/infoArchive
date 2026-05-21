package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.InstructorCourseCreateDto;
import com.meta12.infoArchive.dto.PasswordChangeDto;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final InstructorCourseService instructorCourseService;

    @GetMapping("/instructor")
    public String instructorDashboard() {
        return "instructor/instructor-dashboard";
    }

    @GetMapping("/instructor/my-info")
    public String instructorMyInfo() {
        return "instructor/my-info";
    }

    @GetMapping("/instructor/courses")
    public String instructorCourses(Model model) {

        List<Product> courses = productRepository.findAll();

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
    public String instructorStudents() {
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
            @PathVariable Long id,
            Model model
    ) {
        Product course = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다. id=" + id));

        model.addAttribute("course", course);

        return "instructor/course-detail";
    }

    @GetMapping("/instructor/courses/{id}/edit")
    public String instructorCourseEdit(
            @PathVariable Long id,
            Model model
    ) {
        Product course = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다. id=" + id));

        model.addAttribute("course", course);

        return "instructor/course-edit";
    }

    @PostMapping("/instructor/courses/{id}/edit")
    public String instructorCourseUpdate(
            @PathVariable Long id,
            InstructorCourseCreateDto dto,
            @RequestParam(value = "thumbnailFile", required = false) MultipartFile thumbnailFile
    ) {
        instructorCourseService.updateCourse(id, dto, thumbnailFile);

        return "redirect:/instructor/courses/" + id + "?updateSuccess=true";
    }
}