package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.CustomerInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInquiryRepository extends JpaRepository<CustomerInquiry, Long> {
}