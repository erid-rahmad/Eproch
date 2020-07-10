package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdTriggerParamService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdTriggerParamDTO;
import com.bhp.opusb.service.dto.AdTriggerParamCriteria;
import com.bhp.opusb.service.AdTriggerParamQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdTriggerParam}.
 */
@RestController
@RequestMapping("/api")
public class AdTriggerParamResource {

    private final Logger log = LoggerFactory.getLogger(AdTriggerParamResource.class);

    private static final String ENTITY_NAME = "adTriggerParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdTriggerParamService adTriggerParamService;

    private final AdTriggerParamQueryService adTriggerParamQueryService;

    public AdTriggerParamResource(AdTriggerParamService adTriggerParamService, AdTriggerParamQueryService adTriggerParamQueryService) {
        this.adTriggerParamService = adTriggerParamService;
        this.adTriggerParamQueryService = adTriggerParamQueryService;
    }

    /**
     * {@code POST  /ad-trigger-params} : Create a new adTriggerParam.
     *
     * @param adTriggerParamDTO the adTriggerParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adTriggerParamDTO, or with status {@code 400 (Bad Request)} if the adTriggerParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-trigger-params")
    public ResponseEntity<AdTriggerParamDTO> createAdTriggerParam(@Valid @RequestBody AdTriggerParamDTO adTriggerParamDTO) throws URISyntaxException {
        log.debug("REST request to save AdTriggerParam : {}", adTriggerParamDTO);
        if (adTriggerParamDTO.getId() != null) {
            throw new BadRequestAlertException("A new adTriggerParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdTriggerParamDTO result = adTriggerParamService.save(adTriggerParamDTO);
        return ResponseEntity.created(new URI("/api/ad-trigger-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-trigger-params} : Updates an existing adTriggerParam.
     *
     * @param adTriggerParamDTO the adTriggerParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adTriggerParamDTO,
     * or with status {@code 400 (Bad Request)} if the adTriggerParamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adTriggerParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-trigger-params")
    public ResponseEntity<AdTriggerParamDTO> updateAdTriggerParam(@Valid @RequestBody AdTriggerParamDTO adTriggerParamDTO) throws URISyntaxException {
        log.debug("REST request to update AdTriggerParam : {}", adTriggerParamDTO);
        if (adTriggerParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdTriggerParamDTO result = adTriggerParamService.save(adTriggerParamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adTriggerParamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-trigger-params} : get all the adTriggerParams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adTriggerParams in body.
     */
    @GetMapping("/ad-trigger-params")
    public ResponseEntity<List<AdTriggerParamDTO>> getAllAdTriggerParams(AdTriggerParamCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdTriggerParams by criteria: {}", criteria);
        Page<AdTriggerParamDTO> page = adTriggerParamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-trigger-params/count} : count all the adTriggerParams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-trigger-params/count")
    public ResponseEntity<Long> countAdTriggerParams(AdTriggerParamCriteria criteria) {
        log.debug("REST request to count AdTriggerParams by criteria: {}", criteria);
        return ResponseEntity.ok().body(adTriggerParamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-trigger-params/:id} : get the "id" adTriggerParam.
     *
     * @param id the id of the adTriggerParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adTriggerParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-trigger-params/{id}")
    public ResponseEntity<AdTriggerParamDTO> getAdTriggerParam(@PathVariable Long id) {
        log.debug("REST request to get AdTriggerParam : {}", id);
        Optional<AdTriggerParamDTO> adTriggerParamDTO = adTriggerParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adTriggerParamDTO);
    }

    /**
     * {@code DELETE  /ad-trigger-params/:id} : delete the "id" adTriggerParam.
     *
     * @param id the id of the adTriggerParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-trigger-params/{id}")
    public ResponseEntity<Void> deleteAdTriggerParam(@PathVariable Long id) {
        log.debug("REST request to delete AdTriggerParam : {}", id);
        adTriggerParamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
