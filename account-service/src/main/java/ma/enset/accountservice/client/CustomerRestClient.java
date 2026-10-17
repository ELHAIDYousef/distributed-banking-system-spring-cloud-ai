package ma.enset.accountservice.client;

import ma.enset.accountservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Yousef ELHAID
 */
@FeignClient(name = "customer-service",
        url = "${customer-service.url}",
        fallback = CustomerRestClientFallback.class)
public interface CustomerRestClient {

    @GetMapping("/customers/{id}")
    Customer findCustomerById(@PathVariable("id") Long id);

    @GetMapping("/customers")
    PagedModel<Customer> allCustomers();
}