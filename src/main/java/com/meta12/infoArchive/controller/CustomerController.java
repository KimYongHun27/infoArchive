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

    @PostMapping("/inquiries")
    public CustomerInquiry createInquiry(@RequestBody CustomerInquiryRequestDto dto) {
        return customerService.createInquiry(dto);
    }

    @GetMapping("/inquiries")
    public List<CustomerInquiry> getInquiries() {
        return customerService.getInquiries();
    }

    @PutMapping("/inquiries/{id}/answer")
    public CustomerInquiry answerInquiry(@PathVariable Long id, @RequestParam String answer) {
        return customerService.answerInquiry(id, answer);
    }
}