package kz.singularity.bankappdelivery.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import kz.singularity.bankappdelivery.model.account.Account;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user_id", cascade = CascadeType.ALL)
    private Set<Account> accountSet;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
