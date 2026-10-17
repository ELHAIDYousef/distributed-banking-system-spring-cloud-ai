package ma.enset.accountservice.client;

import ma.enset.accountservice.model.Customer;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yousef ELHAID
 */
@Component
public class CustomerRestClientFallback implements CustomerRestClient {

    @Override
    public Customer findCustomerById(Long id) {
        return new Customer(id, "Not available", "Not available", "Not available");
    }

    @Override
    public PagedModel<Customer> allCustomers() {
        return PagedModel.empty();
    }
}