package edu.msudenver.player.inventory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @PersistenceContext
    public EntityManager entityManager;

    public List<Inventory> getInventory(){ return inventoryRepository.findAll();}

    public List<Inventory> getCharacterInventory(String characterName){
        try {
            return inventoryRepository.findInventoryByCharacterName(characterName);
        } catch (NoSuchElementException| IllegalArgumentException e){
            e.printStackTrace();
            return null;
        }
    }

    public Inventory getInventoryById(Long inventoryId) {
        try {
            return inventoryRepository.findById(inventoryId).get();
        } catch (NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Inventory saveInventory(Inventory inventory){
        inventory = inventoryRepository.saveAndFlush(inventory);
        entityManager.refresh(inventory);
        return inventory;
    }
    public boolean deleteInventory(Long inventoryId){
        try{
            if(inventoryRepository.existsById(inventoryId)){
                inventoryRepository.deleteById(inventoryId);
                return true;
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }


}