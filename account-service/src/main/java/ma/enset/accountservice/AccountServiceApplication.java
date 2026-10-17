package ma.enset.accountservice;

import ma.enset.accountservice.client.CustomerRestClient;
import ma.enset.accountservice.entity.BankAccount;
import ma.enset.accountservice.enums.AccountType;
import ma.enset.accountservice.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository,
                                        CustomerRestClient customerRestClient) {
        return args -> {
            if (bankAccountRepository.count() > 0) return;
            customerRestClient.allCustomers().getContent().forEach(customer -> {
                for (AccountType type : AccountType.values()) {
                    bankAccountRepository.save(BankAccount.builder()
                            .accountId(UUID.randomUUID().toString())
                            .type(type)
                            .balance(Math.random() * 80000)
                            .createdAt(LocalDate.now())
                            .currency("MAD")
                            .customerId(customer.id())
                            .build());
                }
            });
        };
    }
}