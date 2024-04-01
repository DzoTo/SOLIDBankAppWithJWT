package kz.singularity.bankappdelivery.model.account;


import jakarta.persistence.*;
import kz.singularity.bankappdelivery.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
//@Table(name = "saving_accounts")
public class SavingAccount extends AccountWithdraw {
//    public SavingAccount(AccountType accountType, Long id, String clientID, double balance, boolean withdrawAllowed) {
//        super(accountType, id, clientID, balance, withdrawAllowed);
//    }


    public SavingAccount(AccountType accountType, Long id, String clientID, double balance, boolean withdrawAllowed, User user_id) {
        super(accountType, id, clientID, balance, withdrawAllowed, user_id);
    }

    public SavingAccount() {
        super();
    }
}
