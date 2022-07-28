package com.crediall.api.domain;

import com.crediall.api.domain.enumeration.CrediallWalletStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CrediallWallet.
 */
@Entity
@Table(name = "crediall_wallet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CrediallWallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "jhi_limit", precision = 21, scale = 2)
    private BigDecimal limit;

    @Column(name = "balance", precision = 21, scale = 2)
    private BigDecimal balance;

    @Column(name = "spent", precision = 21, scale = 2)
    private BigDecimal spent;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CrediallWalletStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "crediallWallets", "addresses", "accountTypes", "transactions" }, allowSetters = true)
    private CustomerAccount customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CrediallWallet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLimit() {
        return this.limit;
    }

    public CrediallWallet limit(BigDecimal limit) {
        this.setLimit(limit);
        return this;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public CrediallWallet balance(BigDecimal balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getSpent() {
        return this.spent;
    }

    public CrediallWallet spent(BigDecimal spent) {
        this.setSpent(spent);
        return this;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }

    public String getDescription() {
        return this.description;
    }

    public CrediallWallet description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public CrediallWallet sortOrder(Integer sortOrder) {
        this.setSortOrder(sortOrder);
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public LocalDate getDateAdded() {
        return this.dateAdded;
    }

    public CrediallWallet dateAdded(LocalDate dateAdded) {
        this.setDateAdded(dateAdded);
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateModified() {
        return this.dateModified;
    }

    public CrediallWallet dateModified(LocalDate dateModified) {
        this.setDateModified(dateModified);
        return this;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public CrediallWalletStatus getStatus() {
        return this.status;
    }

    public CrediallWallet status(CrediallWalletStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CrediallWalletStatus status) {
        this.status = status;
    }

    public CustomerAccount getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerAccount customerAccount) {
        this.customer = customerAccount;
    }

    public CrediallWallet customer(CustomerAccount customerAccount) {
        this.setCustomer(customerAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrediallWallet)) {
            return false;
        }
        return id != null && id.equals(((CrediallWallet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrediallWallet{" +
            "id=" + getId() +
            ", limit=" + getLimit() +
            ", balance=" + getBalance() +
            ", spent=" + getSpent() +
            ", description='" + getDescription() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
