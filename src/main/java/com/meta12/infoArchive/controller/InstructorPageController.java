package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.PasswordChangeDto;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.meta12.infoArchive.dto.InstructorCourseCreateDto;
import com.meta12.infoArchive.service.InstructorCourseService;
import org.springframework.web.bind.annotation.PathVariable;

import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.repository.ProductRepository;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InstructorPageController {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final InstructorCourseService instructorCourseService;

    // 강사 홈
    @GetMapping("/instructor")
    public String instructorDashboard() {
        return "instructor/instructor-dashboard";
    }

    // 내 정보
    @GetMapping("/instructor/my-info")
    public String instructorMyInfo() {
        return "instructor/my-info";
    }

    // 내 강의 관리
    @GetMapping("/instructor/courses")
    public String instructorCourses(Model model) {

        List<Product> courses = productRepository.findAll();

        model.addAttribute("courses", courses);
        model.addAttribute("totalCount", courses.size());

        long openCount = courses.stream()
                .filter(course -> course.getStatus() != null && course.getStatus().name().equals("OPEN"))
                .count();

        long closedCount = courses.stream()
                .filter(course -> course.getStatus() != null && course.getStatus().name().equals("CLOSED"))
                .count();

        model.addAttribute("openCount", openCount);
        model.addAttribute("closedCount", closedCount);

        return "instructor/courses";
    }

    // 강의 등록
    @GetMapping("/instructor/course-new")
    public String instructorCourseNew() {
        return "instructor/course-new";
    }

    // 강의 등록 처리
    @PostMapping("/instructor/course-new")
    public String instructorCourseCreate(
            Authentication authentication,
            InstructorCourseCreateDto dto
    ) {
        instructorCourseService.createCourse(authentication, dto);
        return "redirect:/instructor/courses?courseSuccess=true";
    }

    // 수강생 관리
    @GetMapping("/instructor/students")
    public String instructorStudents() {
        return "instructor/students";
    }

    // 정산 관리
    @GetMapping("/instructor/settlement")
    public String instructorSettlement() {
        return "instructor/settlement";
    }

    // 강사 비밀번호 변경
    @PostMapping("/instructor/password/change")
    public String changeInstructorPassword(
            Authentication authentication,
            PasswordChangeDto passwordChangeDto
    ) {
        userService.changeMyPassword(authentication, passwordChangeDto);
        return "redirect:/instructor/my-info?passwordSuccess=true";
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

    // 강의 수정 페이지
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

    // 강의 수정 처리
    @PostMapping("/instructor/courses/{id}/edit")
    public String instructorCourseUpdate(
            @PathVariable Long id,
            InstructorCourseCreateDto dto
    ) {
        instructorCourseService.updateCourse(id, dto);

        return "redirect:/instructor/courses/" + id + "?updateSuccess=true";
    }
}