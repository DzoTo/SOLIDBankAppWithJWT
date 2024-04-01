package kz.singularity.bankappdelivery.model.account;

import jakarta.persistence.*;
import kz.singularity.bankappdelivery.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
//@Table(name = "fixed_accounts")
public class FixedAccount extends AccountDeposit {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name = "FIXED";

//    public FixedAccount(AccountType accountType, Long accountID, String clientID, double balance, boolean withdrawAllowed) {
//        super(accountType, accountID, clientID, balance, withdrawAllowed);
//    }


    public FixedAccount(AccountType accountType, Long id, String clientID, double balance, boolean withdrawAllowed, User user_id) {
        super(accountType, id, clientID, balance, withdrawAllowed, user_id);
    }

    public FixedAccount() {
        super();
    }
}
