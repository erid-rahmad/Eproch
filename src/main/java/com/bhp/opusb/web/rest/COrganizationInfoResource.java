package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.COrganizationInfoService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.COrganizationInfoDTO;
import com.bhp.opusb.service.dto.COrganizationInfoCriteria;
import com.bhp.opusb.service.COrganizationInfoQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.COrganizationInfo}.
 */
@RestController
@RequestMapping("/api")
public class COrganizationInfoResource {

    private final Logger log = LoggerFactory.getLogger(COrganizationInfoResource.class);

    private static final String ENTITY_NAME = "cOrganizationInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final COrganizationInfoService cOrganizationInfoService;

    private final COrganizationInfoQueryService cOrganizationInfoQueryService;

    public COrganizationInfoResource(COrganizationInfoService cOrganizationInfoService, COrganizationInfoQueryService cOrganizationInfoQueryService) {
        this.cOrganizationInfoService = cOrganizationInfoService;
        this.cOrganizationInfoQueryService = cOrganizationInfoQueryService;
    }

    /**
     * {@code POST  /c-organization-infos} : Create a new cOrganizationInfo.
     *
     * @param cOrganizationInfoDTO the cOrganizationInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cOrganizationInfoDTO, or with status {@code 400 (Bad Request)} if the cOrganizationInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-organization-infos")
    public ResponseEntity<COrganizationInfoDTO> createCOrganizationInfo(@Valid @RequestBody COrganizationInfoDTO cOrganizationInfoDTO) throws URISyntaxException {
        log.debug("REST request to save COrganizationInfo : {}", cOrganizationInfoDTO);
        if (cOrganizationInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cOrganizationInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        COrganizationInfoDTO result = cOrganizationInfoService.save(cOrganizationInfoDTO);
        return ResponseEntity.created(new URI("/api/c-organization-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-organization-infos} : Updates an existing cOrganizationInfo.
     *
     * @param cOrganizationInfoDTO the cOrganizationInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cOrganizationInfoDTO,
     * or with status {@code 400 (Bad Request)} if the cOrganizationInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cOrganizationInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-organization-infos")
    public ResponseEntity<COrganizationInfoDTO> updateCOrganizationInfo(@Valid @RequestBody COrganizationInfoDTO cOrganizationInfoDTO) throws URISyntaxException {
        log.debug("REST request to update COrganizationInfo : {}", cOrganizationInfoDTO);
        if (cOrganizationInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        COrganizationInfoDTO result = cOrganizationInfoService.save(cOrganizationInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cOrganizationInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-organization-infos} : get all the cOrganizationInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cOrganizationInfos in body.
     */
    @GetMapping("/c-organization-infos")
    public ResponseEntity<List<COrganizationInfoDTO>> getAllCOrganizationInfos(COrganizationInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get COrganizationInfos by criteria: {}", criteria);
        Page<COrganizationInfoDTO> page = cOrganizationInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-organization-infos/count} : count all the cOrganizationInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-organization-infos/count")
    public ResponseEntity<Long> countCOrganizationInfos(COrganizationInfoCriteria criteria) {
        log.debug("REST request to count COrganizationInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(cOrganizationInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-organization-infos/:id} : get the "id" cOrganizationInfo.
     *
     * @param id the id of the cOrganizationInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cOrganizationInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-organization-infos/{id}")
    public ResponseEntity<COrganizationInfoDTO> getCOrganizationInfo(@PathVariable Long id) {
        log.debug("REST request to get COrganizationInfo : {}", id);
        Optional<COrganizationInfoDTO> cOrganizationInfoDTO = cOrganizationInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cOrganizationInfoDTO);
    }

    /**
     * {@code DELETE  /c-organization-infos/:id} : delete the "id" cOrganizationInfo.
     *
     * @param id the id of the cOrganizationInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-organization-infos/{id}")
    public ResponseEntity<Void> deleteCOrganizationInfo(@PathVariable Long id) {
        log.debug("REST request to delete COrganizationInfo : {}", id);
        cOrganizationInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
