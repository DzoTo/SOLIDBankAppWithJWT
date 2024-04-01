package kz.singularity.bankappdelivery.dto;

import kz.singularity.bankappdelivery.model.account.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private AccountType accountType;
    private String clientID;
    private double balance;
    private boolean withdrawAllowed;
}
