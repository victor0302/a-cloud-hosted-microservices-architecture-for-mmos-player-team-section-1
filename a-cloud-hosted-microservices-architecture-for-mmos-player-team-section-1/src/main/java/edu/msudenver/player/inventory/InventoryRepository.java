package edu.msudenver.player.inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    @Query(value = "SELECT * FROM inventories WHERE character_name = :character_name", nativeQuery = true)
    List<Inventory> findInventoryByCharacterName(@Param("character_name") String character_name);
}
