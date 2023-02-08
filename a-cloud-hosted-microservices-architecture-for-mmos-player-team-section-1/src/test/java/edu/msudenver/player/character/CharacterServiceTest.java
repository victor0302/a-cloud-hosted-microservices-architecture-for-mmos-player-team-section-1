package edu.msudenver.player.character;

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
@WebMvcTest(value = CharacterService.class)
@SpringBootTest(classes = CharacterService.class)
public class CharacterServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterRepository characterRepository;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    protected EntityManager entityManager;

    @SpyBean
    private CharacterService characterService;

    @BeforeEach
    public void setup() { characterService.entityManager = entityManager; }


    /*@Test
    public void testGetProfiles() throws Exception {

        PlayerProfile testProfile = new PlayerProfile();
        testProfile.setName("jebusFLY");
        testProfile.setPlayerClass("Warlock");
        testProfile.setRace("Human");

        testProfile = playerProfileService.saveProfile(testProfile);

        //assertSame(profileRepository.findAll(), (Arrays.asList(testProfile)));

    }*/

    @Test
    public void testGetCharacters() throws Exception {

        Character testCharacter = new Character();
        testCharacter.setName("jebusFLY");
        testCharacter.setPlayerClass("Warlock");
        testCharacter.setRace("Human");

        testCharacter = characterService.saveCharacter(testCharacter);

        assertSame(characterService.getCharacter("jebusFLY"), testCharacter);
        assertSame(characterRepository.findById("jebusFLY"), (Arrays.asList(testCharacter)));

    }

    @Test
    public void testSaveCharacter() throws Exception  {

        Character testCharacter = new Character();
        testCharacter.setName("jebusFLY");
        testCharacter.setPlayerClass("Warlock");
        testCharacter.setRace("Human");

        Character testCharacter2 = characterService.saveCharacter(testCharacter);
        testCharacter = characterService.saveCharacter(testCharacter);

        assertEquals(testCharacter, testCharacter2);

    }
    @Test
    public void testDeleteCharacter() throws Exception {

        Character testCharacter = new Character();
        testCharacter.setName("jebusFLY");
        testCharacter.setPlayerClass("Warlock");
        testCharacter.setRace("Human");

        testCharacter = characterService.saveCharacter(testCharacter);

        characterService.deleteCharacter("jebusFLY");

        assertNull(testCharacter);

    }
}
