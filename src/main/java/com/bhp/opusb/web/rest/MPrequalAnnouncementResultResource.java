package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalAnnouncementResultService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalAnnouncementResultDTO;
import com.bhp.opusb.service.dto.MPrequalAnnouncementResultCriteria;
import com.bhp.opusb.service.MPrequalAnnouncementResultQueryService;

import io.github.jhipster.service.filter.StringFilter;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalAnnouncementResult}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalAnnouncementResultResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalAnnouncementResultResource.class);

    private static final String ENTITY_NAME = "mPrequalAnnouncementResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalAnnouncementResultService mPrequalAnnouncementResultService;

    private final MPrequalAnnouncementResultQueryService mPrequalAnnouncementResultQueryService;

    public MPrequalAnnouncementResultResource(MPrequalAnnouncementResultService mPrequalAnnouncementResultService, MPrequalAnnouncementResultQueryService mPrequalAnnouncementResultQueryService) {
        this.mPrequalAnnouncementResultService = mPrequalAnnouncementResultService;
        this.mPrequalAnnouncementResultQueryService = mPrequalAnnouncementResultQueryService;
    }

    /**
     * {@code POST  /m-prequal-announcement-results} : Create a new mPrequalAnnouncementResult.
     *
     * @param mPrequalAnnouncementResultDTO the mPrequalAnnouncementResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalAnnouncementResultDTO, or with status {@code 400 (Bad Request)} if the mPrequalAnnouncementResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequal-announcement-results")
    public ResponseEntity<MPrequalAnnouncementResultDTO> createMPrequalAnnouncementResult(@Valid @RequestBody MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalAnnouncementResult : {}", mPrequalAnnouncementResultDTO);
        if (mPrequalAnnouncementResultDTO.getId() != null) {
            updateMPrequalAnnouncementResult(mPrequalAnnouncementResultDTO);
            return ResponseEntity.ok(mPrequalAnnouncementResultDTO);
        }
        MPrequalAnnouncementResultCriteria criteria = new MPrequalAnnouncementResultCriteria();
        StringFilter filter = new StringFilter();
        filter.setEquals(mPrequalAnnouncementResultDTO.getDocumentNo());
        criteria.setDocumentNo(filter);
        Long count = mPrequalAnnouncementResultQueryService.countByCriteria(criteria);
        if(count>0) throw new BadRequestAlertException("Document No. "+mPrequalAnnouncementResultDTO.getDocumentNo()+" already existed.", ENTITY_NAME, "idexists");

        MPrequalAnnouncementResultDTO result = mPrequalAnnouncementResultService.save(mPrequalAnnouncementResultDTO);
        return ResponseEntity.created(new URI("/api/m-prequal-announcement-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequal-announcement-results} : Updates an existing mPrequalAnnouncementResult.
     *
     * @param mPrequalAnnouncementResultDTO the mPrequalAnnouncementResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalAnnouncementResultDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalAnnouncementResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalAnnouncementResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequal-announcement-results")
    public ResponseEntity<MPrequalAnnouncementResultDTO> updateMPrequalAnnouncementResult(@Valid @RequestBody MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalAnnouncementResult : {}", mPrequalAnnouncementResultDTO);
        if (mPrequalAnnouncementResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalAnnouncementResultDTO result = mPrequalAnnouncementResultService.save(mPrequalAnnouncementResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalAnnouncementResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequal-announcement-results} : get all the mPrequalAnnouncementResults.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalAnnouncementResults in body.
     */
    @GetMapping("/m-prequal-announcement-results")
    public ResponseEntity<List<MPrequalAnnouncementResultDTO>> getAllMPrequalAnnouncementResults(MPrequalAnnouncementResultCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalAnnouncementResults by criteria: {}", criteria);
        Page<MPrequalAnnouncementResultDTO> page = mPrequalAnnouncementResultQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequal-announcement-results/count} : count all the mPrequalAnnouncementResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequal-announcement-results/count")
    public ResponseEntity<Long> countMPrequalAnnouncementResults(MPrequalAnnouncementResultCriteria criteria) {
        log.debug("REST request to count MPrequalAnnouncementResults by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalAnnouncementResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequal-announcement-results/:id} : get the "id" mPrequalAnnouncementResult.
     *
     * @param id the id of the mPrequalAnnouncementResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalAnnouncementResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequal-announcement-results/{id}")
    public ResponseEntity<MPrequalAnnouncementResultDTO> getMPrequalAnnouncementResult(@PathVariable Long id) {
        log.debug("REST request to get MPrequalAnnouncementResult : {}", id);
        Optional<MPrequalAnnouncementResultDTO> mPrequalAnnouncementResultDTO = mPrequalAnnouncementResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalAnnouncementResultDTO);
    }

    /**
     * {@code DELETE  /m-prequal-announcement-results/:id} : delete the "id" mPrequalAnnouncementResult.
     *
     * @param id the id of the mPrequalAnnouncementResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequal-announcement-results/{id}")
    public ResponseEntity<Void> deleteMPrequalAnnouncementResult(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalAnnouncementResult : {}", id);
        mPrequalAnnouncementResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
