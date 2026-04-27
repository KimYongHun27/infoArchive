package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.CustomerInquiryRequestDto;
import com.meta12.infoArchive.entity.CustomerInquiry;
import com.meta12.infoArchive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 고객센터 문의 등록
    @PostMapping("/inquiries")
    public CustomerInquiry createInquiry(@RequestBody CustomerInquiryRequestDto dto) {
        return customerService.createInquiry(dto);
    }

    // 고객센터 문의 전체 조회
    @GetMapping("/inquiries")
    public List<CustomerInquiry> getInquiries() {
        return customerService.getInquiries();
    }

    // 고객센터 문의 답변 등록
    @PutMapping("/inquiries/{id}/answer")
    public CustomerInquiry answerInquiry(@PathVariable Long id, @RequestParam String answer) {
        return customerService.answerInquiry(id, answer);
    }
}