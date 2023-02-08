package edu.msudenver.player.character;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CharacterController.class)
public class CharacterControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterRepository characterRepository;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private EntityManager entityManager;

    @SpyBean
    private CharacterService characterService;

    @BeforeEach
    public void setup() {
        characterService.entityManager = entityManager;
    }

    @Test
    public void testGetCharacter() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/accounts/1/characters/TestCharacter")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        Character testCharacter = new Character();
        testCharacter.setName("TestCharacter");
        testCharacter.setRace("TestRace");
        testCharacter.setPlayerClass("TestClass");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testGetCharacterNotFound() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/accounts/1/characters/notfound")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    public void testCreateCharacter() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/accounts/1/characters")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"SomeCharacter\", \"race\": \"SomeRace\", \"playerClass\": \"SomeClass\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Character someCharacter = new Character();
        someCharacter.setName("SomeCharacter");
        someCharacter.setRace("SomeRace");
        someCharacter.setPlayerClass("SomeClass");
        Mockito.when(characterRepository.saveAndFlush(Mockito.any())).thenReturn(someCharacter);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    public void testCreateCharacterBadRequest() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/accounts/1/characters")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"someCharacter\", \"race\": \"Pigeon\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(characterRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

    }

    @Test
    public void testUpdateCharacter() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1/characters/SomeCharacter")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"SomeCharacter\", \"race\": \"SomeRace\", \"playerClass\": \"SomeClass\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Character someCharacter = new Character();
        someCharacter.setName("SomeCharacter");
        someCharacter.setRace("SomeRace");
        someCharacter.setPlayerClass("SomeClass");
        Mockito.when(characterRepository.findById(Mockito.any())).thenReturn(Optional.of(someCharacter));

        Character newCharacter = new Character();
        newCharacter.setName("SomeCharacter");
        newCharacter.setRace("NewRace");
        newCharacter.setPlayerClass("NewClass");
        Mockito.when(characterRepository.saveAndFlush(Mockito.any())).thenReturn(newCharacter);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testUpdateCharacterNotFound() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1/characters/notfound")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"notFound\"}")
                .contentType(MediaType.APPLICATION_JSON);

                Mockito.when(characterRepository.findById(Mockito.any())).thenReturn(Optional.empty());
                MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        
                MockHttpServletResponse response = result.getResponse();
        
                assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void testUpdateCharacterBadRequest() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/accounts/1/characters/SomeCharacter")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"SomeCharacter\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Character someCharacter = new Character();
        someCharacter.setName("SomeCharacter");
        someCharacter.setRace("SomeRace");
        someCharacter.setPlayerClass("SomeClass");
        Mockito.when(characterRepository.findById(Mockito.any())).thenReturn(Optional.of(someCharacter));

        Mockito.when(characterRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testDeleteCharacter() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/accounts/1/characters/SomeCharacter")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Character someCharacter = new Character();
        someCharacter.setName("SomeCharacter");
        someCharacter.setRace("SomeRace");
        someCharacter.setPlayerClass("SomeClass");
        Mockito.when(characterRepository.findById(Mockito.any())).thenReturn(Optional.of(someCharacter));
        Mockito.when(characterRepository.existsById(Mockito.any())).thenReturn(true);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void testDeleteCharacterNotFound() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/accounts/1/characters/notfound")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Character someCharacter = new Character();
        someCharacter.setName("SomeCharacter");
        someCharacter.setRace("SomeRace");
        someCharacter.setPlayerClass("SomeClass");
        Mockito.when(characterRepository.findById(Mockito.any())).thenReturn(Optional.of(someCharacter));
        Mockito.when(characterRepository.existsById(Mockito.any())).thenReturn(false);

        Mockito.doThrow(IllegalArgumentException.class)
                .when(characterRepository)
                .deleteById(Mockito.any());
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}
