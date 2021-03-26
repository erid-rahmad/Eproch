package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MProjectInformationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MProjectInformationDTO;
import com.bhp.opusb.service.dto.MProjectInformationCriteria;
import com.bhp.opusb.service.MProjectInformationQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.MProjectInformation}.
 */
@RestController
@RequestMapping("/api")
public class MProjectInformationResource {

    private final Logger log = LoggerFactory.getLogger(MProjectInformationResource.class);

    private static final String ENTITY_NAME = "mProjectInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProjectInformationService mProjectInformationService;

    private final MProjectInformationQueryService mProjectInformationQueryService;

    public MProjectInformationResource(MProjectInformationService mProjectInformationService, MProjectInformationQueryService mProjectInformationQueryService) {
        this.mProjectInformationService = mProjectInformationService;
        this.mProjectInformationQueryService = mProjectInformationQueryService;
    }

    /**
     * {@code POST  /m-project-informations} : Create a new mProjectInformation.
     *
     * @param mProjectInformationDTO the mProjectInformationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProjectInformationDTO, or with status {@code 400 (Bad Request)} if the mProjectInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-project-informations")
    public ResponseEntity<MProjectInformationDTO> createMProjectInformation(@Valid @RequestBody MProjectInformationDTO mProjectInformationDTO) throws URISyntaxException {
        log.debug("REST request to save MProjectInformation : {}", mProjectInformationDTO);
        if (mProjectInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProjectInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProjectInformationDTO result = mProjectInformationService.save(mProjectInformationDTO);
        return ResponseEntity.created(new URI("/api/m-project-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-project-informations} : Updates an existing mProjectInformation.
     *
     * @param mProjectInformationDTO the mProjectInformationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProjectInformationDTO,
     * or with status {@code 400 (Bad Request)} if the mProjectInformationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProjectInformationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-project-informations")
    public ResponseEntity<MProjectInformationDTO> updateMProjectInformation(@Valid @RequestBody MProjectInformationDTO mProjectInformationDTO) throws URISyntaxException {
        log.debug("REST request to update MProjectInformation : {}", mProjectInformationDTO);
        if (mProjectInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProjectInformationDTO result = mProjectInformationService.save(mProjectInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProjectInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-project-informations} : get all the mProjectInformations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProjectInformations in body.
     */
    @GetMapping("/m-project-informations")
    public ResponseEntity<List<MProjectInformationDTO>> getAllMProjectInformations(MProjectInformationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProjectInformations by criteria: {}", criteria);
        Page<MProjectInformationDTO> page = mProjectInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-project-informations/count} : count all the mProjectInformations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-project-informations/count")
    public ResponseEntity<Long> countMProjectInformations(MProjectInformationCriteria criteria) {
        log.debug("REST request to count MProjectInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProjectInformationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-project-informations/:id} : get the "id" mProjectInformation.
     *
     * @param id the id of the mProjectInformationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProjectInformationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-project-informations/{id}")
    public ResponseEntity<MProjectInformationDTO> getMProjectInformation(@PathVariable Long id) {
        log.debug("REST request to get MProjectInformation : {}", id);
        Optional<MProjectInformationDTO> mProjectInformationDTO = mProjectInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProjectInformationDTO);
    }

    @GetMapping("/m-project-informationsby-bidding/{id}")
    public ResponseEntity<?> getMProjectInformationbybidding(@PathVariable Long id) {
        log.debug("REST request to get MProjectInformation : {}", id);
        List<MProjectInformationDTO> mProjectInformationDTO = mProjectInformationService.findByBindId(id);
        return ResponseEntity.ok(mProjectInformationDTO);
    }

    /**
     * {@code DELETE  /m-project-informations/:id} : delete the "id" mProjectInformation.
     *
     * @param id the id of the mProjectInformationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-project-informations/{id}")
    public ResponseEntity<Void> deleteMProjectInformation(@PathVariable Long id) {
        log.debug("REST request to delete MProjectInformation : {}", id);
        mProjectInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
