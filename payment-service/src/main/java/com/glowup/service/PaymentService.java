package com.glowup.service;

import com.glowup.domain.PaymentMethod;
import com.glowup.model.PaymentOrder;
import com.glowup.payload.dto.BookingDTO;
import com.glowup.payload.dto.UserDTO;
import com.glowup.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user,
                                    BookingDTO booking,
                                    PaymentMethod paymentMethod) throws Exception;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user,
                                          Long amount,
                                          Long orderId)throws Exception;

    com.stripe.model.checkout.Session createStripePaymentLink(UserDTO user,
                                   Long amount,
                                   Long orderId) throws com.stripe.exception.StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId,
                           String paymentLinkId) throws RazorpayException;

}
