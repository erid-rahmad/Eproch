package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.ADOrganizationInfoService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.ADOrganizationInfoDTO;
import com.bhp.opusb.service.dto.ADOrganizationInfoCriteria;
import com.bhp.opusb.service.ADOrganizationInfoQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.ADOrganizationInfo}.
 */
@RestController
@RequestMapping("/api")
public class ADOrganizationInfoResource {

    private final Logger log = LoggerFactory.getLogger(ADOrganizationInfoResource.class);

    private static final String ENTITY_NAME = "aDOrganizationInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADOrganizationInfoService aDOrganizationInfoService;

    private final ADOrganizationInfoQueryService aDOrganizationInfoQueryService;

    public ADOrganizationInfoResource(ADOrganizationInfoService aDOrganizationInfoService, ADOrganizationInfoQueryService aDOrganizationInfoQueryService) {
        this.aDOrganizationInfoService = aDOrganizationInfoService;
        this.aDOrganizationInfoQueryService = aDOrganizationInfoQueryService;
    }

    /**
     * {@code POST  /ad-organization-infos} : Create a new aDOrganizationInfo.
     *
     * @param aDOrganizationInfoDTO the aDOrganizationInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDOrganizationInfoDTO, or with status {@code 400 (Bad Request)} if the aDOrganizationInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-organization-infos")
    public ResponseEntity<ADOrganizationInfoDTO> createADOrganizationInfo(@Valid @RequestBody ADOrganizationInfoDTO aDOrganizationInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ADOrganizationInfo : {}", aDOrganizationInfoDTO);
        if (aDOrganizationInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDOrganizationInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADOrganizationInfoDTO result = aDOrganizationInfoService.save(aDOrganizationInfoDTO);
        return ResponseEntity.created(new URI("/api/ad-organization-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-organization-infos} : Updates an existing aDOrganizationInfo.
     *
     * @param aDOrganizationInfoDTO the aDOrganizationInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDOrganizationInfoDTO,
     * or with status {@code 400 (Bad Request)} if the aDOrganizationInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDOrganizationInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-organization-infos")
    public ResponseEntity<ADOrganizationInfoDTO> updateADOrganizationInfo(@Valid @RequestBody ADOrganizationInfoDTO aDOrganizationInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ADOrganizationInfo : {}", aDOrganizationInfoDTO);
        if (aDOrganizationInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADOrganizationInfoDTO result = aDOrganizationInfoService.save(aDOrganizationInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDOrganizationInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-organization-infos} : get all the aDOrganizationInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDOrganizationInfos in body.
     */
    @GetMapping("/ad-organization-infos")
    public ResponseEntity<List<ADOrganizationInfoDTO>> getAllADOrganizationInfos(ADOrganizationInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADOrganizationInfos by criteria: {}", criteria);
        Page<ADOrganizationInfoDTO> page = aDOrganizationInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-organization-infos/count} : count all the aDOrganizationInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-organization-infos/count")
    public ResponseEntity<Long> countADOrganizationInfos(ADOrganizationInfoCriteria criteria) {
        log.debug("REST request to count ADOrganizationInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDOrganizationInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-organization-infos/:id} : get the "id" aDOrganizationInfo.
     *
     * @param id the id of the aDOrganizationInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDOrganizationInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-organization-infos/{id}")
    public ResponseEntity<ADOrganizationInfoDTO> getADOrganizationInfo(@PathVariable Long id) {
        log.debug("REST request to get ADOrganizationInfo : {}", id);
        Optional<ADOrganizationInfoDTO> aDOrganizationInfoDTO = aDOrganizationInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDOrganizationInfoDTO);
    }

    /**
     * {@code DELETE  /ad-organization-infos/:id} : delete the "id" aDOrganizationInfo.
     *
     * @param id the id of the aDOrganizationInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-organization-infos/{id}")
    public ResponseEntity<Void> deleteADOrganizationInfo(@PathVariable Long id) {
        log.debug("REST request to delete ADOrganizationInfo : {}", id);
        aDOrganizationInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
