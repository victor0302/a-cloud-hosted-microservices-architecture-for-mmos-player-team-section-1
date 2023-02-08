package edu.msudenver.player.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private EntityManager entityManager;

    @SpyBean
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        accountService.entityManager = entityManager;
    }

    @Test
    public void testGetAccounts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findAll()).thenReturn(Arrays.asList(testAccount));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Player 1"));
    }

    @Test
    public void testGetAccount() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/accounts/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testAccount));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Player 1"));
    }

    @Test
    public void testGetAccountNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testCreateAccount() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1\", \"name\": \"Player 1\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.saveAndFlush(Mockito.any())).thenReturn(testAccount);
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(testAccount);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Player 1"));
    }

    @Test
    public void testCreateAccountBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Player 1\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(accountRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        Mockito.when(accountRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testUpdateAccountName() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1\", \"name\": \"Player 1\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(testAccount));

        Account testAccountUpdated = new Account();
        testAccount.setName("Player 2");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(testAccountUpdated);
        Mockito.when(accountRepository.saveAndFlush(Mockito.any())).thenReturn(testAccountUpdated);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Player 2"));
    }

    @Test
    public void testUpdateAccountNameNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateAccountNameBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(testAccount));

        Mockito.when(accountRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        Mockito.when(accountRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testUpdateAccountStatus() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1/false")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1\", \"status\": \"false\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(testAccount));

        Account testAccountUpdated = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(false);
        testAccount.setLocationId(1L);
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(testAccountUpdated);
        Mockito.when(accountRepository.saveAndFlush(Mockito.any())).thenReturn(testAccountUpdated);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("false"));
    }

    @Test
    public void testUpdateAccountStatusNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1/notfound")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1\", \"status\": \"notfound\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateAccountStatusBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1/true")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(false);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(testAccount));

        Mockito.when(accountRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        Mockito.when(accountRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testUpdateAccountLocation() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1/2")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1\", \"locationId\": \"2\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(testAccount));

        Account testAccountUpdated = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(2L);
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(testAccountUpdated);
        Mockito.when(accountRepository.saveAndFlush(Mockito.any())).thenReturn(testAccountUpdated);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("2L"));
    }

    @Test
    public void testUpdateAccountLocationNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/players/1/notfound")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1d\", \"locationId\":\"notfound\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateAccountLocationBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"1\", \"locationId\":\"1\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(false);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(testAccount));

        Mockito.when(accountRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        Mockito.when(accountRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/accounts/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Account testAccount = new Account();
        testAccount.setName("Player 1");
        testAccount.setStatus(true);
        testAccount.setLocationId(1L);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(testAccount));
        Mockito.when(accountRepository.existsById(Mockito.any())).thenReturn(true);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void testDeletePlayerNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/accounts/notfound")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(accountRepository.existsById(Mockito.any())).thenReturn(false);
        Mockito.doThrow(IllegalArgumentException.class)
                .when(accountRepository)
                .deleteById(Mockito.any());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }
}
