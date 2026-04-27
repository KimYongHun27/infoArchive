package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.CustomerInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

// 고객센터 문의 테이블에 접근하는 Repository
// 기본 CRUD 기능을 JpaRepository가 자동으로 제공함
public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long> {
}