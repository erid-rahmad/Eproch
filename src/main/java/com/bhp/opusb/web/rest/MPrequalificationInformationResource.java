package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationInformationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationInformationDTO;
import com.bhp.opusb.service.dto.MPrequalificationFormDTO;
import com.bhp.opusb.service.dto.MPrequalificationInformationCriteria;
import com.bhp.opusb.domain.enumeration.MPrequalificationProcess;
import com.bhp.opusb.service.MPrequalificationInformationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationInformation}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationInformationResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationInformationResource.class);

    private static final String ENTITY_NAME = "mPrequalificationInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationInformationService mPrequalificationInformationService;

    private final MPrequalificationInformationQueryService mPrequalificationInformationQueryService;

    public MPrequalificationInformationResource(MPrequalificationInformationService mPrequalificationInformationService, MPrequalificationInformationQueryService mPrequalificationInformationQueryService) {
        this.mPrequalificationInformationService = mPrequalificationInformationService;
        this.mPrequalificationInformationQueryService = mPrequalificationInformationQueryService;
    }

    /**
     * {@code POST  /m-prequalification-informations} : Create a new mPrequalificationInformation.
     *
     * @param mPrequalificationInformationDTO the mPrequalificationInformationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationInformationDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-informations")
    public ResponseEntity<MPrequalificationInformationDTO> createMPrequalificationInformation(@Valid @RequestBody MPrequalificationInformationDTO mPrequalificationInformationDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationInformation : {}", mPrequalificationInformationDTO);
        if (mPrequalificationInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationInformationDTO result = mPrequalificationInformationService.save(mPrequalificationInformationDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequalification-informations} : Updates an existing mPrequalificationInformation.
     *
     * @param mPrequalificationInformationDTO the mPrequalificationInformationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationInformationDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationInformationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationInformationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-informations")
    public ResponseEntity<MPrequalificationInformationDTO> updateMPrequalificationInformation(@Valid @RequestBody MPrequalificationInformationDTO mPrequalificationInformationDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationInformation : {}", mPrequalificationInformationDTO);
        if (mPrequalificationInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationInformationDTO result = mPrequalificationInformationService.save(mPrequalificationInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-prequalification-informations} : Create a new mPrequalificationInformation.
     *
     * @param mPrequalificationInformationDTO the mPrequalificationInformationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationInformationDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationInformation has already an ID.
     * @throw
     */
    @PostMapping("/m-prequalification-informations/save-form")
    public ResponseEntity<MPrequalificationInformationDTO> createMPreqForm(@RequestBody MPrequalificationFormDTO mPrequalificationInformationDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationInformation : {}", mPrequalificationInformationDTO);
        if (mPrequalificationInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationInformationDTO result = mPrequalificationInformationService.saveForm(mPrequalificationInformationDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/m-prequalification-informations/save-form")
    public ResponseEntity<MPrequalificationInformationDTO> updateMPreqForm(@RequestBody MPrequalificationFormDTO mPrequalificationInformationDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationInformation : {}", mPrequalificationInformationDTO);
        if (mPrequalificationInformationDTO.getId() == null) {
            throw new BadRequestAlertException("ID Cannot be null", ENTITY_NAME, "idnull");
        }
        MPrequalificationInformationDTO result = mPrequalificationInformationService.saveForm(mPrequalificationInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-biddings/form/:id} : get the "id" mBidding (for the form).
     *
     * @param id the id of the mBiddingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-informations/{stepName}/{id}")
    public ResponseEntity<MPrequalificationFormDTO> getMBiddingForm(@PathVariable String stepName, @PathVariable Long id) {
        log.debug("REST request to get MBidding : {}", id);
        MPrequalificationProcess step;
        
        try {
            step = MPrequalificationProcess.valueOf(stepName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException("Invalid step name", ENTITY_NAME, "invalidstep");
        }
        
        Optional<MPrequalificationFormDTO> mPreqFormDTO = mPrequalificationInformationService.findOneForm(id, step);
        return ResponseUtil.wrapOrNotFound(mPreqFormDTO);
    }

    /**
     * {@code GET  /m-prequalification-informations} : get all the mPrequalificationInformations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationInformations in body.
     */
    @GetMapping("/m-prequalification-informations")
    public ResponseEntity<List<MPrequalificationInformationDTO>> getAllMPrequalificationInformations(MPrequalificationInformationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationInformations by criteria: {}", criteria);
        Page<MPrequalificationInformationDTO> page = mPrequalificationInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-informations/count} : count all the mPrequalificationInformations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-informations/count")
    public ResponseEntity<Long> countMPrequalificationInformations(MPrequalificationInformationCriteria criteria) {
        log.debug("REST request to count MPrequalificationInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationInformationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-informations/:id} : get the "id" mPrequalificationInformation.
     *
     * @param id the id of the mPrequalificationInformationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationInformationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-informations/{id}")
    public ResponseEntity<MPrequalificationInformationDTO> getMPrequalificationInformation(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationInformation : {}", id);
        Optional<MPrequalificationInformationDTO> mPrequalificationInformationDTO = mPrequalificationInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationInformationDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-informations/:id} : delete the "id" mPrequalificationInformation.
     *
     * @param id the id of the mPrequalificationInformationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-informations/{id}")
    public ResponseEntity<Void> deleteMPrequalificationInformation(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationInformation : {}", id);
        mPrequalificationInformationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
