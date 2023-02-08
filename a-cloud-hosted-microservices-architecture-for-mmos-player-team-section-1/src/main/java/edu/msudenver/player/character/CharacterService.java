package edu.msudenver.player.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @PersistenceContext
    public EntityManager entityManager;

    public List<Character> getCharacters() {return characterRepository.findAll();}

    public Character getCharacter(String name) {
        try {
            return characterRepository.findById(name).get();
        } catch(NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Character saveCharacter(Character profile) {
        profile = characterRepository.saveAndFlush(profile);
        entityManager.refresh(profile);
        return profile;
    }
    public boolean deleteCharacter(String name) {
        try {
            if(characterRepository.existsById(name)) {
                characterRepository.deleteById(name);
                return true;
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        return false;

    }

}
