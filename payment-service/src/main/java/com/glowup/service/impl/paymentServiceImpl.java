package com.glowup.service.impl;

import com.glowup.domain.PaymentMethod;
import com.glowup.model.PaymentOrder;
import com.glowup.payload.dto.BookingDTO;
import com.glowup.payload.dto.UserDTO;
import com.glowup.payload.response.PaymentLinkResponse;
import com.glowup.repository.PaymentOrderRepository;
import com.glowup.service.PaymentService;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
import com.stripe.exception.StripeException;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException{
       Long amount =(long)booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        PaymentOrder savedOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.RAZORPAY))
        {
            PaymentLink payment = createRazorpayPaymentLink(user,
                    savedOrder.getAmount(),
                    savedOrder.getId());


            String paymenturl = payment.get("short_url");
            String paymentUrlId = payment.get("id");


            paymentLinkResponse.setPayment_link_url(paymenturl);
            paymentLinkResponse.setGetPayment_link_id(paymentUrlId);

            savedOrder.setPaymentLinkId(paymentUrlId);

            paymentOrderRepository.save(savedOrder);

        }
        else {
            String paymentUrl = createStripePaymentLink(user,
                    savedOrder.getAmount(),
                    savedOrder.getId());
            paymentLinkResponse.setPayment_link_url(paymentUrl);
        }

        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception{
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);
        if(paymentOrder == null) {
            throw new Exception("payment order not found");

        }

            return paymentOrder;

    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDTO user, Long Amount, Long orderId) throws RazorpayException {
        Long amount = Amount*100;


            RazorpayClient razorpay = new RazorpayClient(razorpayApiKey,razorpayApiSecret);

            JSONObject paymentLinkRequest = new JSONObject();

        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");

        JSONObject customer=new JSONObject();
        customer.put("name",user.getFullName());
        customer.put("email",user.getEmail());

        paymentLinkRequest.put("customer",customer);

        JSONObject notify=new JSONObject();
        notify.put("email",true);

        paymentLinkRequest.put("notify",notify);

        paymentLinkRequest.put("reminder_enable",true);

        paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success/"+orderId);

        paymentLinkRequest.put("callback_method","get");

        PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);

            return paymentLink;


    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) {

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount * 100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder().setName("salon appointment booking").build()
                                ).build()

                        ).build()
                ).build();

        Session session = Session.create(params);

        return session.getUrl();

    }}




