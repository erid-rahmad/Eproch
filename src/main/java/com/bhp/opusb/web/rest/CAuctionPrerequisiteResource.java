package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CAuctionPrerequisiteQueryService;
import com.bhp.opusb.service.CAuctionPrerequisiteService;
import com.bhp.opusb.service.dto.CAuctionPrerequisiteCriteria;
import com.bhp.opusb.service.dto.CAuctionPrerequisiteDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CAuctionPrerequisite}.
 */
@RestController
@RequestMapping("/api")
public class CAuctionPrerequisiteResource {

    private final Logger log = LoggerFactory.getLogger(CAuctionPrerequisiteResource.class);

    private static final String ENTITY_NAME = "cAuctionPrerequisite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CAuctionPrerequisiteService cAuctionPrerequisiteService;

    private final CAuctionPrerequisiteQueryService cAuctionPrerequisiteQueryService;

    public CAuctionPrerequisiteResource(CAuctionPrerequisiteService cAuctionPrerequisiteService, CAuctionPrerequisiteQueryService cAuctionPrerequisiteQueryService) {
        this.cAuctionPrerequisiteService = cAuctionPrerequisiteService;
        this.cAuctionPrerequisiteQueryService = cAuctionPrerequisiteQueryService;
    }

    /**
     * {@code POST  /c-auction-prerequisites} : Create a new cAuctionPrerequisite.
     *
     * @param cAuctionPrerequisiteDTO the cAuctionPrerequisiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cAuctionPrerequisiteDTO, or with status {@code 400 (Bad Request)} if the cAuctionPrerequisite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-auction-prerequisites")
    public ResponseEntity<CAuctionPrerequisiteDTO> createCAuctionPrerequisite(@Valid @RequestBody CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO) throws URISyntaxException {
        log.debug("REST request to save CAuctionPrerequisite : {}", cAuctionPrerequisiteDTO);
        if (cAuctionPrerequisiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new cAuctionPrerequisite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CAuctionPrerequisiteDTO result = cAuctionPrerequisiteService.save(cAuctionPrerequisiteDTO);
        return ResponseEntity.created(new URI("/api/c-auction-prerequisites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-auction-prerequisites} : Updates an existing cAuctionPrerequisite.
     *
     * @param cAuctionPrerequisiteDTO the cAuctionPrerequisiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cAuctionPrerequisiteDTO,
     * or with status {@code 400 (Bad Request)} if the cAuctionPrerequisiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cAuctionPrerequisiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-auction-prerequisites")
    public ResponseEntity<CAuctionPrerequisiteDTO> updateCAuctionPrerequisite(@Valid @RequestBody CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO) throws URISyntaxException {
        log.debug("REST request to update CAuctionPrerequisite : {}", cAuctionPrerequisiteDTO);
        if (cAuctionPrerequisiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CAuctionPrerequisiteDTO result = cAuctionPrerequisiteService.save(cAuctionPrerequisiteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cAuctionPrerequisiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-auction-prerequisites} : get all the cAuctionPrerequisites.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cAuctionPrerequisites in body.
     */
    @GetMapping("/c-auction-prerequisites")
    public ResponseEntity<List<CAuctionPrerequisiteDTO>> getAllCAuctionPrerequisites(CAuctionPrerequisiteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CAuctionPrerequisites by criteria: {}", criteria);
        Page<CAuctionPrerequisiteDTO> page = cAuctionPrerequisiteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-auction-prerequisites/count} : count all the cAuctionPrerequisites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-auction-prerequisites/count")
    public ResponseEntity<Long> countCAuctionPrerequisites(CAuctionPrerequisiteCriteria criteria) {
        log.debug("REST request to count CAuctionPrerequisites by criteria: {}", criteria);
        return ResponseEntity.ok().body(cAuctionPrerequisiteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-auction-prerequisites/:id} : get the "id" cAuctionPrerequisite.
     *
     * @param id the id of the cAuctionPrerequisiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cAuctionPrerequisiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-auction-prerequisites/{id}")
    public ResponseEntity<CAuctionPrerequisiteDTO> getCAuctionPrerequisite(@PathVariable Long id) {
        log.debug("REST request to get CAuctionPrerequisite : {}", id);
        Optional<CAuctionPrerequisiteDTO> cAuctionPrerequisiteDTO = cAuctionPrerequisiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cAuctionPrerequisiteDTO);
    }

    /**
     * {@code DELETE  /c-auction-prerequisites/:id} : delete the "id" cAuctionPrerequisite.
     *
     * @param id the id of the cAuctionPrerequisiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-auction-prerequisites/{id}")
    public ResponseEntity<Void> deleteCAuctionPrerequisite(@PathVariable Long id) {
        log.debug("REST request to delete CAuctionPrerequisite : {}", id);
        cAuctionPrerequisiteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
