package edu.msudenver.player.account;

import edu.msudenver.player.character.Character;
import edu.msudenver.player.character.CharacterRepository;
import edu.msudenver.player.character.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AccountService.class)
@SpringBootTest(classes = CharacterService.class)
public class AccountServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    protected EntityManager entityManager;

    @SpyBean
    private AccountService accountService;

    @BeforeEach
    public void setup() { accountService.entityManager = entityManager; }


    @Test
    public void testGetAccounts() throws Exception {

        Account testAccount = new Account();
        testAccount.setName("jebusFLY");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        testAccount = accountService.createAccount(testAccount);

        assertSame(accountRepository.findAll(), (Arrays.asList(testAccount)));

    }

    @Test
    public void testGetAccount() throws Exception {

        Account testAccount = new Account();
        testAccount.setName("jebusFLY");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        testAccount = accountService.createAccount(testAccount);

        assertSame(accountService.getAccount(1l), testAccount);
        assertSame(accountRepository.findById(1l), (Arrays.asList(testAccount)));

    }

    @Test
    public void testSaveAccount() throws Exception  {

        Account testAccount = new Account();
        testAccount.setName("jebusFLY");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Account testAccount2 = accountService.createAccount(testAccount);
        testAccount = accountService.createAccount(testAccount);

        assertEquals(testAccount, testAccount2);

    }
    @Test
    public void testDeleteAccount() throws Exception {

        Account testAccount = new Account();
        testAccount.setName("jebusFLY");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        testAccount = accountService.createAccount(testAccount);

        accountService.deleteAccount(1l);

        assertNull(testAccount);

    }
}
