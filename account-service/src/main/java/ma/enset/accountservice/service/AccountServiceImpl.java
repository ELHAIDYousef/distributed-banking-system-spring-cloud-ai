package ma.enset.accountservice.service;

import lombok.RequiredArgsConstructor;
import ma.enset.accountservice.client.CustomerRestClient;
import ma.enset.accountservice.entity.BankAccount;
import ma.enset.accountservice.exception.AccountNotFoundException;
import ma.enset.accountservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Yousef ELHAID
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRestClient customerRestClient;

    @Override
    public List<BankAccount> getAllAccounts() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        accounts.forEach(this::attachCustomer);
        return accounts;
    }

    @Override
    public BankAccount getAccountById(String accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        attachCustomer(account);
        return account;
    }

    private void attachCustomer(BankAccount account) {
        account.setCustomer(customerRestClient.findCustomerById(account.getCustomerId()));
    }
}