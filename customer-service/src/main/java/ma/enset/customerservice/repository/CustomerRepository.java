package ma.enset.customerservice.repository;

import ma.enset.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Yousef ELHAID
 */
@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}