package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.CustomerInquiryRequestDto;
import com.meta12.infoArchive.entity.CustomerInquiry;
import com.meta12.infoArchive.entity.InquiryStatus;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.CustomerInquiryRepository;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerInquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    public CustomerInquiry createInquiry(CustomerInquiryRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        CustomerInquiry inquiry = CustomerInquiry.builder()
                .user(user)
                .category(dto.getCategory())
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(InquiryStatus.WAITING)
                .createdAt(LocalDateTime.now())
                .build();

        return inquiryRepository.save(inquiry);
    }

    public List<CustomerInquiry> getInquiries() {
        return inquiryRepository.findAll();
    }

    public CustomerInquiry answerInquiry(Long id, String answer) {
        CustomerInquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문의를 찾을 수 없습니다."));

        inquiry.setAnswer(answer);
        inquiry.setStatus(InquiryStatus.ANSWERED);
        inquiry.setAnsweredAt(LocalDateTime.now());

        return inquiryRepository.save(inquiry);
    }
}