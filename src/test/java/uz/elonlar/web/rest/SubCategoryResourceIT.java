package uz.elonlar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import uz.elonlar.IntegrationTest;
import uz.elonlar.domain.SubCategory;
import uz.elonlar.repository.SubCategoryRepository;

/**
 * Integration tests for the {@link SubCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubCategoryResourceIT {

    private static final String DEFAULT_NAME_UZ_LAT = "AAAAAAAAAA";
    private static final String UPDATED_NAME_UZ_LAT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_UZ_KR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_UZ_KR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_RU = "AAAAAAAAAA";
    private static final String UPDATED_NAME_RU = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubCategoryMockMvc;

    private SubCategory subCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategory createEntity(EntityManager em) {
        SubCategory subCategory = new SubCategory()
            .nameUzLat(DEFAULT_NAME_UZ_LAT)
            .nameUzKr(DEFAULT_NAME_UZ_KR)
            .nameRu(DEFAULT_NAME_RU)
            .nameEn(DEFAULT_NAME_EN);
        return subCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategory createUpdatedEntity(EntityManager em) {
        SubCategory subCategory = new SubCategory()
            .nameUzLat(UPDATED_NAME_UZ_LAT)
            .nameUzKr(UPDATED_NAME_UZ_KR)
            .nameRu(UPDATED_NAME_RU)
            .nameEn(UPDATED_NAME_EN);
        return subCategory;
    }

    @BeforeEach
    public void initTest() {
        subCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createSubCategory() throws Exception {
        int databaseSizeBeforeCreate = subCategoryRepository.findAll().size();
        // Create the SubCategory
        restSubCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategory)))
            .andExpect(status().isCreated());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getNameUzLat()).isEqualTo(DEFAULT_NAME_UZ_LAT);
        assertThat(testSubCategory.getNameUzKr()).isEqualTo(DEFAULT_NAME_UZ_KR);
        assertThat(testSubCategory.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testSubCategory.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
    }

    @Test
    @Transactional
    void createSubCategoryWithExistingId() throws Exception {
        // Create the SubCategory with an existing ID
        subCategory.setId(1L);

        int databaseSizeBeforeCreate = subCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubCategories() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get all the subCategoryList
        restSubCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameUzLat").value(hasItem(DEFAULT_NAME_UZ_LAT)))
            .andExpect(jsonPath("$.[*].nameUzKr").value(hasItem(DEFAULT_NAME_UZ_KR)))
            .andExpect(jsonPath("$.[*].nameRu").value(hasItem(DEFAULT_NAME_RU)))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)));
    }

    @Test
    @Transactional
    void getSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get the subCategory
        restSubCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, subCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subCategory.getId().intValue()))
            .andExpect(jsonPath("$.nameUzLat").value(DEFAULT_NAME_UZ_LAT))
            .andExpect(jsonPath("$.nameUzKr").value(DEFAULT_NAME_UZ_KR))
            .andExpect(jsonPath("$.nameRu").value(DEFAULT_NAME_RU))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN));
    }

    @Test
    @Transactional
    void getNonExistingSubCategory() throws Exception {
        // Get the subCategory
        restSubCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Update the subCategory
        SubCategory updatedSubCategory = subCategoryRepository.findById(subCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSubCategory are not directly saved in db
        em.detach(updatedSubCategory);
        updatedSubCategory.nameUzLat(UPDATED_NAME_UZ_LAT).nameUzKr(UPDATED_NAME_UZ_KR).nameRu(UPDATED_NAME_RU).nameEn(UPDATED_NAME_EN);

        restSubCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubCategory))
            )
            .andExpect(status().isOk());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getNameUzLat()).isEqualTo(UPDATED_NAME_UZ_LAT);
        assertThat(testSubCategory.getNameUzKr()).isEqualTo(UPDATED_NAME_UZ_KR);
        assertThat(testSubCategory.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testSubCategory.getNameEn()).isEqualTo(UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void putNonExistingSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubCategoryWithPatch() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Update the subCategory using partial update
        SubCategory partialUpdatedSubCategory = new SubCategory();
        partialUpdatedSubCategory.setId(subCategory.getId());

        partialUpdatedSubCategory.nameUzLat(UPDATED_NAME_UZ_LAT).nameEn(UPDATED_NAME_EN);

        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategory))
            )
            .andExpect(status().isOk());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getNameUzLat()).isEqualTo(UPDATED_NAME_UZ_LAT);
        assertThat(testSubCategory.getNameUzKr()).isEqualTo(DEFAULT_NAME_UZ_KR);
        assertThat(testSubCategory.getNameRu()).isEqualTo(DEFAULT_NAME_RU);
        assertThat(testSubCategory.getNameEn()).isEqualTo(UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void fullUpdateSubCategoryWithPatch() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Update the subCategory using partial update
        SubCategory partialUpdatedSubCategory = new SubCategory();
        partialUpdatedSubCategory.setId(subCategory.getId());

        partialUpdatedSubCategory
            .nameUzLat(UPDATED_NAME_UZ_LAT)
            .nameUzKr(UPDATED_NAME_UZ_KR)
            .nameRu(UPDATED_NAME_RU)
            .nameEn(UPDATED_NAME_EN);

        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategory))
            )
            .andExpect(status().isOk());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getNameUzLat()).isEqualTo(UPDATED_NAME_UZ_LAT);
        assertThat(testSubCategory.getNameUzKr()).isEqualTo(UPDATED_NAME_UZ_KR);
        assertThat(testSubCategory.getNameRu()).isEqualTo(UPDATED_NAME_RU);
        assertThat(testSubCategory.getNameEn()).isEqualTo(UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    void patchNonExistingSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();
        subCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        int databaseSizeBeforeDelete = subCategoryRepository.findAll().size();

        // Delete the subCategory
        restSubCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, subCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
