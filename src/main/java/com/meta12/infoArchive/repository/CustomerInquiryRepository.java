package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.CustomerInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

// 고객센터 문의 테이블에 접근하는 Repository
public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long> {
}