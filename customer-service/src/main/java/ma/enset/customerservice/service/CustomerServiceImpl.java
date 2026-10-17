package ma.enset.customerservice.service;

import lombok.RequiredArgsConstructor;
import ma.enset.customerservice.entity.Customer;
import ma.enset.customerservice.exception.CustomerNotFoundException;
import ma.enset.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Yousef ELHAID
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}