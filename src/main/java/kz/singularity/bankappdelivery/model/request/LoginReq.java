package kz.singularity.bankappdelivery.model.request;


import kz.singularity.bankappdelivery.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class LoginReq {
    private String username;
    private String password;
    //private Set<Account> accountSet;
}
