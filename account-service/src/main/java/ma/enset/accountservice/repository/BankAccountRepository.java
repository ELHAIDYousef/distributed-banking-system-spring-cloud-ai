package ma.enset.accountservice.repository;

import ma.enset.accountservice.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Yousef ELHAID
 */
@RepositoryRestResource
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}