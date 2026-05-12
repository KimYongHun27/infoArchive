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
    private final PurchaseRepository purchaseRepository;
    private final UserService userService;
    private final UserCouponService userCouponService;
    private final ProductService productService;

    @Transactional
    public PurchaseDto processEntireOrder(OrderRequestDto dto) {
        //Order 생성 및 저장
        Order order = saveOrder(dto);

        //생성된 Order를 넘겨서 Payment 생성
        Payment payment = processPayment(order);

        //Payment 결과를 확인하고 Purchase 생성
        Purchase purchase = finalizePurchase(order, payment);

        //영수증에 보여줄 DTO로 변환해서 반환
        return convertToReceiptDto(purchase);
    }

    private Order saveOrder(OrderRequestDto dto)
    {
        Order order = new Order();
        Product product = productService.getProduct(dto.getProductId());
        order.setUser(userService.getUser(dto.getUserId()));

        order.setProduct(product);
        order.setOrderPrice(product.getPrice());
        order.setUserCoupon(userCouponService.getUserCoupon(dto.getUserCouponId()));
        return orderRepository.save(order);
    }

    private Payment processPayment(Order  order)
    {
        Payment payment = new Payment();

        int price = order.getOrderPrice();
        int discountAmount = 0;
        if (order.getUserCoupon() != null) {
            discountAmount = order.getUserCoupon().getCoupon().getDiscountAmount();
        }

        payment.setOrder(order);
        payment.setOrderDate(LocalDateTime.now());
        int randomNumber = ThreadLocalRandom.current().nextInt(1, 101);
        payment.setOrderNumber(payment.getOrderDate() + order.getUser().getId().toString() + randomNumber);
        payment.setPaymentStatus(PaymentStatus.WAIT);
        payment.setDiscountPrice(price - discountAmount);
        return  paymentRepository.save(payment);
    }

    private Purchase finalizePurchase(Order order, Payment payment)
    {
        Purchase purchase = new Purchase();
        purchase.setOrder(order);
        purchase.setPayment(payment);
        return purchaseRepository.save(purchase);
    }

    //영수증 정보
    private PurchaseDto convertToReceiptDto(Purchase purchase)
    {
        PurchaseDto purchaseDto = new PurchaseDto();

        purchaseDto.setOrderNumber(purchase.getPayment().getOrderNumber());
        purchaseDto.setPrice(purchase.getPayment().getDiscountPrice());
        purchaseDto.setProductName(purchase.getOrder().getProduct().getProductName());
        purchaseDto.setCreateAt(purchase.getPayment().getOrderDate());
        return purchaseDto;
    }
}
