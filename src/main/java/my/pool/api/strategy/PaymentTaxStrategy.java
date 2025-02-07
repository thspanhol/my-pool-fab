package my.pool.api.strategy;

public interface PaymentTaxStrategy {

    String getType();
    double getTax(double amount);

}
