package ma.enset.accountservice.mcp;

import lombok.RequiredArgsConstructor;
import ma.enset.accountservice.entity.BankAccount;
import ma.enset.accountservice.service.AccountService;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author Yousef ELHAID
 */
@Component
@RequiredArgsConstructor
public class AccountMcpTools {

    private final AccountService accountService;

    @McpTool(name = "getAllAccounts",
            description = "Get all bank accounts with their balance, type, currency and owner customer")
    public List<BankAccount> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @McpTool(name = "getAccountById",
            description = "Get one bank account by its id, with its owner customer")
    public BankAccount getAccountById(
            @McpToolParam(description = "The bank account id", required = true) String accountId) {
        return accountService.getAccountById(accountId);
    }
}