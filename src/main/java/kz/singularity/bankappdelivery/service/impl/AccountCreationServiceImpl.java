package kz.singularity.bankappdelivery.service.impl;




import kz.singularity.bankappdelivery.Repository.AccountRepository;
import kz.singularity.bankappdelivery.Repository.UserRepository;
import kz.singularity.bankappdelivery.model.account.*;
import kz.singularity.bankappdelivery.model.user.User;
import kz.singularity.bankappdelivery.service.AccountCreationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationServiceImpl implements AccountCreationService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final UserServiceImpl userService;

    public AccountCreationServiceImpl(AccountRepository accountRepository, UserServiceImpl userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }


    @Override
    public void create(AccountType accountType, long bankID, String clientID, Long accountID) {
        User user = userService.getUserByID(Long.valueOf(clientID));
        String accountNumber = String.format("%03d%06d%s", 1, 0, accountID);
        accountID = Long.valueOf(accountNumber);
        switch (accountType) {
            case CHECKING -> {
                accountRepository.save(new CheckingAccount(accountType, accountID, clientID, 0.0, true, user ));
                user.getAccountSet().add(accountRepository.getAccountById(accountID));
            }
            case SAVING -> {
                accountRepository.save(new SavingAccount(accountType, accountID, clientID, 0.0, true, user ));
                user.getAccountSet().add(accountRepository.getAccountById(accountID));
            }
            case FIXED -> {
                accountRepository.save(new FixedAccount(accountType, accountID, clientID, 0.0, false, user));
                user.getAccountSet().add(accountRepository.getAccountById(accountID));
            }
            default -> System.out.println("Input is incorrect!");
        }
    }
}
