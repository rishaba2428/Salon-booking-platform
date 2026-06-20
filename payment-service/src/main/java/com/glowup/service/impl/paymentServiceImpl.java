package com.glowup.service.impl;

import com.glowup.domain.PaymentMethod;
import com.glowup.model.PaymentOrder;
import com.glowup.payload.dto.BookingDTO;
import com.glowup.payload.dto.UserDTO;
import com.glowup.payload.response.PaymentLinkResponse;
import com.glowup.repository.PaymentOrderRepository;
import com.glowup.service.PaymentService;
import com.razorpay.PaymentLink;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class paymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretkey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) {
       Long amount =(long)booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        PaymentOrder savedOrder = paymentOrderRepository.save(order);

        return null;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) {
        return null;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return null;
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) {
        return null;
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) {
        return "";
    }
}
