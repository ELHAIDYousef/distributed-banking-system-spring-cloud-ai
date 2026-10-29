package ma.enset.customerservice.exception;

/**
 *
 * @author Yousef ELHAID
 */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long customerId) {
        super("Customer not found: " + customerId);
    }
}