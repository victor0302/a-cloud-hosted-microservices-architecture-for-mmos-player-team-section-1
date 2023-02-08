package edu.msudenver.player.character;


import edu.msudenver.player.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "characters")
public class Character {

    @Id
    @Column(name = "name")
    @NotNull(message = "name cannot be null")
    private String name;

    @OneToOne
    @JoinColumn(name = "accountId", referencedColumnName = "accountId", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Account accountId;

    @Column(name = "playerClass")
    @NotNull(message = "playerClass cannot be null")
    private String playerClass;

    @Column(name = "race")
    @NotNull(message = "race cannot be null")
    private String race;

    public Character() {}

    public Character(String name, Account accountId, String playerClass, String race, Integer level) {
        this.name = name;
        this.accountId = accountId;
        this.playerClass = playerClass;
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Account getAccountId() {
        return accountId;
    }

}
