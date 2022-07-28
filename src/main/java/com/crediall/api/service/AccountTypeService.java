package com.crediall.api.service;

import com.crediall.api.domain.AccountType;
import com.crediall.api.repository.AccountTypeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountType}.
 */
@Service
@Transactional
public class AccountTypeService {

    private final Logger log = LoggerFactory.getLogger(AccountTypeService.class);

    private final AccountTypeRepository accountTypeRepository;

    public AccountTypeService(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    /**
     * Save a accountType.
     *
     * @param accountType the entity to save.
     * @return the persisted entity.
     */
    public AccountType save(AccountType accountType) {
        log.debug("Request to save AccountType : {}", accountType);
        return accountTypeRepository.save(accountType);
    }

    /**
     * Update a accountType.
     *
     * @param accountType the entity to save.
     * @return the persisted entity.
     */
    public AccountType update(AccountType accountType) {
        log.debug("Request to save AccountType : {}", accountType);
        return accountTypeRepository.save(accountType);
    }

    /**
     * Partially update a accountType.
     *
     * @param accountType the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccountType> partialUpdate(AccountType accountType) {
        log.debug("Request to partially update AccountType : {}", accountType);

        return accountTypeRepository
            .findById(accountType.getId())
            .map(existingAccountType -> {
                if (accountType.getName() != null) {
                    existingAccountType.setName(accountType.getName());
                }
                if (accountType.getKeywords() != null) {
                    existingAccountType.setKeywords(accountType.getKeywords());
                }
                if (accountType.getDescription() != null) {
                    existingAccountType.setDescription(accountType.getDescription());
                }
                if (accountType.getRating() != null) {
                    existingAccountType.setRating(accountType.getRating());
                }
                if (accountType.getDateAdded() != null) {
                    existingAccountType.setDateAdded(accountType.getDateAdded());
                }
                if (accountType.getDateModified() != null) {
                    existingAccountType.setDateModified(accountType.getDateModified());
                }

                return existingAccountType;
            })
            .map(accountTypeRepository::save);
    }

    /**
     * Get all the accountTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountType> findAll(Pageable pageable) {
        log.debug("Request to get all AccountTypes");
        return accountTypeRepository.findAll(pageable);
    }

    /**
     * Get one accountType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccountType> findOne(Long id) {
        log.debug("Request to get AccountType : {}", id);
        return accountTypeRepository.findById(id);
    }

    /**
     * Delete the accountType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccountType : {}", id);
        accountTypeRepository.deleteById(id);
    }
}
