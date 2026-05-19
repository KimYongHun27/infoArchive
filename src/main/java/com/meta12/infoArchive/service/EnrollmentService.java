package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.*;
import com.meta12.infoArchive.repository.CartRepository;
import com.meta12.infoArchive.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void enrollCourses(User user, List<Long> cartItemIds) {
        // 1. 선택된 장바구니 아이템들 조회
        List<Cart> cartItems = cartRepository.findAllById(cartItemIds);

        for (Cart cart : cartItems) {
            // 2. 장바구니 아이템에서 강의(Course) 정보 추출 후 Enrollment 생성
            enrollmentRepository.save(Enrollment.createEnrollment(user, cart.getCourse()));
        }

        // 3. 결제가 완료되었으므로 구매한 장바구니 아이템 삭제 (선택 사항)
        cartRepository.deleteAll(cartItems);
    }

//    public Enrollment insert()
//    {
//        //User user, Course course
//        //return enrollmentRepository.save(Enrollment.createEnrollment());
//    }
}
