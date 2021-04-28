package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MPrequalificationDateSetQueryService;
import com.bhp.opusb.service.MPrequalificationDateSetService;
import com.bhp.opusb.service.dto.MPrequalificationDateSetCriteria;
import com.bhp.opusb.service.dto.MPrequalificationDateSetDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationDateSet}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationDateSetResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationDateSetResource.class);

    private static final String ENTITY_NAME = "mPrequalificationDateSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationDateSetService mPrequalificationDateSetService;

    private final MPrequalificationDateSetQueryService mPrequalificationDateSetQueryService;

    public MPrequalificationDateSetResource(MPrequalificationDateSetService mPrequalificationDateSetService, MPrequalificationDateSetQueryService mPrequalificationDateSetQueryService) {
        this.mPrequalificationDateSetService = mPrequalificationDateSetService;
        this.mPrequalificationDateSetQueryService = mPrequalificationDateSetQueryService;
    }

    /**
     * {@code POST  /m-prequalification-date-sets} : Create a new mPrequalificationDateSet.
     *
     * @param mPrequalificationDateSetDTO the mPrequalificationDateSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationDateSetDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationDateSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-date-sets")
    public ResponseEntity<MPrequalificationDateSetDTO> createMPrequalificationDateSet(@Valid @RequestBody MPrequalificationDateSetDTO mPrequalificationDateSetDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationDateSet : {}", mPrequalificationDateSetDTO);
        if (mPrequalificationDateSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationDateSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationDateSetDTO result = mPrequalificationDateSetService.save(mPrequalificationDateSetDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-date-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequalification-date-sets} : Updates an existing mPrequalificationDateSet.
     *
     * @param mPrequalificationDateSetDTO the mPrequalificationDateSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationDateSetDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationDateSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationDateSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-date-sets")
    public ResponseEntity<MPrequalificationDateSetDTO> updateMPrequalificationDateSet(@Valid @RequestBody MPrequalificationDateSetDTO mPrequalificationDateSetDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationDateSet : {}", mPrequalificationDateSetDTO);
        if (mPrequalificationDateSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationDateSetDTO result = mPrequalificationDateSetService.save(mPrequalificationDateSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationDateSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-date-sets} : get all the mPrequalificationDateSets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationDateSets in body.
     */
    @GetMapping("/m-prequalification-date-sets")
    public ResponseEntity<List<MPrequalificationDateSetDTO>> getAllMPrequalificationDateSets(MPrequalificationDateSetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationDateSets by criteria: {}", criteria);
        Page<MPrequalificationDateSetDTO> page = mPrequalificationDateSetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-date-sets/count} : count all the mPrequalificationDateSets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-date-sets/count")
    public ResponseEntity<Long> countMPrequalificationDateSets(MPrequalificationDateSetCriteria criteria) {
        log.debug("REST request to count MPrequalificationDateSets by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationDateSetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-date-sets/:id} : get the "id" mPrequalificationDateSet.
     *
     * @param id the id of the mPrequalificationDateSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationDateSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-date-sets/{id}")
    public ResponseEntity<MPrequalificationDateSetDTO> getMPrequalificationDateSet(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationDateSet : {}", id);
        Optional<MPrequalificationDateSetDTO> mPrequalificationDateSetDTO = mPrequalificationDateSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationDateSetDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-date-sets/:id} : delete the "id" mPrequalificationDateSet.
     *
     * @param id the id of the mPrequalificationDateSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-date-sets/{id}")
    public ResponseEntity<Void> deleteMPrequalificationDateSet(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationDateSet : {}", id);
        mPrequalificationDateSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
