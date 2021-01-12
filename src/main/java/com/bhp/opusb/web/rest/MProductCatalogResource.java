package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MProductCatalogQueryService;
import com.bhp.opusb.service.MProductCatalogService;
import com.bhp.opusb.service.dto.MProductCatalogCriteria;
import com.bhp.opusb.service.dto.MProductCatalogDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.MProductCatalog}.
 */
@RestController
@RequestMapping("/api")
public class MProductCatalogResource {

    private final Logger log = LoggerFactory.getLogger(MProductCatalogResource.class);

    private static final String ENTITY_NAME = "mProductCatalog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProductCatalogService mProductCatalogService;

    private final MProductCatalogQueryService mProductCatalogQueryService;

    public MProductCatalogResource(MProductCatalogService mProductCatalogService, MProductCatalogQueryService mProductCatalogQueryService) {
        this.mProductCatalogService = mProductCatalogService;
        this.mProductCatalogQueryService = mProductCatalogQueryService;
    }

    /**
     * {@code POST  /m-product-catalogs} : Create a new mProductCatalog.
     *
     * @param mProductCatalogDTO the mProductCatalogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProductCatalogDTO, or with status {@code 400 (Bad Request)} if the mProductCatalog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-product-catalogs")
    public ResponseEntity<MProductCatalogDTO> createMProductCatalog(@Valid @RequestBody MProductCatalogDTO mProductCatalogDTO) throws URISyntaxException {
        log.debug("REST request to save MProductCatalog : {}", mProductCatalogDTO);
        if (mProductCatalogDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProductCatalog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProductCatalogDTO result = mProductCatalogService.save(mProductCatalogDTO);
        return ResponseEntity.created(new URI("/api/m-product-catalogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-product-catalogs} : Updates an existing mProductCatalog.
     *
     * @param mProductCatalogDTO the mProductCatalogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProductCatalogDTO,
     * or with status {@code 400 (Bad Request)} if the mProductCatalogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProductCatalogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-product-catalogs")
    public ResponseEntity<MProductCatalogDTO> updateMProductCatalog(@Valid @RequestBody MProductCatalogDTO mProductCatalogDTO) throws URISyntaxException {
        log.debug("REST request to update MProductCatalog : {}", mProductCatalogDTO);
        if (mProductCatalogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProductCatalogDTO result = mProductCatalogService.save(mProductCatalogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProductCatalogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-product-catalogs} : get all the mProductCatalogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProductCatalogs in body.
     */
    @GetMapping("/m-product-catalogs")
    public ResponseEntity<List<MProductCatalogDTO>> getAllMProductCatalogs(MProductCatalogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProductCatalogs by criteria: {}", criteria);
        Page<MProductCatalogDTO> page = mProductCatalogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-product-catalogs} : get all the mProductCatalogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProductCatalogs in body.
     */
    @GetMapping("/m-product-catalogs/marketplace")
    public ResponseEntity<List<MProductCatalogDTO>> getMarketplaceCatalogs(MProductCatalogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProductCatalogs by criteria: {}", criteria);
        Page<MProductCatalogDTO> page = mProductCatalogQueryService.findInMarketplace(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/m-product-catalogs/marketplace/import")
    public ResponseEntity<Void> uploadFile(@RequestParam MultipartFile file) {
        mProductCatalogService.importBhinnekaCatalog(file);
        return ResponseEntity.status(200).build();
    }

    /**
     * {@code GET  /m-product-catalogs/count} : count all the mProductCatalogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-product-catalogs/count")
    public ResponseEntity<Long> countMProductCatalogs(MProductCatalogCriteria criteria) {
        log.debug("REST request to count MProductCatalogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProductCatalogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-product-catalogs/:id} : get the "id" mProductCatalog.
     *
     * @param id the id of the mProductCatalogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProductCatalogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-product-catalogs/{id}")
    public ResponseEntity<MProductCatalogDTO> getMProductCatalog(@PathVariable Long id) {
        log.debug("REST request to get MProductCatalog : {}", id);
        Optional<MProductCatalogDTO> mProductCatalogDTO = mProductCatalogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProductCatalogDTO);
    }

    /**
     * {@code DELETE  /m-product-catalogs/:id} : delete the "id" mProductCatalog.
     *
     * @param id the id of the mProductCatalogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-product-catalogs/{id}")
    public ResponseEntity<Void> deleteMProductCatalog(@PathVariable Long id) {
        log.debug("REST request to delete MProductCatalog : {}", id);
        mProductCatalogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
