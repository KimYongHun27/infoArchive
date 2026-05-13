package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.CustomerInquiryRequestDto;
import com.meta12.infoArchive.entity.CustomerInquiry;
import com.meta12.infoArchive.entity.InquiryStatus;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerInquiryRepository customerInquiryRepository;
    private final UserRepository userRepository;

    // 고객센터 문의 등록
    public CustomerInquiry createInquiry(CustomerInquiryRequestDto requestDto) {

        User foundUser = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        CustomerInquiry newInquiry = CustomerInquiry.builder()
                .user(foundUser)
                .category(requestDto.getCategory())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .status(InquiryStatus.WAITING)
                .createdAt(LocalDateTime.now())
                .build();

        return customerInquiryRepository.save(newInquiry);
    }

    // 고객센터 문의 전체 조회
    public List<CustomerInquiry> getInquiries() {
        return customerInquiryRepository.findAll();
    }

    // 고객센터 문의 답변 등록
    public CustomerInquiry answerInquiry(Long inquiryId, String answerContent) {

        CustomerInquiry foundInquiry = customerInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의를 찾을 수 없습니다."));

        foundInquiry.setAnswer(answerContent);
        foundInquiry.setStatus(InquiryStatus.ANSWERED);
        foundInquiry.setAnsweredAt(LocalDateTime.now());

        return customerInquiryRepository.save(foundInquiry);
    }
}