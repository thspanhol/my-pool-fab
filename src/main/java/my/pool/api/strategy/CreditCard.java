package my.pool.api.strategy;

import org.springframework.stereotype.Service;

@Service
public class CreditCard implements PaymentTax{

    @Override
    public String getType() {
        return "Credit";
    }

    @Override
    public double getTax(double amount) {
        return amount * 0.15 ;
    }

}
