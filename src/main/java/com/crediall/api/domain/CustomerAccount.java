package com.crediall.api.domain;

import com.crediall.api.domain.enumeration.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustomerAccount.
 */
@Entity
@Table(name = "customer_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<CrediallWallet> crediallWallets = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categories", "customer" }, allowSetters = true)
    private Set<AccountType> accountTypes = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomerAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public CustomerAccount firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public CustomerAccount lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public CustomerAccount email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public CustomerAccount telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    public CustomerAccount status(AccountStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Set<CrediallWallet> getCrediallWallets() {
        return this.crediallWallets;
    }

    public void setCrediallWallets(Set<CrediallWallet> crediallWallets) {
        if (this.crediallWallets != null) {
            this.crediallWallets.forEach(i -> i.setCustomer(null));
        }
        if (crediallWallets != null) {
            crediallWallets.forEach(i -> i.setCustomer(this));
        }
        this.crediallWallets = crediallWallets;
    }

    public CustomerAccount crediallWallets(Set<CrediallWallet> crediallWallets) {
        this.setCrediallWallets(crediallWallets);
        return this;
    }

    public CustomerAccount addCrediallWallet(CrediallWallet crediallWallet) {
        this.crediallWallets.add(crediallWallet);
        crediallWallet.setCustomer(this);
        return this;
    }

    public CustomerAccount removeCrediallWallet(CrediallWallet crediallWallet) {
        this.crediallWallets.remove(crediallWallet);
        crediallWallet.setCustomer(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setCustomer(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setCustomer(this));
        }
        this.addresses = addresses;
    }

    public CustomerAccount addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public CustomerAccount addAddress(Address address) {
        this.addresses.add(address);
        address.setCustomer(this);
        return this;
    }

    public CustomerAccount removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCustomer(null);
        return this;
    }

    public Set<AccountType> getAccountTypes() {
        return this.accountTypes;
    }

    public void setAccountTypes(Set<AccountType> accountTypes) {
        if (this.accountTypes != null) {
            this.accountTypes.forEach(i -> i.setCustomer(null));
        }
        if (accountTypes != null) {
            accountTypes.forEach(i -> i.setCustomer(this));
        }
        this.accountTypes = accountTypes;
    }

    public CustomerAccount accountTypes(Set<AccountType> accountTypes) {
        this.setAccountTypes(accountTypes);
        return this;
    }

    public CustomerAccount addAccountType(AccountType accountType) {
        this.accountTypes.add(accountType);
        accountType.setCustomer(this);
        return this;
    }

    public CustomerAccount removeAccountType(AccountType accountType) {
        this.accountTypes.remove(accountType);
        accountType.setCustomer(null);
        return this;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setCustomer(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setCustomer(this));
        }
        this.transactions = transactions;
    }

    public CustomerAccount transactions(Set<Transaction> transactions) {
        this.setTransactions(transactions);
        return this;
    }

    public CustomerAccount addTransactions(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setCustomer(this);
        return this;
    }

    public CustomerAccount removeTransactions(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerAccount)) {
            return false;
        }
        return id != null && id.equals(((CustomerAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerAccount{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
