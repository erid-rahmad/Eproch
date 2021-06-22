package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractDocumentService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractDocumentDTO;
import com.bhp.opusb.service.dto.MContractDocumentCriteria;
import com.bhp.opusb.service.MContractDocumentQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContractDocument}.
 */
@RestController
@RequestMapping("/api")
public class MContractDocumentResource {

    private final Logger log = LoggerFactory.getLogger(MContractDocumentResource.class);

    private static final String ENTITY_NAME = "mContractDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractDocumentService mContractDocumentService;

    private final MContractDocumentQueryService mContractDocumentQueryService;

    public MContractDocumentResource(MContractDocumentService mContractDocumentService, MContractDocumentQueryService mContractDocumentQueryService) {
        this.mContractDocumentService = mContractDocumentService;
        this.mContractDocumentQueryService = mContractDocumentQueryService;
    }

    /**
     * {@code POST  /m-contract-documents} : Create a new mContractDocument.
     *
     * @param mContractDocumentDTO the mContractDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractDocumentDTO, or with status {@code 400 (Bad Request)} if the mContractDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-documents")
    public ResponseEntity<MContractDocumentDTO> createMContractDocument(@Valid @RequestBody MContractDocumentDTO mContractDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save MContractDocument : {}", mContractDocumentDTO);
        if (mContractDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContractDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractDocumentDTO result = mContractDocumentService.save(mContractDocumentDTO);
        return ResponseEntity.created(new URI("/api/m-contract-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-documents} : Updates an existing mContractDocument.
     *
     * @param mContractDocumentDTO the mContractDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the mContractDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-documents")
    public ResponseEntity<MContractDocumentDTO> updateMContractDocument(@Valid @RequestBody MContractDocumentDTO mContractDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update MContractDocument : {}", mContractDocumentDTO);
        if (mContractDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractDocumentDTO result = mContractDocumentService.save(mContractDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-documents} : get all the mContractDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractDocuments in body.
     */
    @GetMapping("/m-contract-documents")
    public ResponseEntity<List<MContractDocumentDTO>> getAllMContractDocuments(MContractDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractDocuments by criteria: {}", criteria);
        Page<MContractDocumentDTO> page = mContractDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-documents/count} : count all the mContractDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-documents/count")
    public ResponseEntity<Long> countMContractDocuments(MContractDocumentCriteria criteria) {
        log.debug("REST request to count MContractDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-documents/:id} : get the "id" mContractDocument.
     *
     * @param id the id of the mContractDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-documents/{id}")
    public ResponseEntity<MContractDocumentDTO> getMContractDocument(@PathVariable Long id) {
        log.debug("REST request to get MContractDocument : {}", id);
        Optional<MContractDocumentDTO> mContractDocumentDTO = mContractDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractDocumentDTO);
    }

    /**
     * {@code DELETE  /m-contract-documents/:id} : delete the "id" mContractDocument.
     *
     * @param id the id of the mContractDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-documents/{id}")
    public ResponseEntity<Void> deleteMContractDocument(@PathVariable Long id) {
        log.debug("REST request to delete MContractDocument : {}", id);
        mContractDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
