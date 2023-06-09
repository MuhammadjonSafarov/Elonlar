package uz.elonlar.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.elonlar.domain.Announcement;
import uz.elonlar.repository.AnnouncementRepository;
import uz.elonlar.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.elonlar.domain.Announcement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AnnouncementResource {

    private final Logger log = LoggerFactory.getLogger(AnnouncementResource.class);

    private static final String ENTITY_NAME = "announcement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnouncementRepository announcementRepository;

    public AnnouncementResource(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    /**
     * {@code POST  /announcements} : Create a new announcement.
     *
     * @param announcement the announcement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new announcement, or with status {@code 400 (Bad Request)} if the announcement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/announcements")
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement) throws URISyntaxException {
        log.debug("REST request to save Announcement : {}", announcement);
        if (announcement.getId() != null) {
            throw new BadRequestAlertException("A new announcement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Announcement result = announcementRepository.save(announcement);
        return ResponseEntity
            .created(new URI("/api/announcements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /announcements/:id} : Updates an existing announcement.
     *
     * @param id the id of the announcement to save.
     * @param announcement the announcement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated announcement,
     * or with status {@code 400 (Bad Request)} if the announcement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the announcement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/announcements/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Announcement announcement
    ) throws URISyntaxException {
        log.debug("REST request to update Announcement : {}, {}", id, announcement);
        if (announcement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, announcement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!announcementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Announcement result = announcementRepository.save(announcement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, announcement.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /announcements/:id} : Partial updates given fields of an existing announcement, field will ignore if it is null
     *
     * @param id the id of the announcement to save.
     * @param announcement the announcement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated announcement,
     * or with status {@code 400 (Bad Request)} if the announcement is not valid,
     * or with status {@code 404 (Not Found)} if the announcement is not found,
     * or with status {@code 500 (Internal Server Error)} if the announcement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/announcements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Announcement> partialUpdateAnnouncement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Announcement announcement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Announcement partially : {}, {}", id, announcement);
        if (announcement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, announcement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!announcementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Announcement> result = announcementRepository
            .findById(announcement.getId())
            .map(existingAnnouncement -> {
                if (announcement.getTitle() != null) {
                    existingAnnouncement.setTitle(announcement.getTitle());
                }
                if (announcement.getDescription() != null) {
                    existingAnnouncement.setDescription(announcement.getDescription());
                }
                if (announcement.getEmail() != null) {
                    existingAnnouncement.setEmail(announcement.getEmail());
                }
                if (announcement.getPhoneNumber() != null) {
                    existingAnnouncement.setPhoneNumber(announcement.getPhoneNumber());
                }
                if (announcement.getHireDate() != null) {
                    existingAnnouncement.setHireDate(announcement.getHireDate());
                }
                if (announcement.getSalary() != null) {
                    existingAnnouncement.setSalary(announcement.getSalary());
                }
                if (announcement.getLongitude() != null) {
                    existingAnnouncement.setLongitude(announcement.getLongitude());
                }
                if (announcement.getLatitude() != null) {
                    existingAnnouncement.setLatitude(announcement.getLatitude());
                }
                if (announcement.getStatus() != null) {
                    existingAnnouncement.setStatus(announcement.getStatus());
                }

                return existingAnnouncement;
            })
            .map(announcementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, announcement.getId().toString())
        );
    }

    /**
     * {@code GET  /announcements} : get all the announcements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of announcements in body.
     */
    @GetMapping("/announcements")
    public ResponseEntity<List<Announcement>> getAllAnnouncements(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Announcements");
        Page<Announcement> page = announcementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /announcements/:id} : get the "id" announcement.
     *
     * @param id the id of the announcement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the announcement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/announcements/{id}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable Long id) {
        log.debug("REST request to get Announcement : {}", id);
        Optional<Announcement> announcement = announcementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(announcement);
    }

    /**
     * {@code DELETE  /announcements/:id} : delete the "id" announcement.
     *
     * @param id the id of the announcement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        log.debug("REST request to delete Announcement : {}", id);
        announcementRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
