package edu.msudenver.player.character;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/characters")
public class CharacterController
{
    @Autowired
    private CharacterService characterService;

    @GetMapping(path = "/{name}")
    public ResponseEntity<Character> getCharacter(@PathVariable String name)
    {
        Character character = characterService.getCharacter(name);
        return new ResponseEntity<>(character, character == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Character> createCharacter(@RequestBody Character character)
    {
        try 
        {
            return new ResponseEntity<>(characterService.saveCharacter(character), HttpStatus.CREATED);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{name}",consumes = "application/json",produces = "application/json")
    public ResponseEntity<Character> updateCharacter(@PathVariable String name, @RequestBody Character updatedCharacter)
    {
        Character retrievedCharacter = characterService.getCharacter(name);
        if (retrievedCharacter != null)
        {
            retrievedCharacter.setName(updatedCharacter.getName());
            retrievedCharacter.setPlayerClass(updatedCharacter.getPlayerClass());
            retrievedCharacter.setRace(updatedCharacter.getRace());
            try
            {
                return ResponseEntity.ok(characterService.saveCharacter(retrievedCharacter));
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
            }
        } 
        else 
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{name}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable String name)
    {
        return new ResponseEntity<>(characterService.deleteCharacter(name) ?
                HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }
}
