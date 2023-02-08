package edu.msudenver.player.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @PersistenceContext
    public EntityManager entityManager;

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long accountId) {
        try {
            return accountRepository.findById(accountId).get();
        } catch(NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Account createAccount(Account account) {
        account = accountRepository.saveAndFlush(account);
        entityManager.refresh(account);
        return account;
    }

    public boolean deleteAccount(Long accountId) {
        try {
            if(accountRepository.existsById(accountId)) {
                accountRepository.deleteById(accountId);
                return true;
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        return false;
    }
}
