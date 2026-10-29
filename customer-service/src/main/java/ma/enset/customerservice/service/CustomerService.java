package ma.enset.customerservice.service;

import ma.enset.customerservice.entity.Customer;

import java.util.List;

/**
 *
 * @author Yousef ELHAID
 */
public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long customerId);
    Customer saveCustomer(Customer customer);
}