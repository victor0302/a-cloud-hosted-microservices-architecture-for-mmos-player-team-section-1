package edu.msudenver.player.inventory;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping(path = "/{characterName}", produces = "application/json")
    public ResponseEntity<List<Inventory>> getCharacterInventory(@PathVariable String characterName) {
        return ResponseEntity.ok(inventoryService.getCharacterInventory(characterName));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        try {
            return new ResponseEntity<>(inventoryService.saveInventory(inventory), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }
}