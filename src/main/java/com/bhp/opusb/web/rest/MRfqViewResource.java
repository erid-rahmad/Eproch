package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MRfqViewService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MRfqViewDTO;
import com.bhp.opusb.service.dto.MRfqSubmissionDTO;
import com.bhp.opusb.service.dto.MRfqViewCriteria;
import com.bhp.opusb.service.MRfqViewQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MRfqView}.
 */
@RestController
@RequestMapping("/api")
public class MRfqViewResource {

    private final Logger log = LoggerFactory.getLogger(MRfqViewResource.class);

    private final MRfqViewService mRfqViewService;

    private final MRfqViewQueryService mRfqViewQueryService;

    public MRfqViewResource(MRfqViewService mRfqViewService, MRfqViewQueryService mRfqViewQueryService) {
        this.mRfqViewService = mRfqViewService;
        this.mRfqViewQueryService = mRfqViewQueryService;
    }

    /**
     * {@code GET  /m-rfq-views} : get all the mRfqViews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mRfqViews in body.
     */
    @GetMapping("/m-rfq-views")
    public ResponseEntity<List<MRfqViewDTO>> getAllMRfqViews(MRfqViewCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MRfqViews by criteria: {}", criteria);
        Page<MRfqViewDTO> page = mRfqViewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-rfq-views/count} : count all the mRfqViews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-rfq-views/count")
    public ResponseEntity<Long> countMRfqViews(MRfqViewCriteria criteria) {
        log.debug("REST request to count MRfqViews by criteria: {}", criteria);
        return ResponseEntity.ok().body(mRfqViewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-rfq-views/:id} : get the "id" mRfqView.
     *
     * @param id the id of the mRfqViewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mRfqViewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-rfq-views/{id}")
    public ResponseEntity<MRfqViewDTO> getMRfqView(@PathVariable Long id) {
        log.debug("REST request to get MRfqView : {}", id);
        Optional<MRfqViewDTO> mRfqViewDTO = mRfqViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mRfqViewDTO);
    }
}
