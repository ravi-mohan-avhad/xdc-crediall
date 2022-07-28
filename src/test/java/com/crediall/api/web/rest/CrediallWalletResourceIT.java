package com.crediall.api.web.rest;

import static com.crediall.api.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.crediall.api.IntegrationTest;
import com.crediall.api.domain.CrediallWallet;
import com.crediall.api.domain.enumeration.CrediallWalletStatus;
import com.crediall.api.repository.CrediallWalletRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CrediallWalletResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CrediallWalletResourceIT {

    private static final BigDecimal DEFAULT_LIMIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIMIT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SPENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SPENT = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final CrediallWalletStatus DEFAULT_STATUS = CrediallWalletStatus.AVAILABLE;
    private static final CrediallWalletStatus UPDATED_STATUS = CrediallWalletStatus.RESTRICTED;

    private static final String ENTITY_API_URL = "/api/crediall-wallets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CrediallWalletRepository crediallWalletRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCrediallWalletMockMvc;

    private CrediallWallet crediallWallet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrediallWallet createEntity(EntityManager em) {
        CrediallWallet crediallWallet = new CrediallWallet()
            .limit(DEFAULT_LIMIT)
            .balance(DEFAULT_BALANCE)
            .spent(DEFAULT_SPENT)
            .description(DEFAULT_DESCRIPTION)
            .sortOrder(DEFAULT_SORT_ORDER)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .status(DEFAULT_STATUS);
        return crediallWallet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrediallWallet createUpdatedEntity(EntityManager em) {
        CrediallWallet crediallWallet = new CrediallWallet()
            .limit(UPDATED_LIMIT)
            .balance(UPDATED_BALANCE)
            .spent(UPDATED_SPENT)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .status(UPDATED_STATUS);
        return crediallWallet;
    }

    @BeforeEach
    public void initTest() {
        crediallWallet = createEntity(em);
    }

    @Test
    @Transactional
    void createCrediallWallet() throws Exception {
        int databaseSizeBeforeCreate = crediallWalletRepository.findAll().size();
        // Create the CrediallWallet
        restCrediallWalletMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crediallWallet))
            )
            .andExpect(status().isCreated());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeCreate + 1);
        CrediallWallet testCrediallWallet = crediallWalletList.get(crediallWalletList.size() - 1);
        assertThat(testCrediallWallet.getLimit()).isEqualByComparingTo(DEFAULT_LIMIT);
        assertThat(testCrediallWallet.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
        assertThat(testCrediallWallet.getSpent()).isEqualByComparingTo(DEFAULT_SPENT);
        assertThat(testCrediallWallet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCrediallWallet.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testCrediallWallet.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testCrediallWallet.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testCrediallWallet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createCrediallWalletWithExistingId() throws Exception {
        // Create the CrediallWallet with an existing ID
        crediallWallet.setId(1L);

        int databaseSizeBeforeCreate = crediallWalletRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrediallWalletMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crediallWallet))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = crediallWalletRepository.findAll().size();
        // set the field null
        crediallWallet.setDescription(null);

        // Create the CrediallWallet, which fails.

        restCrediallWalletMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crediallWallet))
            )
            .andExpect(status().isBadRequest());

        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCrediallWallets() throws Exception {
        // Initialize the database
        crediallWalletRepository.saveAndFlush(crediallWallet);

        // Get all the crediallWalletList
        restCrediallWalletMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crediallWallet.getId().intValue())))
            .andExpect(jsonPath("$.[*].limit").value(hasItem(sameNumber(DEFAULT_LIMIT))))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))))
            .andExpect(jsonPath("$.[*].spent").value(hasItem(sameNumber(DEFAULT_SPENT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getCrediallWallet() throws Exception {
        // Initialize the database
        crediallWalletRepository.saveAndFlush(crediallWallet);

        // Get the crediallWallet
        restCrediallWalletMockMvc
            .perform(get(ENTITY_API_URL_ID, crediallWallet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crediallWallet.getId().intValue()))
            .andExpect(jsonPath("$.limit").value(sameNumber(DEFAULT_LIMIT)))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)))
            .andExpect(jsonPath("$.spent").value(sameNumber(DEFAULT_SPENT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCrediallWallet() throws Exception {
        // Get the crediallWallet
        restCrediallWalletMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCrediallWallet() throws Exception {
        // Initialize the database
        crediallWalletRepository.saveAndFlush(crediallWallet);

        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();

        // Update the crediallWallet
        CrediallWallet updatedCrediallWallet = crediallWalletRepository.findById(crediallWallet.getId()).get();
        // Disconnect from session so that the updates on updatedCrediallWallet are not directly saved in db
        em.detach(updatedCrediallWallet);
        updatedCrediallWallet
            .limit(UPDATED_LIMIT)
            .balance(UPDATED_BALANCE)
            .spent(UPDATED_SPENT)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .status(UPDATED_STATUS);

        restCrediallWalletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCrediallWallet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCrediallWallet))
            )
            .andExpect(status().isOk());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
        CrediallWallet testCrediallWallet = crediallWalletList.get(crediallWalletList.size() - 1);
        assertThat(testCrediallWallet.getLimit()).isEqualByComparingTo(UPDATED_LIMIT);
        assertThat(testCrediallWallet.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testCrediallWallet.getSpent()).isEqualByComparingTo(UPDATED_SPENT);
        assertThat(testCrediallWallet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCrediallWallet.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testCrediallWallet.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testCrediallWallet.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCrediallWallet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCrediallWallet() throws Exception {
        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();
        crediallWallet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrediallWalletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crediallWallet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crediallWallet))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCrediallWallet() throws Exception {
        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();
        crediallWallet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrediallWalletMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crediallWallet))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCrediallWallet() throws Exception {
        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();
        crediallWallet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrediallWalletMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crediallWallet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCrediallWalletWithPatch() throws Exception {
        // Initialize the database
        crediallWalletRepository.saveAndFlush(crediallWallet);

        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();

        // Update the crediallWallet using partial update
        CrediallWallet partialUpdatedCrediallWallet = new CrediallWallet();
        partialUpdatedCrediallWallet.setId(crediallWallet.getId());

        partialUpdatedCrediallWallet
            .limit(UPDATED_LIMIT)
            .balance(UPDATED_BALANCE)
            .spent(UPDATED_SPENT)
            .description(UPDATED_DESCRIPTION)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restCrediallWalletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrediallWallet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrediallWallet))
            )
            .andExpect(status().isOk());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
        CrediallWallet testCrediallWallet = crediallWalletList.get(crediallWalletList.size() - 1);
        assertThat(testCrediallWallet.getLimit()).isEqualByComparingTo(UPDATED_LIMIT);
        assertThat(testCrediallWallet.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testCrediallWallet.getSpent()).isEqualByComparingTo(UPDATED_SPENT);
        assertThat(testCrediallWallet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCrediallWallet.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testCrediallWallet.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testCrediallWallet.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCrediallWallet.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCrediallWalletWithPatch() throws Exception {
        // Initialize the database
        crediallWalletRepository.saveAndFlush(crediallWallet);

        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();

        // Update the crediallWallet using partial update
        CrediallWallet partialUpdatedCrediallWallet = new CrediallWallet();
        partialUpdatedCrediallWallet.setId(crediallWallet.getId());

        partialUpdatedCrediallWallet
            .limit(UPDATED_LIMIT)
            .balance(UPDATED_BALANCE)
            .spent(UPDATED_SPENT)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .status(UPDATED_STATUS);

        restCrediallWalletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrediallWallet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrediallWallet))
            )
            .andExpect(status().isOk());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
        CrediallWallet testCrediallWallet = crediallWalletList.get(crediallWalletList.size() - 1);
        assertThat(testCrediallWallet.getLimit()).isEqualByComparingTo(UPDATED_LIMIT);
        assertThat(testCrediallWallet.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
        assertThat(testCrediallWallet.getSpent()).isEqualByComparingTo(UPDATED_SPENT);
        assertThat(testCrediallWallet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCrediallWallet.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testCrediallWallet.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testCrediallWallet.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCrediallWallet.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCrediallWallet() throws Exception {
        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();
        crediallWallet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrediallWalletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, crediallWallet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crediallWallet))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCrediallWallet() throws Exception {
        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();
        crediallWallet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrediallWalletMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crediallWallet))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCrediallWallet() throws Exception {
        int databaseSizeBeforeUpdate = crediallWalletRepository.findAll().size();
        crediallWallet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrediallWalletMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(crediallWallet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CrediallWallet in the database
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCrediallWallet() throws Exception {
        // Initialize the database
        crediallWalletRepository.saveAndFlush(crediallWallet);

        int databaseSizeBeforeDelete = crediallWalletRepository.findAll().size();

        // Delete the crediallWallet
        restCrediallWalletMockMvc
            .perform(delete(ENTITY_API_URL_ID, crediallWallet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CrediallWallet> crediallWalletList = crediallWalletRepository.findAll();
        assertThat(crediallWalletList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
