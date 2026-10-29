package ma.enset.customerservice.mcp;

import lombok.RequiredArgsConstructor;
import ma.enset.customerservice.entity.Customer;
import ma.enset.customerservice.service.CustomerService;
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
public class CustomerMcpTools {

    private final CustomerService customerService;

    @McpTool(name = "getAllCustomers",
            description = "Get the list of all bank customers with their id, first name, last name and email")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @McpTool(name = "getCustomerById",
            description = "Get one bank customer by its id")
    public Customer getCustomerById(
            @McpToolParam(description = "The customer id", required = true) Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @McpTool(name = "saveCustomer",
            description = "Create a new bank customer from a first name, a last name and a unique email")
    public Customer saveCustomer(
            @McpToolParam(description = "The customer to create: firstName, lastName, email", required = true) Customer customer) {
        return customerService.saveCustomer(customer);
    }
}