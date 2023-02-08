package edu.msudenver.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.msudenver.player.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "logIn")
public class LogIn {

    @Id
    @Column(name = "accountId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long accountId;

    @ManyToOne()
    @JoinColumn(name = "accountId", referencedColumnName = "accountId", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Account account;

    @Column(name = "logInStatus")
    @NotNull(message = "status cannot be null")
    private Boolean logInStatus;

    @Column(name = "itemTableVersion")
    @NotNull(message = "version cannot be null")
    private String itemTableVersion;

    @Column(name = "locationId")
    private Long locationId;


    public LogIn(Long accountId, Account account, Boolean logInStatus, String itemTableVersion, Long locationId) {
        this.accountId = accountId;
        this.account = account;
        this.logInStatus = logInStatus;
        this.itemTableVersion = itemTableVersion;
        this.locationId = locationId;
    }

    public LogIn() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public Account getPlayer() {
        return account;
    }

    public Boolean getLogInStatus() {
        return logInStatus;
    }

    public void setLogInStatus(Boolean logInStatus) {
        this.logInStatus = logInStatus;
    }

    public String getItemTableVersion() {
        return itemTableVersion;
    }

    public void setItemTableVersion(String itemTableVersion) {
        this.itemTableVersion = itemTableVersion;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
