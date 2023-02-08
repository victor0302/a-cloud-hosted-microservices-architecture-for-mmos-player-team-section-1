package edu.msudenver.player.account;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Account {


    @Column(name = "name")
    @NotNull(message = "name cannot be null")
    private String name;

    @Id
    @Column(name = "accountId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long accountId;

    @Column(name = "status")
    @NotNull(message = "status cannot be null")
    private Boolean status;

    @JoinColumn(name = "locationId")
    private Long locationId;

    public Account() {}

    public Account(String name, Long accountId, Boolean status, Long locationId) {
        this.name = name;
        this.accountId = accountId;
        this.status = status;
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getLocationId() { return locationId; }

    public void setLocationId(Long locationId) { this.locationId = locationId; }
}
