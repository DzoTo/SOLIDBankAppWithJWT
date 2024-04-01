package kz.singularity.bankappdelivery.model.account;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import kz.singularity.bankappdelivery.model.user.User;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Account {
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String clientID;
    private double balance;
    boolean withdrawAllowed;

    @ManyToOne
    @JoinColumn(name = "user_accouts")
    private User user_id;
    @Override
    public String toString() {
        String accountNumber = String.format("%03d%06d%s", 1, 0, id);

        return "Account{" +
                "accountType=" + accountType +
                ", id='" + accountNumber + '\'' +
                ", clientID='" + clientID + '\'' +
                ", balance=" + balance +
                ", withdrawAllowed=" + withdrawAllowed +
                '}';
    }

}
