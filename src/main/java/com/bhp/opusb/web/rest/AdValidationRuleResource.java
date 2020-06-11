package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdValidationRuleService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdValidationRuleDTO;
import com.bhp.opusb.service.dto.AdValidationRuleCriteria;
import com.bhp.opusb.service.AdValidationRuleQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdValidationRule}.
 */
@RestController
@RequestMapping("/api")
public class AdValidationRuleResource {

    private final Logger log = LoggerFactory.getLogger(AdValidationRuleResource.class);

    private static final String ENTITY_NAME = "adValidationRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdValidationRuleService adValidationRuleService;

    private final AdValidationRuleQueryService adValidationRuleQueryService;

    public AdValidationRuleResource(AdValidationRuleService adValidationRuleService, AdValidationRuleQueryService adValidationRuleQueryService) {
        this.adValidationRuleService = adValidationRuleService;
        this.adValidationRuleQueryService = adValidationRuleQueryService;
    }

    /**
     * {@code POST  /ad-validation-rules} : Create a new adValidationRule.
     *
     * @param adValidationRuleDTO the adValidationRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adValidationRuleDTO, or with status {@code 400 (Bad Request)} if the adValidationRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-validation-rules")
    public ResponseEntity<AdValidationRuleDTO> createAdValidationRule(@Valid @RequestBody AdValidationRuleDTO adValidationRuleDTO) throws URISyntaxException {
        log.debug("REST request to save AdValidationRule : {}", adValidationRuleDTO);
        if (adValidationRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new adValidationRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdValidationRuleDTO result = adValidationRuleService.save(adValidationRuleDTO);
        return ResponseEntity.created(new URI("/api/ad-validation-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-validation-rules} : Updates an existing adValidationRule.
     *
     * @param adValidationRuleDTO the adValidationRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adValidationRuleDTO,
     * or with status {@code 400 (Bad Request)} if the adValidationRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adValidationRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-validation-rules")
    public ResponseEntity<AdValidationRuleDTO> updateAdValidationRule(@Valid @RequestBody AdValidationRuleDTO adValidationRuleDTO) throws URISyntaxException {
        log.debug("REST request to update AdValidationRule : {}", adValidationRuleDTO);
        if (adValidationRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdValidationRuleDTO result = adValidationRuleService.save(adValidationRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adValidationRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-validation-rules} : get all the adValidationRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adValidationRules in body.
     */
    @GetMapping("/ad-validation-rules")
    public ResponseEntity<List<AdValidationRuleDTO>> getAllAdValidationRules(AdValidationRuleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdValidationRules by criteria: {}", criteria);
        Page<AdValidationRuleDTO> page = adValidationRuleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-validation-rules/count} : count all the adValidationRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-validation-rules/count")
    public ResponseEntity<Long> countAdValidationRules(AdValidationRuleCriteria criteria) {
        log.debug("REST request to count AdValidationRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(adValidationRuleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-validation-rules/:id} : get the "id" adValidationRule.
     *
     * @param id the id of the adValidationRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adValidationRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-validation-rules/{id}")
    public ResponseEntity<AdValidationRuleDTO> getAdValidationRule(@PathVariable Long id) {
        log.debug("REST request to get AdValidationRule : {}", id);
        Optional<AdValidationRuleDTO> adValidationRuleDTO = adValidationRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adValidationRuleDTO);
    }

    /**
     * {@code DELETE  /ad-validation-rules/:id} : delete the "id" adValidationRule.
     *
     * @param id the id of the adValidationRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-validation-rules/{id}")
    public ResponseEntity<Void> deleteAdValidationRule(@PathVariable Long id) {
        log.debug("REST request to delete AdValidationRule : {}", id);
        adValidationRuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
