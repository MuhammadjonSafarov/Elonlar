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
import uz.elonlar.domain.District;
import uz.elonlar.repository.DistrictRepository;
import uz.elonlar.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.elonlar.domain.District}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DistrictResource {

    private final Logger log = LoggerFactory.getLogger(DistrictResource.class);

    private static final String ENTITY_NAME = "district";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictRepository districtRepository;

    public DistrictResource(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    /**
     * {@code POST  /districts} : Create a new district.
     *
     * @param district the district to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new district, or with status {@code 400 (Bad Request)} if the district has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/districts")
    public ResponseEntity<District> createDistrict(@RequestBody District district) throws URISyntaxException {
        log.debug("REST request to save District : {}", district);
        if (district.getId() != null) {
            throw new BadRequestAlertException("A new district cannot already have an ID", ENTITY_NAME, "idexists");
        }
        District result = districtRepository.save(district);
        return ResponseEntity
            .created(new URI("/api/districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /districts/:id} : Updates an existing district.
     *
     * @param id the id of the district to save.
     * @param district the district to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated district,
     * or with status {@code 400 (Bad Request)} if the district is not valid,
     * or with status {@code 500 (Internal Server Error)} if the district couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/districts/{id}")
    public ResponseEntity<District> updateDistrict(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody District district
    ) throws URISyntaxException {
        log.debug("REST request to update District : {}, {}", id, district);
        if (district.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, district.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        District result = districtRepository.save(district);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, district.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /districts/:id} : Partial updates given fields of an existing district, field will ignore if it is null
     *
     * @param id the id of the district to save.
     * @param district the district to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated district,
     * or with status {@code 400 (Bad Request)} if the district is not valid,
     * or with status {@code 404 (Not Found)} if the district is not found,
     * or with status {@code 500 (Internal Server Error)} if the district couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/districts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<District> partialUpdateDistrict(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody District district
    ) throws URISyntaxException {
        log.debug("REST request to partial update District partially : {}, {}", id, district);
        if (district.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, district.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<District> result = districtRepository
            .findById(district.getId())
            .map(existingDistrict -> {
                if (district.getNameUzLat() != null) {
                    existingDistrict.setNameUzLat(district.getNameUzLat());
                }
                if (district.getNameUzKr() != null) {
                    existingDistrict.setNameUzKr(district.getNameUzKr());
                }
                if (district.getNameRu() != null) {
                    existingDistrict.setNameRu(district.getNameRu());
                }
                if (district.getNameEn() != null) {
                    existingDistrict.setNameEn(district.getNameEn());
                }
                if (district.getLongitude() != null) {
                    existingDistrict.setLongitude(district.getLongitude());
                }
                if (district.getLatitude() != null) {
                    existingDistrict.setLatitude(district.getLatitude());
                }

                return existingDistrict;
            })
            .map(districtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, district.getId().toString())
        );
    }
    /**
     * {@code GET  /districts} : get all the districts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districts in body.
     */
    @GetMapping("/districts")
    public ResponseEntity<List<District>> getAllDistricts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Districts");
        Page<District> page = districtRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /districts/:id} : get the "id" district.
     *
     * @param id the id of the district to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the district, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/districts/{id}")
    public ResponseEntity<District> getDistrict(@PathVariable Long id) {
        log.debug("REST request to get District : {}", id);
        Optional<District> district = districtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(district);
    }

    /**
     * {@code DELETE  /districts/:id} : delete the "id" district.
     *
     * @param id the id of the district to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        log.debug("REST request to delete District : {}", id);
        districtRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
