package uz.elonlar.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import uz.elonlar.domain.Announcement;
import uz.elonlar.domain.enumeration.AnnouncementStatus;
import uz.elonlar.repository.AnnouncementRepository;

/**
 * Integration tests for the {@link AnnouncementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnnouncementResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_SALARY = 1L;
    private static final Long UPDATED_SALARY = 2L;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final AnnouncementStatus DEFAULT_STATUS = AnnouncementStatus.ACTIVE;
    private static final AnnouncementStatus UPDATED_STATUS = AnnouncementStatus.INACTIVE;

    private static final String ENTITY_API_URL = "/api/announcements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnnouncementMockMvc;

    private Announcement announcement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hireDate(DEFAULT_HIRE_DATE)
            .salary(DEFAULT_SALARY)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .status(DEFAULT_STATUS);
        return announcement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createUpdatedEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .status(UPDATED_STATUS);
        return announcement;
    }

    @BeforeEach
    public void initTest() {
        announcement = createEntity(em);
    }

    @Test
    @Transactional
    void createAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = announcementRepository.findAll().size();
        // Create the Announcement
        restAnnouncementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isCreated());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeCreate + 1);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAnnouncement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAnnouncement.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAnnouncement.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAnnouncement.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testAnnouncement.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testAnnouncement.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAnnouncement.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAnnouncement.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createAnnouncementWithExistingId() throws Exception {
        // Create the Announcement with an existing ID
        announcement.setId(1L);

        int databaseSizeBeforeCreate = announcementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnouncementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnnouncements() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList
        restAnnouncementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(announcement.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get the announcement
        restAnnouncementMockMvc
            .perform(get(ENTITY_API_URL_ID, announcement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(announcement.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnnouncement() throws Exception {
        // Get the announcement
        restAnnouncementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement
        Announcement updatedAnnouncement = announcementRepository.findById(announcement.getId()).get();
        // Disconnect from session so that the updates on updatedAnnouncement are not directly saved in db
        em.detach(updatedAnnouncement);
        updatedAnnouncement
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .status(UPDATED_STATUS);

        restAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnnouncement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAnnouncement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAnnouncement.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAnnouncement.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAnnouncement.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testAnnouncement.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testAnnouncement.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAnnouncement.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAnnouncement.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, announcement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnnouncementWithPatch() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement using partial update
        Announcement partialUpdatedAnnouncement = new Announcement();
        partialUpdatedAnnouncement.setId(announcement.getId());

        partialUpdatedAnnouncement
            .title(UPDATED_TITLE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);

        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAnnouncement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAnnouncement.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAnnouncement.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAnnouncement.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testAnnouncement.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testAnnouncement.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAnnouncement.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAnnouncement.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateAnnouncementWithPatch() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement using partial update
        Announcement partialUpdatedAnnouncement = new Announcement();
        partialUpdatedAnnouncement.setId(announcement.getId());

        partialUpdatedAnnouncement
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .status(UPDATED_STATUS);

        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnouncement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnouncement))
            )
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAnnouncement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAnnouncement.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAnnouncement.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAnnouncement.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testAnnouncement.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testAnnouncement.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAnnouncement.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAnnouncement.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, announcement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();
        announcement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(announcement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeDelete = announcementRepository.findAll().size();

        // Delete the announcement
        restAnnouncementMockMvc
            .perform(delete(ENTITY_API_URL_ID, announcement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
