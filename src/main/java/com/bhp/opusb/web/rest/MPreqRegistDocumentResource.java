package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPreqRegistDocumentService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPreqRegistDocumentDTO;
import com.bhp.opusb.service.dto.MPreqRegistDocumentCriteria;
import com.bhp.opusb.service.MPreqRegistDocumentQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPreqRegistDocument}.
 */
@RestController
@RequestMapping("/api")
public class MPreqRegistDocumentResource {

    private final Logger log = LoggerFactory.getLogger(MPreqRegistDocumentResource.class);

    private static final String ENTITY_NAME = "mPreqRegistDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPreqRegistDocumentService mPreqRegistDocumentService;

    private final MPreqRegistDocumentQueryService mPreqRegistDocumentQueryService;

    public MPreqRegistDocumentResource(MPreqRegistDocumentService mPreqRegistDocumentService, MPreqRegistDocumentQueryService mPreqRegistDocumentQueryService) {
        this.mPreqRegistDocumentService = mPreqRegistDocumentService;
        this.mPreqRegistDocumentQueryService = mPreqRegistDocumentQueryService;
    }

    /**
     * {@code POST  /m-preq-regist-documents} : Create a new mPreqRegistDocument.
     *
     * @param mPreqRegistDocumentDTO the mPreqRegistDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPreqRegistDocumentDTO, or with status {@code 400 (Bad Request)} if the mPreqRegistDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-preq-regist-documents")
    public ResponseEntity<MPreqRegistDocumentDTO> createMPreqRegistDocument(@Valid @RequestBody MPreqRegistDocumentDTO mPreqRegistDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save MPreqRegistDocument : {}", mPreqRegistDocumentDTO);
        if (mPreqRegistDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPreqRegistDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPreqRegistDocumentDTO result = mPreqRegistDocumentService.save(mPreqRegistDocumentDTO);
        return ResponseEntity.created(new URI("/api/m-preq-regist-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-preq-regist-documents} : Updates an existing mPreqRegistDocument.
     *
     * @param mPreqRegistDocumentDTO the mPreqRegistDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPreqRegistDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the mPreqRegistDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPreqRegistDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-preq-regist-documents")
    public ResponseEntity<MPreqRegistDocumentDTO> updateMPreqRegistDocument(@Valid @RequestBody MPreqRegistDocumentDTO mPreqRegistDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update MPreqRegistDocument : {}", mPreqRegistDocumentDTO);
        if (mPreqRegistDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPreqRegistDocumentDTO result = mPreqRegistDocumentService.save(mPreqRegistDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPreqRegistDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-preq-regist-documents} : get all the mPreqRegistDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPreqRegistDocuments in body.
     */
    @GetMapping("/m-preq-regist-documents")
    public ResponseEntity<List<MPreqRegistDocumentDTO>> getAllMPreqRegistDocuments(MPreqRegistDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPreqRegistDocuments by criteria: {}", criteria);
        Page<MPreqRegistDocumentDTO> page = mPreqRegistDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-preq-regist-documents/count} : count all the mPreqRegistDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-preq-regist-documents/count")
    public ResponseEntity<Long> countMPreqRegistDocuments(MPreqRegistDocumentCriteria criteria) {
        log.debug("REST request to count MPreqRegistDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPreqRegistDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-preq-regist-documents/:id} : get the "id" mPreqRegistDocument.
     *
     * @param id the id of the mPreqRegistDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPreqRegistDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-preq-regist-documents/{id}")
    public ResponseEntity<MPreqRegistDocumentDTO> getMPreqRegistDocument(@PathVariable Long id) {
        log.debug("REST request to get MPreqRegistDocument : {}", id);
        Optional<MPreqRegistDocumentDTO> mPreqRegistDocumentDTO = mPreqRegistDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPreqRegistDocumentDTO);
    }

    /**
     * {@code DELETE  /m-preq-regist-documents/:id} : delete the "id" mPreqRegistDocument.
     *
     * @param id the id of the mPreqRegistDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-preq-regist-documents/{id}")
    public ResponseEntity<Void> deleteMPreqRegistDocument(@PathVariable Long id) {
        log.debug("REST request to delete MPreqRegistDocument : {}", id);
        mPreqRegistDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
