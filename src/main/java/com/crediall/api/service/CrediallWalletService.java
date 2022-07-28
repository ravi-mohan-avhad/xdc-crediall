package com.crediall.api.service;

import com.crediall.api.domain.CrediallWallet;
import com.crediall.api.repository.CrediallWalletRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CrediallWallet}.
 */
@Service
@Transactional
public class CrediallWalletService {

    private final Logger log = LoggerFactory.getLogger(CrediallWalletService.class);

    private final CrediallWalletRepository crediallWalletRepository;

    public CrediallWalletService(CrediallWalletRepository crediallWalletRepository) {
        this.crediallWalletRepository = crediallWalletRepository;
    }

    /**
     * Save a crediallWallet.
     *
     * @param crediallWallet the entity to save.
     * @return the persisted entity.
     */
    public CrediallWallet save(CrediallWallet crediallWallet) {
        log.debug("Request to save CrediallWallet : {}", crediallWallet);
        return crediallWalletRepository.save(crediallWallet);
    }

    /**
     * Update a crediallWallet.
     *
     * @param crediallWallet the entity to save.
     * @return the persisted entity.
     */
    public CrediallWallet update(CrediallWallet crediallWallet) {
        log.debug("Request to save CrediallWallet : {}", crediallWallet);
        return crediallWalletRepository.save(crediallWallet);
    }

    /**
     * Partially update a crediallWallet.
     *
     * @param crediallWallet the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CrediallWallet> partialUpdate(CrediallWallet crediallWallet) {
        log.debug("Request to partially update CrediallWallet : {}", crediallWallet);

        return crediallWalletRepository
            .findById(crediallWallet.getId())
            .map(existingCrediallWallet -> {
                if (crediallWallet.getLimit() != null) {
                    existingCrediallWallet.setLimit(crediallWallet.getLimit());
                }
                if (crediallWallet.getBalance() != null) {
                    existingCrediallWallet.setBalance(crediallWallet.getBalance());
                }
                if (crediallWallet.getSpent() != null) {
                    existingCrediallWallet.setSpent(crediallWallet.getSpent());
                }
                if (crediallWallet.getDescription() != null) {
                    existingCrediallWallet.setDescription(crediallWallet.getDescription());
                }
                if (crediallWallet.getSortOrder() != null) {
                    existingCrediallWallet.setSortOrder(crediallWallet.getSortOrder());
                }
                if (crediallWallet.getDateAdded() != null) {
                    existingCrediallWallet.setDateAdded(crediallWallet.getDateAdded());
                }
                if (crediallWallet.getDateModified() != null) {
                    existingCrediallWallet.setDateModified(crediallWallet.getDateModified());
                }
                if (crediallWallet.getStatus() != null) {
                    existingCrediallWallet.setStatus(crediallWallet.getStatus());
                }

                return existingCrediallWallet;
            })
            .map(crediallWalletRepository::save);
    }

    /**
     * Get all the crediallWallets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CrediallWallet> findAll(Pageable pageable) {
        log.debug("Request to get all CrediallWallets");
        return crediallWalletRepository.findAll(pageable);
    }

    /**
     * Get one crediallWallet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CrediallWallet> findOne(Long id) {
        log.debug("Request to get CrediallWallet : {}", id);
        return crediallWalletRepository.findById(id);
    }

    /**
     * Delete the crediallWallet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CrediallWallet : {}", id);
        crediallWalletRepository.deleteById(id);
    }
}
