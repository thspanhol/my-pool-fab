package my.pool.api.strategy;

import org.springframework.stereotype.Service;

@Service
public class DebitCard implements PaymentTaxStrategy {

    @Override
    public String getType() {
        return "Debit";
    }

    @Override
    public double getTax(double amount) {
        return amount * 0.05;
    }

}
