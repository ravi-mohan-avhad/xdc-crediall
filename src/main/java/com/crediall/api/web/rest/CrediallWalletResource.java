package com.crediall.api.web.rest;

import com.crediall.api.domain.CrediallWallet;
import com.crediall.api.repository.CrediallWalletRepository;
import com.crediall.api.service.CrediallWalletService;
import com.crediall.api.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.crediall.api.domain.CrediallWallet}.
 */
@RestController
@RequestMapping("/api")
public class CrediallWalletResource {

    private final Logger log = LoggerFactory.getLogger(CrediallWalletResource.class);

    private static final String ENTITY_NAME = "crediallWallet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrediallWalletService crediallWalletService;

    private final CrediallWalletRepository crediallWalletRepository;

    public CrediallWalletResource(CrediallWalletService crediallWalletService, CrediallWalletRepository crediallWalletRepository) {
        this.crediallWalletService = crediallWalletService;
        this.crediallWalletRepository = crediallWalletRepository;
    }

    /**
     * {@code POST  /crediall-wallets} : Create a new crediallWallet.
     *
     * @param crediallWallet the crediallWallet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crediallWallet, or with status {@code 400 (Bad Request)} if the crediallWallet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crediall-wallets")
    public ResponseEntity<CrediallWallet> createCrediallWallet(@Valid @RequestBody CrediallWallet crediallWallet)
        throws URISyntaxException {
        log.debug("REST request to save CrediallWallet : {}", crediallWallet);
        if (crediallWallet.getId() != null) {
            throw new BadRequestAlertException("A new crediallWallet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CrediallWallet result = crediallWalletService.save(crediallWallet);
        return ResponseEntity
            .created(new URI("/api/crediall-wallets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crediall-wallets/:id} : Updates an existing crediallWallet.
     *
     * @param id the id of the crediallWallet to save.
     * @param crediallWallet the crediallWallet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crediallWallet,
     * or with status {@code 400 (Bad Request)} if the crediallWallet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crediallWallet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crediall-wallets/{id}")
    public ResponseEntity<CrediallWallet> updateCrediallWallet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CrediallWallet crediallWallet
    ) throws URISyntaxException {
        log.debug("REST request to update CrediallWallet : {}, {}", id, crediallWallet);
        if (crediallWallet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crediallWallet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crediallWalletRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CrediallWallet result = crediallWalletService.update(crediallWallet);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crediallWallet.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crediall-wallets/:id} : Partial updates given fields of an existing crediallWallet, field will ignore if it is null
     *
     * @param id the id of the crediallWallet to save.
     * @param crediallWallet the crediallWallet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crediallWallet,
     * or with status {@code 400 (Bad Request)} if the crediallWallet is not valid,
     * or with status {@code 404 (Not Found)} if the crediallWallet is not found,
     * or with status {@code 500 (Internal Server Error)} if the crediallWallet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crediall-wallets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CrediallWallet> partialUpdateCrediallWallet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CrediallWallet crediallWallet
    ) throws URISyntaxException {
        log.debug("REST request to partial update CrediallWallet partially : {}, {}", id, crediallWallet);
        if (crediallWallet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crediallWallet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crediallWalletRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CrediallWallet> result = crediallWalletService.partialUpdate(crediallWallet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crediallWallet.getId().toString())
        );
    }

    /**
     * {@code GET  /crediall-wallets} : get all the crediallWallets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crediallWallets in body.
     */
    @GetMapping("/crediall-wallets")
    public ResponseEntity<List<CrediallWallet>> getAllCrediallWallets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CrediallWallets");
        Page<CrediallWallet> page = crediallWalletService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crediall-wallets/:id} : get the "id" crediallWallet.
     *
     * @param id the id of the crediallWallet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crediallWallet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crediall-wallets/{id}")
    public ResponseEntity<CrediallWallet> getCrediallWallet(@PathVariable Long id) {
        log.debug("REST request to get CrediallWallet : {}", id);
        Optional<CrediallWallet> crediallWallet = crediallWalletService.findOne(id);
        return ResponseUtil.wrapOrNotFound(crediallWallet);
    }

    /**
     * {@code DELETE  /crediall-wallets/:id} : delete the "id" crediallWallet.
     *
     * @param id the id of the crediallWallet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crediall-wallets/{id}")
    public ResponseEntity<Void> deleteCrediallWallet(@PathVariable Long id) {
        log.debug("REST request to delete CrediallWallet : {}", id);
        crediallWalletService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
