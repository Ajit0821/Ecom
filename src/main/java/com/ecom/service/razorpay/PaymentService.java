package com.ecom.service.razorpay;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private RazorpayClient razorpayClient;

    public PaymentService() throws RazorpayException {
        this.razorpayClient = new RazorpayClient("rzp_test_2UIpknTlw851k2", "5R9bDYKy14ZufqDKxDOyiP0O");
    }

    public String createPaymentLink(String name, String email, double amount) throws RazorpayException {
        JSONObject request = new JSONObject();
        request.put("amount", (int) (amount * 100));  // Amount in paise
        request.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("name", name);
        customer.put("email", email);

        JSONObject link = razorpayClient.paymentLink.create(request).toJson();
        JSONObject orders = razorpayClient.orders.create(request).toJson();
        return link.getString("short_url");  // Return the payment link
    }
}
