package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Cart;
import com.meta12.infoArchive.entity.Enrollment;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.CartRepository;
import com.meta12.infoArchive.repository.EnrollmentRepository;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // 장바구니 결제 후 여러 상품 수강 등록
    @Transactional
    public void enrollProducts(User user, List<Long> cartIds) {

        List<Cart> cartList = cartRepository.findAllById(cartIds);

        for (Cart cart : cartList) {

            // 다른 유저 장바구니 결제 방지
            if (!cart.getUser().getId().equals(user.getId())) {
                throw new IllegalStateException("본인의 장바구니만 결제할 수 있습니다.");
            }

            boolean alreadyEnrolled =
                    enrollmentRepository.existsByUserIdAndProductId(
                            user.getId(),
                            cart.getProduct().getId()
                    );

            if (!alreadyEnrolled) {
                enrollmentRepository.save(
                        Enrollment.createEnrollment(user, cart.getProduct())
                );
            }
        }

        // 결제 완료 후 장바구니 삭제
        cartRepository.deleteAll(cartList);
    }

    // 단일 상품 결제 후 수강 등록
    @Transactional
    public void enrollProduct(User user, Long productId) {

        boolean alreadyEnrolled =
                enrollmentRepository.existsByUserIdAndProductId(user.getId(), productId);

        if (alreadyEnrolled) {
            return;
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        enrollmentRepository.save(
                Enrollment.createEnrollment(user, product)
        );
    }

    @Transactional(readOnly = true)
    public boolean isEnrolled(User user, Long productId) {
        return enrollmentRepository.existsByUserIdAndProductId(user.getId(), productId);
    }

    @Transactional
    public void updateProgress(Authentication authentication,
                               Long productId,
                               Integer watchedSeconds,
                               Integer totalSeconds) {

        User user = userService.getLoginUser(authentication);

        Enrollment enrollment = enrollmentRepository
                .findByUserIdAndProductId(user.getId(), productId)
                .orElseThrow(() -> new IllegalArgumentException("수강 중인 강의가 아닙니다."));

        if (totalSeconds == null || totalSeconds <= 0) {
            return;
        }

        int oldWatched = enrollment.getWatchedSeconds() == null ? 0 : enrollment.getWatchedSeconds();
        int currentWatched = watchedSeconds == null ? 0 : watchedSeconds;

        int maxWatched = Math.max(oldWatched, currentWatched);

        int progressRate = (int) Math.floor((maxWatched * 100.0) / totalSeconds);

        if (progressRate > 100) {
            progressRate = 100;
        }

        enrollment.setWatchedSeconds(maxWatched);
        enrollment.setTotalSeconds(totalSeconds);
        enrollment.setProgressRate(progressRate);

        if (progressRate >= 90 && !Boolean.TRUE.equals(enrollment.getCompleted())) {
            enrollment.setCompleted(true);
            enrollment.setCompletedAt(LocalDateTime.now());
        }
    }
}