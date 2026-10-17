package ma.enset.customerservice;

import ma.enset.customerservice.entity.Customer;
import ma.enset.customerservice.repository.CustomerRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.stream.IntStream;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
	                                    RepositoryRestConfiguration restConfiguration) {
		return args -> {
			restConfiguration.exposeIdsFor(Customer.class);
			if (customerRepository.count() > 0) return;

			Faker faker = new Faker();
			customerRepository.saveAll(IntStream.rangeClosed(1, 10)
					.mapToObj(i -> {
						String firstName = faker.name().firstName();
						String lastName = faker.name().lastName();
						return Customer.builder()
								.firstName(firstName)
								.lastName(lastName)
								.email(faker.internet().emailAddress(
										(firstName + "." + lastName + i).toLowerCase()))
								.build();
					})
					.toList());
		};
	}
}