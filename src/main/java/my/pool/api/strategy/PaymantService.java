package my.pool.api.strategy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymantService {

    private final List<PaymentTaxStrategy> paymentTaxStrategies;

    Map<String, PaymentTaxStrategy> map = new HashMap<>();

    @PostConstruct
    public void popular() {
        paymentTaxStrategies.forEach(strategy -> {
            map.put(strategy.getType(), strategy);
        });
    }

    public Double calculateTax(ObjectTax objectTax) {
        System.out.println(map);
        return map.get(objectTax.type()).getTax(objectTax.amount());
    }
}
