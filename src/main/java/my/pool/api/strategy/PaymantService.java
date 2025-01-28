package my.pool.api.strategy;

import org.springframework.stereotype.Service;

@Service
public class PaymantService {

    public String processPayment(PaymentTax paymentTax, double money) {
        return paymentTax.getTax(money) + " tax for " + paymentTax.getType() + " payment";
    };
}
