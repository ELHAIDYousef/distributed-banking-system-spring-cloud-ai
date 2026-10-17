package ma.enset.accountservice.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.accountservice.enums.AccountType;
import ma.enset.accountservice.model.Customer;

import java.time.LocalDate;

/**
 *
 * @author Yousef ELHAID
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BankAccount {
    @Id
    private String accountId;
    private double balance;
    private LocalDate createdAt;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    private Long customerId;

    @Transient
    private Customer customer;

}