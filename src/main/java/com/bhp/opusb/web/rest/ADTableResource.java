package com.bhp.opusb.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.ADTableQueryService;
import com.bhp.opusb.service.ADTableService;
import com.bhp.opusb.service.ImportExportService;
import com.bhp.opusb.service.dto.ADTableCriteria;
import com.bhp.opusb.service.dto.ADTableDTO;
import com.bhp.opusb.service.dto.ImportParameterDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.ADTable}.
 */
@RestController
@RequestMapping("/api")
public class ADTableResource {

    private final Logger log = LoggerFactory.getLogger(ADTableResource.class);

    private static final String ENTITY_NAME = "aDTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADTableService aDTableService;
    private final ImportExportService importExportService;

    private final ADTableQueryService aDTableQueryService;

    public ADTableResource(ADTableService aDTableService, ImportExportService importExportService, ADTableQueryService aDTableQueryService) {
        this.aDTableService = aDTableService;
        this.importExportService = importExportService;
        this.aDTableQueryService = aDTableQueryService;
    }

    /**
     * {@code POST  /ad-tables} : Create a new aDTable.
     *
     * @param aDTableDTO the aDTableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDTableDTO, or with status {@code 400 (Bad Request)} if the aDTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-tables")
    public ResponseEntity<ADTableDTO> createADTable(@Valid @RequestBody ADTableDTO aDTableDTO) throws URISyntaxException {
        log.debug("REST request to save ADTable : {}", aDTableDTO);
        if (aDTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADTableDTO result = aDTableService.save(aDTableDTO);
        return ResponseEntity.created(new URI("/api/ad-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/ad-tables/import")
    public ResponseEntity<Void> uploadFile(@RequestParam MultipartFile file, @RequestParam ImportParameterDTO config) throws IOException {
        log.debug("REST request to import ADTable. file: {}, config: {}", file, config);
        importExportService.importCsv(file.getInputStream(), config);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code PUT  /ad-tables} : Updates an existing aDTable.
     *
     * @param aDTableDTO the aDTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDTableDTO,
     * or with status {@code 400 (Bad Request)} if the aDTableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-tables")
    public ResponseEntity<ADTableDTO> updateADTable(@Valid @RequestBody ADTableDTO aDTableDTO) throws URISyntaxException {
        log.debug("REST request to update ADTable : {}", aDTableDTO);
        if (aDTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADTableDTO result = aDTableService.save(aDTableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-tables} : get all the aDTables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDTables in body.
     */
    @GetMapping("/ad-tables")
    public ResponseEntity<List<ADTableDTO>> getAllADTables(ADTableCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADTables by criteria: {}", criteria);
        Page<ADTableDTO> page = aDTableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-tables/count} : count all the aDTables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-tables/count")
    public ResponseEntity<Long> countADTables(ADTableCriteria criteria) {
        log.debug("REST request to count ADTables by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDTableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-tables/:id} : get the "id" aDTable.
     *
     * @param id the id of the aDTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-tables/{id}")
    public ResponseEntity<ADTableDTO> getADTable(@PathVariable Long id) {
        log.debug("REST request to get ADTable : {}", id);
        Optional<ADTableDTO> aDTableDTO = aDTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDTableDTO);
    }

    /**
     * {@code DELETE  /ad-tables/:id} : delete the "id" aDTable.
     *
     * @param id the id of the aDTableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-tables/{id}")
    public ResponseEntity<Void> deleteADTable(@PathVariable Long id) {
        log.debug("REST request to delete ADTable : {}", id);
        aDTableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
