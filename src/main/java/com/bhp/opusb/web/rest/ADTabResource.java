package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.ADTabQueryService;
import com.bhp.opusb.service.ADTabService;
import com.bhp.opusb.service.dto.ADTabCriteria;
import com.bhp.opusb.service.dto.ADTabDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.ADTab}.
 */
@RestController
@RequestMapping("/api")
public class ADTabResource {

    private final Logger log = LoggerFactory.getLogger(ADTabResource.class);

    private static final String ENTITY_NAME = "aDTab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADTabService aDTabService;

    private final ADTabQueryService aDTabQueryService;

    public ADTabResource(ADTabService aDTabService, ADTabQueryService aDTabQueryService) {
        this.aDTabService = aDTabService;
        this.aDTabQueryService = aDTabQueryService;
    }

    /**
     * {@code POST  /ad-tabs} : Create a new aDTab.
     *
     * @param aDTabDTO the aDTabDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDTabDTO, or with status {@code 400 (Bad Request)} if the aDTab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-tabs")
    public ResponseEntity<ADTabDTO> createADTab(@Valid @RequestBody ADTabDTO aDTabDTO) throws URISyntaxException {
        log.debug("REST request to save ADTab : {}", aDTabDTO);
        if (aDTabDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDTab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADTabDTO result = aDTabService.save(aDTabDTO);
        return ResponseEntity.created(new URI("/api/ad-tabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-tabs} : Updates an existing aDTab.
     *
     * @param aDTabDTO the aDTabDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDTabDTO,
     * or with status {@code 400 (Bad Request)} if the aDTabDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDTabDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-tabs")
    public ResponseEntity<ADTabDTO> updateADTab(@Valid @RequestBody ADTabDTO aDTabDTO) throws URISyntaxException {
        log.debug("REST request to update ADTab : {}", aDTabDTO);
        if (aDTabDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADTabDTO result = aDTabService.save(aDTabDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDTabDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-tabs} : get all the aDTabs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDTabs in body.
     */
    @GetMapping("/ad-tabs")
    public ResponseEntity<List<ADTabDTO>> getAllADTabs(ADTabCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADTabs by criteria: {}", criteria);
        Page<ADTabDTO> page = aDTabQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ad-tabs/tree/{id}")
    public ResponseEntity<Map<String, Object>> getTabTree(@PathVariable Long id) {
        log.debug("REST request to get tab tree of tab ID : {}", id);
        Map<String, Object> tabTree = aDTabService.getTabTree(id);
        return ResponseEntity.ok().body(tabTree);
    }

    /**
     * {@code GET  /ad-tabs/count} : count all the aDTabs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-tabs/count")
    public ResponseEntity<Long> countADTabs(ADTabCriteria criteria) {
        log.debug("REST request to count ADTabs by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDTabQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-tabs/:id} : get the "id" aDTab.
     *
     * @param id the id of the aDTabDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDTabDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-tabs/{id}")
    public ResponseEntity<ADTabDTO> getADTab(@PathVariable Long id) {
        log.debug("REST request to get ADTab : {}", id);
        Optional<ADTabDTO> aDTabDTO = aDTabService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDTabDTO);
    }

    /**
     * {@code DELETE  /ad-tabs/:id} : delete the "id" aDTab.
     *
     * @param id the id of the aDTabDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-tabs/{id}")
    public ResponseEntity<Void> deleteADTab(@PathVariable Long id) {
        log.debug("REST request to delete ADTab : {}", id);
        aDTabService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
