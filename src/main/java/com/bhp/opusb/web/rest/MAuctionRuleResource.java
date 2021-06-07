package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionRuleQueryService;
import com.bhp.opusb.service.MAuctionRuleService;
import com.bhp.opusb.service.dto.MAuctionRuleCriteria;
import com.bhp.opusb.service.dto.MAuctionRuleDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionRule}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionRuleResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionRuleResource.class);

    private static final String ENTITY_NAME = "mAuctionRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionRuleService mAuctionRuleService;

    private final MAuctionRuleQueryService mAuctionRuleQueryService;

    public MAuctionRuleResource(MAuctionRuleService mAuctionRuleService, MAuctionRuleQueryService mAuctionRuleQueryService) {
        this.mAuctionRuleService = mAuctionRuleService;
        this.mAuctionRuleQueryService = mAuctionRuleQueryService;
    }

    /**
     * {@code POST  /m-auction-rules} : Create a new mAuctionRule.
     *
     * @param mAuctionRuleDTO the mAuctionRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionRuleDTO, or with status {@code 400 (Bad Request)} if the mAuctionRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-rules")
    public ResponseEntity<MAuctionRuleDTO> createMAuctionRule(@Valid @RequestBody MAuctionRuleDTO mAuctionRuleDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionRule : {}", mAuctionRuleDTO);
        if (mAuctionRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionRuleDTO result = mAuctionRuleService.save(mAuctionRuleDTO);
        return ResponseEntity.created(new URI("/api/m-auction-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-rules} : Updates an existing mAuctionRule.
     *
     * @param mAuctionRuleDTO the mAuctionRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionRuleDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-rules")
    public ResponseEntity<MAuctionRuleDTO> updateMAuctionRule(@Valid @RequestBody MAuctionRuleDTO mAuctionRuleDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionRule : {}", mAuctionRuleDTO);
        if (mAuctionRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionRuleDTO result = mAuctionRuleService.save(mAuctionRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-rules} : get all the mAuctionRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionRules in body.
     */
    @GetMapping("/m-auction-rules")
    public ResponseEntity<List<MAuctionRuleDTO>> getAllMAuctionRules(MAuctionRuleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionRules by criteria: {}", criteria);
        Page<MAuctionRuleDTO> page = mAuctionRuleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-rules/count} : count all the mAuctionRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-rules/count")
    public ResponseEntity<Long> countMAuctionRules(MAuctionRuleCriteria criteria) {
        log.debug("REST request to count MAuctionRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionRuleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-rules/:id} : get the "id" mAuctionRule.
     *
     * @param id the id of the mAuctionRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-rules/{id}")
    public ResponseEntity<MAuctionRuleDTO> getMAuctionRule(@PathVariable Long id) {
        log.debug("REST request to get MAuctionRule : {}", id);
        Optional<MAuctionRuleDTO> mAuctionRuleDTO = mAuctionRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionRuleDTO);
    }

    /**
     * {@code DELETE  /m-auction-rules/:id} : delete the "id" mAuctionRule.
     *
     * @param id the id of the mAuctionRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-rules/{id}")
    public ResponseEntity<Void> deleteMAuctionRule(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionRule : {}", id);
        mAuctionRuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
