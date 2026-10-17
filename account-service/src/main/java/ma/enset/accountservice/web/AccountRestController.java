package ma.enset.accountservice.web;

import lombok.RequiredArgsConstructor;
import ma.enset.accountservice.client.CustomerRestClient;
import ma.enset.accountservice.entity.BankAccount;
import ma.enset.accountservice.repository.BankAccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author Yousef ELHAID
 */
@RestController
@RequiredArgsConstructor
public class AccountRestController {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRestClient customerRestClient;

    @GetMapping("/accounts")
    public List<BankAccount> accounts() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        accounts.forEach(account ->
                account.setCustomer(customerRestClient.findCustomerById(account.getCustomerId())));
        return accounts;
    }

    @GetMapping("/accounts/{id}")
    public BankAccount accountById(@PathVariable String id) {
        BankAccount account = bankAccountRepository.findById(id).orElseThrow();
        account.setCustomer(customerRestClient.findCustomerById(account.getCustomerId()));
        return account;
    }
}