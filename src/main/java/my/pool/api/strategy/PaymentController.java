package my.pool.api.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pool/payment")
public class PaymentController {

    private final PaymantService paymantService;

    @PostMapping("/all")
    public Double getPayment(@RequestBody ObjectTax objectTax) {
        return paymantService.calculateTax(objectTax);
    }

}
