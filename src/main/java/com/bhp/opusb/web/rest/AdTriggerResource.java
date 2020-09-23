package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.AdTriggerQueryService;
import com.bhp.opusb.service.AdTriggerService;
import com.bhp.opusb.service.dto.AdTriggerCriteria;
import com.bhp.opusb.service.dto.AdTriggerDTO;
import com.bhp.opusb.service.dto.TriggerResult;
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
 * REST controller for managing {@link com.bhp.opusb.domain.AdTrigger}.
 */
@RestController
@RequestMapping("/api")
public class AdTriggerResource {

    private final Logger log = LoggerFactory.getLogger(AdTriggerResource.class);

    private static final String ENTITY_NAME = "adTrigger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdTriggerService adTriggerService;

    private final AdTriggerQueryService adTriggerQueryService;

    public AdTriggerResource(AdTriggerService adTriggerService, AdTriggerQueryService adTriggerQueryService) {
        this.adTriggerService = adTriggerService;
        this.adTriggerQueryService = adTriggerQueryService;
    }

    /**
     * {@code POST  /ad-triggers} : Create a new adTrigger.
     *
     * @param adTriggerDTO the adTriggerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adTriggerDTO, or with status {@code 400 (Bad Request)} if the adTrigger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-triggers")
    public ResponseEntity<AdTriggerDTO> createAdTrigger(@Valid @RequestBody AdTriggerDTO adTriggerDTO) throws URISyntaxException {
        log.debug("REST request to save AdTrigger : {}", adTriggerDTO);
        if (adTriggerDTO.getId() != null) {
            throw new BadRequestAlertException("A new adTrigger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdTriggerDTO result = adTriggerService.save(adTriggerDTO);
        return ResponseEntity.created(new URI("/api/ad-triggers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-triggers} : Updates an existing adTrigger.
     *
     * @param adTriggerDTO the adTriggerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adTriggerDTO,
     * or with status {@code 400 (Bad Request)} if the adTriggerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adTriggerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-triggers")
    public ResponseEntity<AdTriggerDTO> updateAdTrigger(@Valid @RequestBody AdTriggerDTO adTriggerDTO) throws URISyntaxException {
        log.debug("REST request to update AdTrigger : {}", adTriggerDTO);
        if (adTriggerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdTriggerDTO result = adTriggerService.save(adTriggerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adTriggerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-triggers} : get all the adTriggers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adTriggers in body.
     */
    @GetMapping("/ad-triggers")
    public ResponseEntity<List<AdTriggerDTO>> getAllAdTriggers(AdTriggerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdTriggers by criteria: {}", criteria);
        Page<AdTriggerDTO> page = adTriggerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-triggers/count} : count all the adTriggers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-triggers/count")
    public ResponseEntity<Long> countAdTriggers(AdTriggerCriteria criteria) {
        log.debug("REST request to count AdTriggers by criteria: {}", criteria);
        return ResponseEntity.ok().body(adTriggerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-triggers/:id} : get the "id" adTrigger.
     *
     * @param id the id of the adTriggerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adTriggerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-triggers/{id}")
    public ResponseEntity<AdTriggerDTO> getAdTrigger(@PathVariable Long id) {
        log.debug("REST request to get AdTrigger : {}", id);
        Optional<AdTriggerDTO> adTriggerDTO = adTriggerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adTriggerDTO);
    }

    /**
     * {@code DELETE  /ad-triggers/:id} : delete the "id" adTrigger.
     *
     * @param id the id of the adTriggerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-triggers/{id}")
    public ResponseEntity<Void> deleteAdTrigger(@PathVariable Long id) {
        log.debug("REST request to delete AdTrigger : {}", id);
        adTriggerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

  /**
   * {@code post  /process-trigger} : Execute a process.
   *
   * @param serviceName the process name to execute.
   * @param params the process parameters.
   * @return the {@link ResponseEntity} with status {@code 200 (ok)} and with
   *         body 1, or with status {@code 400 (bad request)} if
   *         the name is not provided.
   * @throws URISyntaxException if the location uri syntax is incorrect.
   */
  @PostMapping("/ad-triggers/process/{serviceName}")
  public ResponseEntity<Object> runProcess(@PathVariable String serviceName, @RequestBody Map<String, Object> params)
      throws URISyntaxException {
    log.debug("REST request to run process: {}", serviceName);
    if (serviceName == null) {
      throw new BadRequestAlertException("Service name is not defined", "ProcessTrigger", "serviceNameUndefined");
    }

    TriggerResult result = adTriggerService.executeProcess(serviceName, params);
    return ResponseEntity.ok().body(result.get());
  }
}
