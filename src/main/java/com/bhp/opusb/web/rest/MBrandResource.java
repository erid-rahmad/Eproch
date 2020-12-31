package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MBrandQueryService;
import com.bhp.opusb.service.MBrandService;
import com.bhp.opusb.service.dto.MBrandCriteria;
import com.bhp.opusb.service.dto.MBrandDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MBrand}.
 */
@RestController
@RequestMapping("/api")
public class MBrandResource {

    private final Logger log = LoggerFactory.getLogger(MBrandResource.class);

    private static final String ENTITY_NAME = "mBrand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBrandService mBrandService;

    private final MBrandQueryService mBrandQueryService;

    public MBrandResource(MBrandService mBrandService, MBrandQueryService mBrandQueryService) {
        this.mBrandService = mBrandService;
        this.mBrandQueryService = mBrandQueryService;
    }

    /**
     * {@code POST  /m-brands} : Create a new mBrand.
     *
     * @param mBrandDTO the mBrandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBrandDTO, or with status {@code 400 (Bad Request)} if the mBrand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-brands")
    public ResponseEntity<MBrandDTO> createMBrand(@Valid @RequestBody MBrandDTO mBrandDTO) throws URISyntaxException {
        log.debug("REST request to save MBrand : {}", mBrandDTO);
        if (mBrandDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBrand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBrandDTO result = mBrandService.save(mBrandDTO);
        return ResponseEntity.created(new URI("/api/m-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-brands} : Updates an existing mBrand.
     *
     * @param mBrandDTO the mBrandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBrandDTO,
     * or with status {@code 400 (Bad Request)} if the mBrandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBrandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-brands")
    public ResponseEntity<MBrandDTO> updateMBrand(@Valid @RequestBody MBrandDTO mBrandDTO) throws URISyntaxException {
        log.debug("REST request to update MBrand : {}", mBrandDTO);
        if (mBrandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBrandDTO result = mBrandService.save(mBrandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBrandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-brands} : get all the mBrands.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBrands in body.
     */
    @GetMapping("/m-brands")
    public ResponseEntity<List<MBrandDTO>> getAllMBrands(MBrandCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBrands by criteria: {}", criteria);
        Page<MBrandDTO> page = mBrandQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-brands/count} : count all the mBrands.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-brands/count")
    public ResponseEntity<Long> countMBrands(MBrandCriteria criteria) {
        log.debug("REST request to count MBrands by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBrandQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-brands/:id} : get the "id" mBrand.
     *
     * @param id the id of the mBrandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBrandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-brands/{id}")
    public ResponseEntity<MBrandDTO> getMBrand(@PathVariable Long id) {
        log.debug("REST request to get MBrand : {}", id);
        Optional<MBrandDTO> mBrandDTO = mBrandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBrandDTO);
    }

    /**
     * {@code DELETE  /m-brands/:id} : delete the "id" mBrand.
     *
     * @param id the id of the mBrandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-brands/{id}")
    public ResponseEntity<Void> deleteMBrand(@PathVariable Long id) {
        log.debug("REST request to delete MBrand : {}", id);
        mBrandService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
