package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorConfirmationContractService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorConfirmationContractDTO;
import com.bhp.opusb.service.dto.MVendorConfirmationContractCriteria;
import com.bhp.opusb.service.MVendorConfirmationContractQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorConfirmationContract}.
 */
@RestController
@RequestMapping("/api")
public class MVendorConfirmationContractResource {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationContractResource.class);

    private static final String ENTITY_NAME = "mVendorConfirmationContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorConfirmationContractService mVendorConfirmationContractService;

    private final MVendorConfirmationContractQueryService mVendorConfirmationContractQueryService;

    public MVendorConfirmationContractResource(MVendorConfirmationContractService mVendorConfirmationContractService, MVendorConfirmationContractQueryService mVendorConfirmationContractQueryService) {
        this.mVendorConfirmationContractService = mVendorConfirmationContractService;
        this.mVendorConfirmationContractQueryService = mVendorConfirmationContractQueryService;
    }

    /**
     * {@code POST  /m-vendor-confirmation-contracts} : Create a new mVendorConfirmationContract.
     *
     * @param mVendorConfirmationContractDTO the mVendorConfirmationContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorConfirmationContractDTO, or with status {@code 400 (Bad Request)} if the mVendorConfirmationContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-confirmation-contracts")
    public ResponseEntity<MVendorConfirmationContractDTO> createMVendorConfirmationContract(@Valid @RequestBody MVendorConfirmationContractDTO mVendorConfirmationContractDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorConfirmationContract : {}", mVendorConfirmationContractDTO);
        if (mVendorConfirmationContractDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorConfirmationContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorConfirmationContractDTO result = mVendorConfirmationContractService.save(mVendorConfirmationContractDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-confirmation-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-vendor-confirmation-contracts} : Create a new mVendorConfirmationContract.
     *
     * @param mVendorConfirmationContractDTO the mVendorConfirmationContractDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorConfirmationContractDTO, or with status {@code 400 (Bad Request)} if the mVendorConfirmationContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-confirmation-contracts/publish/{id}")
    public ResponseEntity<MVendorConfirmationContractDTO> publishContract(@PathVariable Long id, @RequestBody MVendorConfirmationContractDTO mVendorConfirmationContractDTO) throws URISyntaxException {
        log.debug("REST request to publish MVendorConfirmationContract : {}", mVendorConfirmationContractDTO);
        if (id == null) {
            throw new BadRequestAlertException("Cannot publish contract without its ID", ENTITY_NAME, "noid");
        }

        mVendorConfirmationContractService.publish(mVendorConfirmationContractDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code PUT  /m-vendor-confirmation-contracts} : Updates an existing mVendorConfirmationContract.
     *
     * @param mVendorConfirmationContractDTO the mVendorConfirmationContractDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorConfirmationContractDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorConfirmationContractDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorConfirmationContractDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-confirmation-contracts")
    public ResponseEntity<MVendorConfirmationContractDTO> updateMVendorConfirmationContract(@Valid @RequestBody MVendorConfirmationContractDTO mVendorConfirmationContractDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorConfirmationContract : {}", mVendorConfirmationContractDTO);
        if (mVendorConfirmationContractDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorConfirmationContractDTO result = mVendorConfirmationContractService.save(mVendorConfirmationContractDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorConfirmationContractDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-confirmation-contracts} : get all the mVendorConfirmationContracts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorConfirmationContracts in body.
     */
    @GetMapping("/m-vendor-confirmation-contracts")
    public ResponseEntity<List<MVendorConfirmationContractDTO>> getAllMVendorConfirmationContracts(MVendorConfirmationContractCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorConfirmationContracts by criteria: {}", criteria);
        Page<MVendorConfirmationContractDTO> page = mVendorConfirmationContractQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-confirmation-contracts/count} : count all the mVendorConfirmationContracts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-confirmation-contracts/count")
    public ResponseEntity<Long> countMVendorConfirmationContracts(MVendorConfirmationContractCriteria criteria) {
        log.debug("REST request to count MVendorConfirmationContracts by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorConfirmationContractQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-confirmation-contracts/:id} : get the "id" mVendorConfirmationContract.
     *
     * @param id the id of the mVendorConfirmationContractDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorConfirmationContractDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-confirmation-contracts/{id}")
    public ResponseEntity<MVendorConfirmationContractDTO> getMVendorConfirmationContract(@PathVariable Long id) {
        log.debug("REST request to get MVendorConfirmationContract : {}", id);
        Optional<MVendorConfirmationContractDTO> mVendorConfirmationContractDTO = mVendorConfirmationContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorConfirmationContractDTO);
    }

    /**
     * {@code DELETE  /m-vendor-confirmation-contracts/:id} : delete the "id" mVendorConfirmationContract.
     *
     * @param id the id of the mVendorConfirmationContractDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-confirmation-contracts/{id}")
    public ResponseEntity<Void> deleteMVendorConfirmationContract(@PathVariable Long id) {
        log.debug("REST request to delete MVendorConfirmationContract : {}", id);
        mVendorConfirmationContractService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
