package com.glowup.service;

import com.glowup.domain.PaymentMethod;
import com.glowup.model.PaymentOrder;
import com.glowup.payload.dto.BookingDTO;
import com.glowup.payload.dto.UserDTO;
import com.glowup.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user,
                                    BookingDTO booking,
                                    PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id);

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user,
                                          Long amount,
                                          Long orderId);
    String createStripePaymentLink(UserDTO user,
                                   Long amount,
                                   Long orderId);


}
