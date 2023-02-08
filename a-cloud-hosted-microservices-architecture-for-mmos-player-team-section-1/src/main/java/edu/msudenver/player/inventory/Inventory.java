package edu.msudenver.player.inventory;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.msudenver.player.character.Character;

import javax.persistence.*;


@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "inventory_id", updatable = false)
    private Long inventoryId;
    @Column(name = "character_name")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String characterName;

    @OneToOne
    @JoinColumn(name = "character_name", referencedColumnName = "name", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Character character;

    @Column(name = "catalog_version")
    private String catalogVersion;
    @Column(name = "catalog_id")
    private Long catalogId;
    @Column(name = "catalog_type")
    private String catalogType;
    @Column(name = "equipped")
    private boolean equipped;
    @Column(name = "hp")
    private int hp;
    @Column(name = "attack")
    private int attack;
    @Column(name = "quantity")
    private int quantity;

    public Inventory() {}

    public Inventory(Long inventoryId, Character character, String catalogVersion, Long catalogId, String catalogType,
                     boolean equipped, int hp, int attack, int quantity) {
        this.inventoryId = inventoryId;
        this.character = character;
        this.catalogVersion = catalogVersion;
        this.catalogId = catalogId;
        this.catalogType = catalogType;
        this.equipped = equipped;
        this.hp = hp;
        this.attack = attack;
        this.quantity = quantity;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterId) {
        this.characterName = characterId;
    }

    public String getCatalogVersion() {
        return catalogVersion;
    }

    public void setCatalogVersion(String catalogVersion) {
        this.catalogVersion = catalogVersion;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}