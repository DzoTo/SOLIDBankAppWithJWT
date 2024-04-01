package kz.singularity.bankappdelivery.model.account;

import jakarta.persistence.*;
import kz.singularity.bankappdelivery.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Entity
//@Table(name = "checking_accounts")
public class CheckingAccount extends AccountWithdraw {

//    public CheckingAccount(AccountType accountType, Long id, String clientID, double balance, boolean withdrawAllowed) {
//        super(accountType, id, clientID, balance, withdrawAllowed);
//    }


    public CheckingAccount(AccountType accountType, Long id, String clientID, double balance, boolean withdrawAllowed, User user_id) {
        super(accountType, id, clientID, balance, withdrawAllowed, user_id);
    }

    public CheckingAccount() {
        super();
    }
}
