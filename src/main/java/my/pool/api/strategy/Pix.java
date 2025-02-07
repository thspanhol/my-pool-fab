package my.pool.api.strategy;

import org.springframework.stereotype.Service;

@Service
public class Pix implements PaymentTaxStrategy {

    @Override
    public String getType() {
        return "Pix";
    }

    @Override
    public double getTax(double amount) {
        return amount * 1 ;
    }

}
