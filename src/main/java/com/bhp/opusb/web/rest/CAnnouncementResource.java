package com.bhp.opusb.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.bhp.opusb.service.CAnnouncementQueryService;
import com.bhp.opusb.service.CAnnouncementService;
import com.bhp.opusb.service.dto.CAnnouncementCriteria;
import com.bhp.opusb.service.dto.CAnnouncementDTO;
import com.bhp.opusb.service.dto.CAnnouncementPublishDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.CAnnouncement}.
 */
@RestController
@RequestMapping("/api")
public class CAnnouncementResource {

    private final Logger log = LoggerFactory.getLogger(CAnnouncementResource.class);

    private static final String ENTITY_NAME = "cAnnouncement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CAnnouncementService cAnnouncementService;

    private final CAnnouncementQueryService cAnnouncementQueryService;

    public CAnnouncementResource(CAnnouncementService cAnnouncementService, CAnnouncementQueryService cAnnouncementQueryService) {
        this.cAnnouncementService = cAnnouncementService;
        this.cAnnouncementQueryService = cAnnouncementQueryService;
    }

    /**
     * {@code POST  /c-announcements} : Create a new cAnnouncement.
     *
     * @param cAnnouncementDTO the cAnnouncementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cAnnouncementDTO, or with status {@code 400 (Bad Request)} if the cAnnouncement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-announcements")
    public ResponseEntity<CAnnouncementDTO> createCAnnouncement(@Valid @RequestBody CAnnouncementDTO cAnnouncementDTO) throws URISyntaxException {
        log.debug("REST request to save CAnnouncement : {}", cAnnouncementDTO);
        if (cAnnouncementDTO.getId() != null) {
            log.debug("update C CAnnouncement");
            updateCAnnouncement(cAnnouncementDTO);
            return ResponseEntity.ok(cAnnouncementDTO);

        }
        CAnnouncementDTO result = cAnnouncementService.save(cAnnouncementDTO);
        return ResponseEntity.created(new URI("/api/c-announcements/" + result.getId()))
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
    @PostMapping("/c-announcements/publish/{id}")
    public ResponseEntity<CAnnouncementDTO> publishAnnouncement(@PathVariable Long id, @RequestBody CAnnouncementPublishDTO cAnnouncementPublishDTO) throws URISyntaxException, MessagingException, IOException {
        log.debug("REST request to publish CAnnouncement : {}", cAnnouncementPublishDTO);
        if (id == null) {
            throw new BadRequestAlertException("Cannot publish announcement without its ID", ENTITY_NAME, "noid");
        }

        cAnnouncementService.publish(cAnnouncementPublishDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code PUT  /c-announcements} : Updates an existing cAnnouncement.
     *
     * @param cAnnouncementDTO the cAnnouncementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cAnnouncementDTO,
     * or with status {@code 400 (Bad Request)} if the cAnnouncementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cAnnouncementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-announcements")
    public ResponseEntity<CAnnouncementDTO> updateCAnnouncement(@Valid @RequestBody CAnnouncementDTO cAnnouncementDTO) throws URISyntaxException {
        log.debug("REST request to update CAnnouncement : {}", cAnnouncementDTO);
        if (cAnnouncementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CAnnouncementDTO result = cAnnouncementService.save(cAnnouncementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cAnnouncementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-announcements} : get all the cAnnouncements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cAnnouncements in body.
     */
    @GetMapping("/c-announcements")
    public ResponseEntity<List<CAnnouncementDTO>> getAllCAnnouncements(CAnnouncementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CAnnouncements by criteria: {}", criteria);
        Page<CAnnouncementDTO> page = cAnnouncementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }



    /**
     * {@code GET  /c-announcements/count} : count all the cAnnouncements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-announcements/count")
    public ResponseEntity<Long> countCAnnouncements(CAnnouncementCriteria criteria) {
        log.debug("REST request to count CAnnouncements by criteria: {}", criteria);
        return ResponseEntity.ok().body(cAnnouncementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-announcements/:id} : get the "id" cAnnouncement.
     *
     * @param id the id of the cAnnouncementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cAnnouncementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-announcements/{id}")
    public ResponseEntity<CAnnouncementDTO> getCAnnouncement(@PathVariable Long id) {
        log.debug("REST request to get CAnnouncement : {}", id);
        Optional<CAnnouncementDTO> cAnnouncementDTO = cAnnouncementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cAnnouncementDTO);
    }

    @GetMapping("/c-announcementemaillist/{id}")
    public ResponseEntity<Map<String,Object>> listemail(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to list CAnnouncement : {}");
        return ResponseEntity.ok(cAnnouncementService.emailInvitation(id));
    }

    /**
     * {@code DELETE  /c-announcements/:id} : delete the "id" cAnnouncement.
     *
     * @param id the id of the cAnnouncementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-announcements/{id}")
    public ResponseEntity<Void> deleteCAnnouncement(@PathVariable Long id) {
        log.debug("REST request to delete CAnnouncement : {}", id);
        cAnnouncementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
