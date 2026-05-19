package com.meta12.infoArchive.repository;

import com.meta12.infoArchive.entity.Payment;
import com.meta12.infoArchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 로그인한 유저의 주문내역 최신순
    List<Payment> findByUserOrderByOrderDateDesc(User user);

    // 전체 주문 개수
    long countByUser(User user);

    // 강의 주문 개수: product_id가 있는 결제
    long countByUserAndProductIsNotNull(User user);

    // 멤버십 주문 개수: product_id가 없는 결제
    long countByUserAndProductIsNull(User user);
}