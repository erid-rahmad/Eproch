package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractClauseDocumentService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractClauseDocumentDTO;
import com.bhp.opusb.service.dto.MContractClauseDocumentCriteria;
import com.bhp.opusb.service.MContractClauseDocumentQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContractClauseDocument}.
 */
@RestController
@RequestMapping("/api")
public class MContractClauseDocumentResource {

    private final Logger log = LoggerFactory.getLogger(MContractClauseDocumentResource.class);

    private static final String ENTITY_NAME = "mContractClauseDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractClauseDocumentService mContractClauseDocumentService;

    private final MContractClauseDocumentQueryService mContractClauseDocumentQueryService;

    public MContractClauseDocumentResource(MContractClauseDocumentService mContractClauseDocumentService, MContractClauseDocumentQueryService mContractClauseDocumentQueryService) {
        this.mContractClauseDocumentService = mContractClauseDocumentService;
        this.mContractClauseDocumentQueryService = mContractClauseDocumentQueryService;
    }

    /**
     * {@code POST  /m-contract-clause-documents} : Create a new mContractClauseDocument.
     *
     * @param mContractClauseDocumentDTO the mContractClauseDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractClauseDocumentDTO, or with status {@code 400 (Bad Request)} if the mContractClauseDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-clause-documents")
    public ResponseEntity<MContractClauseDocumentDTO> createMContractClauseDocument(@Valid @RequestBody MContractClauseDocumentDTO mContractClauseDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save MContractClauseDocument : {}", mContractClauseDocumentDTO);
        if (mContractClauseDocumentDTO.getId() != null) {
            return updateMContractClauseDocument(mContractClauseDocumentDTO);
        }
        MContractClauseDocumentDTO result = mContractClauseDocumentService.save(mContractClauseDocumentDTO);
        return ResponseEntity.created(new URI("/api/m-contract-clause-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-clause-documents} : Updates an existing mContractClauseDocument.
     *
     * @param mContractClauseDocumentDTO the mContractClauseDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractClauseDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the mContractClauseDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractClauseDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-clause-documents")
    public ResponseEntity<MContractClauseDocumentDTO> updateMContractClauseDocument(@Valid @RequestBody MContractClauseDocumentDTO mContractClauseDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update MContractClauseDocument : {}", mContractClauseDocumentDTO);
        if (mContractClauseDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractClauseDocumentDTO result = mContractClauseDocumentService.save(mContractClauseDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractClauseDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-clause-documents} : get all the mContractClauseDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractClauseDocuments in body.
     */
    @GetMapping("/m-contract-clause-documents")
    public ResponseEntity<List<MContractClauseDocumentDTO>> getAllMContractClauseDocuments(MContractClauseDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractClauseDocuments by criteria: {}", criteria);
        Page<MContractClauseDocumentDTO> page = mContractClauseDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-clause-documents/count} : count all the mContractClauseDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-clause-documents/count")
    public ResponseEntity<Long> countMContractClauseDocuments(MContractClauseDocumentCriteria criteria) {
        log.debug("REST request to count MContractClauseDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractClauseDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-clause-documents/:id} : get the "id" mContractClauseDocument.
     *
     * @param id the id of the mContractClauseDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractClauseDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-clause-documents/{id}")
    public ResponseEntity<MContractClauseDocumentDTO> getMContractClauseDocument(@PathVariable Long id) {
        log.debug("REST request to get MContractClauseDocument : {}", id);
        Optional<MContractClauseDocumentDTO> mContractClauseDocumentDTO = mContractClauseDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractClauseDocumentDTO);
    }

    /**
     * {@code DELETE  /m-contract-clause-documents/:id} : delete the "id" mContractClauseDocument.
     *
     * @param id the id of the mContractClauseDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-clause-documents/{id}")
    public ResponseEntity<Void> deleteMContractClauseDocument(@PathVariable Long id) {
        log.debug("REST request to delete MContractClauseDocument : {}", id);
        mContractClauseDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
