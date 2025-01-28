package my.pool.api.strategy;

import org.springframework.stereotype.Service;

@Service
public class DebitCard implements PaymentTax{

    @Override
    public String getType() {
        return "Debit";
    }

    @Override
    public double getTax(double amount) {
        return amount * 0.05;
    }

}
