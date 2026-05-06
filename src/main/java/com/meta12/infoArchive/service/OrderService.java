package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.OrderRequestDto;
import com.meta12.infoArchive.dto.PurchaseDto;
import com.meta12.infoArchive.entity.Order;
import com.meta12.infoArchive.entity.Payment;
import com.meta12.infoArchive.entity.Purchase;
import com.meta12.infoArchive.repository.OrderRepository;
import com.meta12.infoArchive.repository.PaymentRepository;
import com.meta12.infoArchive.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public PurchaseDto processEntireOrder(OrderRequestDto dto) {
        // 1단계: Order 생성 및 저장
        Order order = saveOrder(dto);

        // 2단계: 생성된 Order를 넘겨서 Payment 생성
        Payment payment = processPayment(order);

        // 3단계: Payment 결과를 확인하고 Purchase 생성
        Purchase purchase = finalizePurchase(order, payment);

        // 4단계: 영수증에 보여줄 DTO로 변환해서 반환
        return convertToReceiptDto(purchase);
    }

    private Order saveOrder(OrderRequestDto dto)
    {
        Order order = new Order();

        return order;
    }

    private Payment processPayment(Order  order)
    {
        Payment payment = new Payment();

        return payment;
    }

    private Purchase finalizePurchase(Order Order, Payment payment)
    {
        Purchase purchase = new Purchase();

        return purchase;
    }

    private PurchaseDto convertToReceiptDto(Purchase purchase)
    {
        PurchaseDto purchaseDto = new PurchaseDto();

        return purchaseDto;
    }
}
