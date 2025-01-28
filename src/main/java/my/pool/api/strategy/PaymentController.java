package my.pool.api.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pool/payment")
public class PaymentController {

    private final PaymantService paymantService;

    @GetMapping("/credit")
    public String getPaymentCredit() {
        PaymentTax credit = new CreditCard();
        return paymantService.processPayment(credit, 100);
    }

    @GetMapping("/debit")
    public String getPaymentDebit() {
        PaymentTax debit = new DebitCard();
        return paymantService.processPayment(debit, 100);
    }

}
