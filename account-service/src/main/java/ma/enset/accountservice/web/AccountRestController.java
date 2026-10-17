package ma.enset.accountservice.web;

import lombok.RequiredArgsConstructor;
import ma.enset.accountservice.entity.BankAccount;
import ma.enset.accountservice.service.AccountService;
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

    private final AccountService accountService;

    @GetMapping("/accounts")
    public List<BankAccount> accounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    public BankAccount accountById(@PathVariable String id) {
        return accountService.getAccountById(id);
    }
}