package com.shivamsrivastav.payment.controller;

import com.shivamsrivastav.payment.dto.request.ExecutePaymentRequest;
import com.shivamsrivastav.payment.dto.response.PaymentResponse;
import com.shivamsrivastav.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Page Controller
 * 
 * Handles page redirects for PayPal checkout flow.
 * 
 * @author Shivam Srivastav
 */
@Controller
@RequestMapping("/api/payments")
public class PageController {

    private static final Logger log = LoggerFactory.getLogger(PageController.class);

    private final PaymentService paymentService;

    public PageController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/success")
    public String handleSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            Model model) {

        log.info("Payment success callback: paymentId={}, payerId={}", paymentId, payerId);

        try {
            ExecutePaymentRequest request = ExecutePaymentRequest.builder()
                    .paymentId(paymentId)
                    .payerId(payerId)
                    .build();

            PaymentResponse response = paymentService.executePayment(request);
            model.addAttribute("payment", response);
            model.addAttribute("success", true);

        } catch (Exception e) {
            log.error("Error executing payment: {}", e.getMessage(), e);
            model.addAttribute("success", false);
            model.addAttribute("error", e.getMessage());
        }

        return "success";
    }

    @GetMapping("/cancel")
    public String handleCancel(Model model) {
        log.info("Payment cancelled by user");
        model.addAttribute("cancelled", true);
        return "cancel";
    }
}
