package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Payment;
import com.meta12.infoArchive.entity.PaymentStatus;
import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByUserOrderByOrderDateDesc(User user);

    List<Payment> findByUserAndProductIsNotNullOrderByOrderDateDesc(User user);

    List<Payment> findByUserAndProductIsNullOrderByOrderDateDesc(User user);

    long countByUser(User user);

    long countByUserAndProductIsNotNull(User user);

    long countByUserAndProductIsNull(User user);

    List<Payment> findTop5ByUserOrderByOrderDateDesc(User user);

    // 결제 완료한 강의인지 확인
    boolean existsByUserAndProductIdAndPaymentStatus(
            User user,
            Long productId,
            PaymentStatus paymentStatus
    );
}