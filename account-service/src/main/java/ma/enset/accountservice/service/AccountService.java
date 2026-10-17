package ma.enset.accountservice.service;

import ma.enset.accountservice.entity.BankAccount;

import java.util.List;

/**
 *
 * @author Yousef ELHAID
 */
public interface AccountService {
    List<BankAccount> getAllAccounts();
    BankAccount getAccountById(String accountId);
}