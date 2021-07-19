package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalAnnouncementService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalAnnouncementDTO;
import com.bhp.opusb.service.dto.MPrequalAnnouncementPublishDTO;
import com.bhp.opusb.service.dto.MPrequalAnnouncementCriteria;
import com.bhp.opusb.service.MPrequalAnnouncementQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalAnnouncement}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalAnnouncementResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalAnnouncementResource.class);

    private static final String ENTITY_NAME = "mPrequalAnnouncement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalAnnouncementService mPrequalAnnouncementService;

    private final MPrequalAnnouncementQueryService mPrequalAnnouncementQueryService;

    public MPrequalAnnouncementResource(MPrequalAnnouncementService mPrequalAnnouncementService, MPrequalAnnouncementQueryService mPrequalAnnouncementQueryService) {
        this.mPrequalAnnouncementService = mPrequalAnnouncementService;
        this.mPrequalAnnouncementQueryService = mPrequalAnnouncementQueryService;
    }

    /**
     * {@code POST  /m-prequal-announcements} : Create a new mPrequalAnnouncement.
     *
     * @param mPrequalAnnouncementDTO the mPrequalAnnouncementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalAnnouncementDTO, or with status {@code 400 (Bad Request)} if the mPrequalAnnouncement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequal-announcements")
    public ResponseEntity<MPrequalAnnouncementDTO> createMPrequalAnnouncement(@Valid @RequestBody MPrequalAnnouncementDTO mPrequalAnnouncementDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalAnnouncement : {}", mPrequalAnnouncementDTO);
        if (mPrequalAnnouncementDTO.getId() != null) {
            log.debug("updating prequalification announcement id {}", mPrequalAnnouncementDTO.getId());
            updateMPrequalAnnouncement(mPrequalAnnouncementDTO);
            return ResponseEntity.ok(mPrequalAnnouncementDTO);
        }
        MPrequalAnnouncementDTO result = mPrequalAnnouncementService.save(mPrequalAnnouncementDTO);
        return ResponseEntity.created(new URI("/api/m-prequal-announcements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /c-announcements} : Create a new cAnnouncement.
     *
     * @param cAnnouncementPublishDTO the cAnnouncementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cAnnouncementDTO, or with status {@code 400 (Bad Request)} if the cAnnouncement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequal-announcements/publish/{id}")
    public ResponseEntity<MPrequalAnnouncementDTO> publishAnnouncement(@PathVariable Long id, @RequestBody MPrequalAnnouncementPublishDTO mPrequalAnnouncementPublishDTO) throws URISyntaxException, MessagingException, IOException {
        log.debug("REST request to publish MPrequalAnnouncementPublishDTO : {}", mPrequalAnnouncementPublishDTO);
        if (id == null) {
            throw new BadRequestAlertException("Cannot publish announcement without its ID", ENTITY_NAME, "noid");
        }

        mPrequalAnnouncementService.publish(mPrequalAnnouncementPublishDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code PUT  /m-prequal-announcements} : Updates an existing mPrequalAnnouncement.
     *
     * @param mPrequalAnnouncementDTO the mPrequalAnnouncementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalAnnouncementDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalAnnouncementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalAnnouncementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequal-announcements")
    public ResponseEntity<MPrequalAnnouncementDTO> updateMPrequalAnnouncement(@Valid @RequestBody MPrequalAnnouncementDTO mPrequalAnnouncementDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalAnnouncement : {}", mPrequalAnnouncementDTO);
        if (mPrequalAnnouncementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalAnnouncementDTO result = mPrequalAnnouncementService.save(mPrequalAnnouncementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalAnnouncementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequal-announcements} : get all the mPrequalAnnouncements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalAnnouncements in body.
     */
    @GetMapping("/m-prequal-announcements")
    public ResponseEntity<List<MPrequalAnnouncementDTO>> getAllMPrequalAnnouncements(MPrequalAnnouncementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalAnnouncements by criteria: {}", criteria);
        Page<MPrequalAnnouncementDTO> page = mPrequalAnnouncementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequal-announcements/count} : count all the mPrequalAnnouncements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequal-announcements/count")
    public ResponseEntity<Long> countMPrequalAnnouncements(MPrequalAnnouncementCriteria criteria) {
        log.debug("REST request to count MPrequalAnnouncements by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalAnnouncementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequal-announcements/:id} : get the "id" mPrequalAnnouncement.
     *
     * @param id the id of the mPrequalAnnouncementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalAnnouncementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequal-announcements/{id}")
    public ResponseEntity<MPrequalAnnouncementDTO> getMPrequalAnnouncement(@PathVariable Long id) {
        log.debug("REST request to get MPrequalAnnouncement : {}", id);
        Optional<MPrequalAnnouncementDTO> mPrequalAnnouncementDTO = mPrequalAnnouncementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalAnnouncementDTO);
    }

    /**
     * {@code DELETE  /m-prequal-announcements/:id} : delete the "id" mPrequalAnnouncement.
     *
     * @param id the id of the mPrequalAnnouncementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequal-announcements/{id}")
    public ResponseEntity<Void> deleteMPrequalAnnouncement(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalAnnouncement : {}", id);
        mPrequalAnnouncementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
