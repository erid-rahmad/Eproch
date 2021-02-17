package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CBiddingTypeService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CBiddingTypeDTO;
import com.bhp.opusb.service.dto.CBiddingTypeCriteria;
import com.bhp.opusb.service.CBiddingTypeQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CBiddingType}.
 */
@RestController
@RequestMapping("/api")
public class CBiddingTypeResource {

    private final Logger log = LoggerFactory.getLogger(CBiddingTypeResource.class);

    private static final String ENTITY_NAME = "cBiddingType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CBiddingTypeService cBiddingTypeService;

    private final CBiddingTypeQueryService cBiddingTypeQueryService;

    public CBiddingTypeResource(CBiddingTypeService cBiddingTypeService, CBiddingTypeQueryService cBiddingTypeQueryService) {
        this.cBiddingTypeService = cBiddingTypeService;
        this.cBiddingTypeQueryService = cBiddingTypeQueryService;
    }

    /**
     * {@code POST  /c-bidding-types} : Create a new cBiddingType.
     *
     * @param cBiddingTypeDTO the cBiddingTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cBiddingTypeDTO, or with status {@code 400 (Bad Request)} if the cBiddingType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-bidding-types")
    public ResponseEntity<CBiddingTypeDTO> createCBiddingType(@Valid @RequestBody CBiddingTypeDTO cBiddingTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CBiddingType : {}", cBiddingTypeDTO);
        if (cBiddingTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cBiddingType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CBiddingTypeDTO result = cBiddingTypeService.save(cBiddingTypeDTO);
        return ResponseEntity.created(new URI("/api/c-bidding-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-bidding-types} : Updates an existing cBiddingType.
     *
     * @param cBiddingTypeDTO the cBiddingTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cBiddingTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cBiddingTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cBiddingTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-bidding-types")
    public ResponseEntity<CBiddingTypeDTO> updateCBiddingType(@Valid @RequestBody CBiddingTypeDTO cBiddingTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CBiddingType : {}", cBiddingTypeDTO);
        if (cBiddingTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CBiddingTypeDTO result = cBiddingTypeService.save(cBiddingTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cBiddingTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-bidding-types} : get all the cBiddingTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cBiddingTypes in body.
     */
    @GetMapping("/c-bidding-types")
    public ResponseEntity<List<CBiddingTypeDTO>> getAllCBiddingTypes(CBiddingTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CBiddingTypes by criteria: {}", criteria);
        Page<CBiddingTypeDTO> page = cBiddingTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-bidding-types/count} : count all the cBiddingTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-bidding-types/count")
    public ResponseEntity<Long> countCBiddingTypes(CBiddingTypeCriteria criteria) {
        log.debug("REST request to count CBiddingTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cBiddingTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-bidding-types/:id} : get the "id" cBiddingType.
     *
     * @param id the id of the cBiddingTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cBiddingTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-bidding-types/{id}")
    public ResponseEntity<CBiddingTypeDTO> getCBiddingType(@PathVariable Long id) {
        log.debug("REST request to get CBiddingType : {}", id);
        Optional<CBiddingTypeDTO> cBiddingTypeDTO = cBiddingTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cBiddingTypeDTO);
    }

    /**
     * {@code DELETE  /c-bidding-types/:id} : delete the "id" cBiddingType.
     *
     * @param id the id of the cBiddingTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-bidding-types/{id}")
    public ResponseEntity<Void> deleteCBiddingType(@PathVariable Long id) {
        log.debug("REST request to delete CBiddingType : {}", id);
        cBiddingTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
