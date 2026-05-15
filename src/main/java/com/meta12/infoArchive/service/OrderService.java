package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.OrderRequestDto;
import com.meta12.infoArchive.dto.PurchaseDto;
import com.meta12.infoArchive.entity.*;
import com.meta12.infoArchive.repository.OrderRepository;
import com.meta12.infoArchive.repository.PaymentRepository;
import com.meta12.infoArchive.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final PurchaseRepository purchaseRepository;
    private final UserService userService;
    private final UserCouponService userCouponService;
    private final ProductService productService;

}
