package edu.msudenver.player.account;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping(path = "/{accountId}", produces = "application/json")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccount(accountId);
        return new ResponseEntity<>(account, account == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{accountId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> updateAccountName(@PathVariable Long accountId, @RequestBody Account updatedAccount) {
        Account retrievedAccount = accountService.getAccount(accountId);
        if (retrievedAccount != null) {
            retrievedAccount.setName(updatedAccount.getName());
            try {
                return ResponseEntity.ok(accountService.createAccount(retrievedAccount));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path = "/{accountId}/{status}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> updateAccountStatus(@PathVariable Long accountId, @PathVariable Boolean status) {
        Account account = accountService.getAccount(accountId);
        if (account != null) {
            account.setStatus(status);
            try {
                return ResponseEntity.ok(accountService.createAccount(account));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//PlayerLocation needs to be a part of character instead of Account
    @PatchMapping(path = "/{accountId}/{locationId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> updatePlayerLocation(@PathVariable Long accountId, @PathVariable Long locationId) {
        Account account = accountService.getAccount(accountId);
        if (account != null) {
            account.setLocationId(locationId);
            try {
                return ResponseEntity.ok(accountService.createAccount(account));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        return new ResponseEntity<>(accountService.deleteAccount(accountId) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }
}
