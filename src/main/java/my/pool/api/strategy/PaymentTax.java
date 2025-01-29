package my.pool.api.strategy;

public interface PaymentTax {

    String getType();
    double getTax(double amount);

}
