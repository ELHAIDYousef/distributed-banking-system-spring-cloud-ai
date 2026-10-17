package ma.enset.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
}