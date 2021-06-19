package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractDTO;
import com.bhp.opusb.service.dto.MContractCriteria;
import com.bhp.opusb.service.MContractQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContract}.
 */
@RestController
@RequestMapping("/api")
public class MContractResource {

    private final Logger log = LoggerFactory.getLogger(MContractResource.class);

    private static final String ENTITY_NAME = "mContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractService mContractService;

    private final MContractQueryService mContractQueryService;

    public MContractResource(MContractService mContractService, MContractQueryService mContractQueryService) {
        this.mContractService = mContractService;
        this.mContractQueryService = mContractQueryService;
    }

    /**
     * {@code POST  /m-contracts} : Create a new mContract.
     *
     * @param mContractDTO the mContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractDTO, or with status {@code 400 (Bad Request)} if the mContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contracts")
    public ResponseEntity<MContractDTO> createMContract(@Valid @RequestBody MContractDTO mContractDTO) throws URISyntaxException {
        log.debug("REST request to save MContract : {}", mContractDTO);
        if (mContractDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractDTO result = mContractService.save(mContractDTO);
        return ResponseEntity.created(new URI("/api/m-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contracts} : Updates an existing mContract.
     *
     * @param mContractDTO the mContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractDTO,
     * or with status {@code 400 (Bad Request)} if the mContractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contracts")
    public ResponseEntity<MContractDTO> updateMContract(@Valid @RequestBody MContractDTO mContractDTO) throws URISyntaxException {
        log.debug("REST request to update MContract : {}", mContractDTO);
        if (mContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractDTO result = mContractService.save(mContractDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contracts} : get all the mContracts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContracts in body.
     */
    @GetMapping("/m-contracts")
    public ResponseEntity<List<MContractDTO>> getAllMContracts(MContractCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContracts by criteria: {}", criteria);
        Page<MContractDTO> page = mContractQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contracts/count} : count all the mContracts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contracts/count")
    public ResponseEntity<Long> countMContracts(MContractCriteria criteria) {
        log.debug("REST request to count MContracts by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contracts/:id} : get the "id" mContract.
     *
     * @param id the id of the mContractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contracts/{id}")
    public ResponseEntity<MContractDTO> getMContract(@PathVariable Long id) {
        log.debug("REST request to get MContract : {}", id);
        Optional<MContractDTO> mContractDTO = mContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractDTO);
    }

    /**
     * {@code DELETE  /m-contracts/:id} : delete the "id" mContract.
     *
     * @param id the id of the mContractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contracts/{id}")
    public ResponseEntity<Void> deleteMContract(@PathVariable Long id) {
        log.debug("REST request to delete MContract : {}", id);
        mContractService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
