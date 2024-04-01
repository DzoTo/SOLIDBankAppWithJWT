package kz.singularity.bankappdelivery.controller;

import kz.singularity.bankappdelivery.dto.AccountDTO;
import kz.singularity.bankappdelivery.dto.AccountTypeId;
import kz.singularity.bankappdelivery.model.account.Account;
import kz.singularity.bankappdelivery.model.account.AccountBasicCLI;
import kz.singularity.bankappdelivery.model.transaction.Transaction;
import kz.singularity.bankappdelivery.model.transaction.TransactionType;
import kz.singularity.bankappdelivery.model.user.User;
import kz.singularity.bankappdelivery.service.impl.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/acc")
@Getter
@Setter
@RequiredArgsConstructor
public class AccountController {
    private final AccountListingServiceImpl accountListingServiceImpl;
    private final AccountCreationServiceImpl accountCreationServiceImpl;
    private final AccountBasicCLI accountBasicCLI;
    private final AccountWithdrawServiceImpl accountWithdrawServiceImpl;
    private final AccountDepositServiceImpl accountDepositServiceImpl;
    private final UserServiceImpl userService;

    @GetMapping("/accounts")
    public List<AccountDTO> findAllAccounts() {
         List<Account> accounts= accountListingServiceImpl.getAllAccounts();
         List<AccountDTO> userDTOList = new ArrayList<>();
         for(Account account : accounts){
             userDTOList.add(new AccountDTO(account.getId(), account.getAccountType(), account.getClientID(),
                     account.getBalance(), account.isWithdrawAllowed()));
         }
         return userDTOList;
    }

    @GetMapping("/accounts/{account_id}")
    public ResponseEntity<?> getAccountById(@PathVariable("account_id") Long id) {
        Account account = accountListingServiceImpl.getAccountById(id);
        if (account == null) {
            String errorMessage = "There is no account with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } else {
            User user = userService.getUserByID(account.getUser_id().getId());
            if (user.getAccountSet().contains(account)) { /*checking that account in current users*/
                return ResponseEntity.ok(account);
            } else {
                String errMessage = "This account belongs to another user";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMessage);
            }
        }
    }

    @DeleteMapping("/accounts/{account_id}")
    public void deleteAccountByID(@PathVariable("account_id") Long id) {
        accountListingServiceImpl.deleteAccount(id);
    }

    @PostMapping("/accounts")
    public void createAccount(@RequestBody AccountTypeId dto) {
        accountBasicCLI.createAccountRequest(dto.getAccountType(), dto.getClientID());
    }

    @PostMapping("/accounts/{account_id}/withdraw")
    public ResponseEntity<?> withdrowMoneyFromAccount(@PathVariable("account_id") Long id,
                                                      @RequestParam double amount) {
        Account account = accountListingServiceImpl.getAccountById(id);
        if (account == null) {
            String errorMessage = "Account not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }


        accountWithdrawServiceImpl.withdraw(amount, account);
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WITHDRAW);
        accountDepositServiceImpl.add(transaction);
        return ResponseEntity.ok("Withdraw operation was successful");
    }

    @PostMapping("/accounts/{account_id}/transfer")
    public ResponseEntity<?> transferMoney(@PathVariable("account_id") Long account_id,
                                           @RequestBody Long destination_id,
                                           @RequestParam double amount) {
        Account account1 = accountListingServiceImpl.getAccountById(account_id);
        Account account2 = accountListingServiceImpl.getAccountById(destination_id);
        if (account1 != null && account2 != null) {
            accountWithdrawServiceImpl.withdraw(amount, account1);
            accountDepositServiceImpl.deposit(amount, account2);
            return ResponseEntity.ok("Transfered from account: {} to account {} successfully");
        } else {
            String errorMessage = "Either acc1 or acc2 does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping("/accounts/{account_id}/deposit")
    public ResponseEntity<?> depositMoneyToAccount(@PathVariable("account_id") Long id,
                                                   @RequestParam double amount) {
        Account account = accountListingServiceImpl.getAccountById(id);
        if (account == null) {
            String errorMessage = "Account not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = account.getUser_id();

        if(Objects.equals(user.getUsername(), currentUsername)){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("You dont have access");
        }

        accountDepositServiceImpl.deposit(amount, account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        accountDepositServiceImpl.add(transaction);

        return ResponseEntity.ok("Deposit operation is successful");
    }

    @GetMapping("/accounts/{account_id}/transactions")
    public Iterable<Transaction> getAllTransactions(@PathVariable("account_id") Long id) {
        return accountDepositServiceImpl.findAllTransactions();
    }
}
