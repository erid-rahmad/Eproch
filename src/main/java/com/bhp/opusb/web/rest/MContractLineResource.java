package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractLineDTO;
import com.bhp.opusb.service.dto.MContractLineCriteria;
import com.bhp.opusb.service.MContractLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContractLine}.
 */
@RestController
@RequestMapping("/api")
public class MContractLineResource {

    private final Logger log = LoggerFactory.getLogger(MContractLineResource.class);

    private static final String ENTITY_NAME = "mContractLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractLineService mContractLineService;

    private final MContractLineQueryService mContractLineQueryService;

    public MContractLineResource(MContractLineService mContractLineService, MContractLineQueryService mContractLineQueryService) {
        this.mContractLineService = mContractLineService;
        this.mContractLineQueryService = mContractLineQueryService;
    }

    /**
     * {@code POST  /m-contract-lines} : Create a new mContractLine.
     *
     * @param mContractLineDTO the mContractLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractLineDTO, or with status {@code 400 (Bad Request)} if the mContractLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-lines")
    public ResponseEntity<MContractLineDTO> createMContractLine(@Valid @RequestBody MContractLineDTO mContractLineDTO) throws URISyntaxException {
        log.debug("REST request to save MContractLine : {}", mContractLineDTO);
        if (mContractLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContractLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractLineDTO result = mContractLineService.save(mContractLineDTO);
        return ResponseEntity.created(new URI("/api/m-contract-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-lines} : Updates an existing mContractLine.
     *
     * @param mContractLineDTO the mContractLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractLineDTO,
     * or with status {@code 400 (Bad Request)} if the mContractLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-lines")
    public ResponseEntity<MContractLineDTO> updateMContractLine(@Valid @RequestBody MContractLineDTO mContractLineDTO) throws URISyntaxException {
        log.debug("REST request to update MContractLine : {}", mContractLineDTO);
        if (mContractLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractLineDTO result = mContractLineService.save(mContractLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-lines} : get all the mContractLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractLines in body.
     */
    @GetMapping("/m-contract-lines")
    public ResponseEntity<List<MContractLineDTO>> getAllMContractLines(MContractLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractLines by criteria: {}", criteria);
        Page<MContractLineDTO> page = mContractLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-lines/count} : count all the mContractLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-lines/count")
    public ResponseEntity<Long> countMContractLines(MContractLineCriteria criteria) {
        log.debug("REST request to count MContractLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-lines/:id} : get the "id" mContractLine.
     *
     * @param id the id of the mContractLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-lines/{id}")
    public ResponseEntity<MContractLineDTO> getMContractLine(@PathVariable Long id) {
        log.debug("REST request to get MContractLine : {}", id);
        Optional<MContractLineDTO> mContractLineDTO = mContractLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractLineDTO);
    }

    /**
     * {@code DELETE  /m-contract-lines/:id} : delete the "id" mContractLine.
     *
     * @param id the id of the mContractLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-lines/{id}")
    public ResponseEntity<Void> deleteMContractLine(@PathVariable Long id) {
        log.debug("REST request to delete MContractLine : {}", id);
        mContractLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
