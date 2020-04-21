package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.ADClientService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.ADClientDTO;
import com.bhp.opusb.service.dto.ADClientCriteria;
import com.bhp.opusb.service.ADClientQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.ADClient}.
 */
@RestController
@RequestMapping("/api")
public class ADClientResource {

    private final Logger log = LoggerFactory.getLogger(ADClientResource.class);

    private static final String ENTITY_NAME = "aDClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADClientService aDClientService;

    private final ADClientQueryService aDClientQueryService;

    public ADClientResource(ADClientService aDClientService, ADClientQueryService aDClientQueryService) {
        this.aDClientService = aDClientService;
        this.aDClientQueryService = aDClientQueryService;
    }

    /**
     * {@code POST  /ad-clients} : Create a new aDClient.
     *
     * @param aDClientDTO the aDClientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDClientDTO, or with status {@code 400 (Bad Request)} if the aDClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-clients")
    public ResponseEntity<ADClientDTO> createADClient(@Valid @RequestBody ADClientDTO aDClientDTO) throws URISyntaxException {
        log.debug("REST request to save ADClient : {}", aDClientDTO);
        if (aDClientDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADClientDTO result = aDClientService.save(aDClientDTO);
        return ResponseEntity.created(new URI("/api/ad-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-clients} : Updates an existing aDClient.
     *
     * @param aDClientDTO the aDClientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDClientDTO,
     * or with status {@code 400 (Bad Request)} if the aDClientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDClientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-clients")
    public ResponseEntity<ADClientDTO> updateADClient(@Valid @RequestBody ADClientDTO aDClientDTO) throws URISyntaxException {
        log.debug("REST request to update ADClient : {}", aDClientDTO);
        if (aDClientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADClientDTO result = aDClientService.save(aDClientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDClientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-clients} : get all the aDClients.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDClients in body.
     */
    @GetMapping("/ad-clients")
    public ResponseEntity<List<ADClientDTO>> getAllADClients(ADClientCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADClients by criteria: {}", criteria);
        Page<ADClientDTO> page = aDClientQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-clients/count} : count all the aDClients.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-clients/count")
    public ResponseEntity<Long> countADClients(ADClientCriteria criteria) {
        log.debug("REST request to count ADClients by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDClientQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-clients/:id} : get the "id" aDClient.
     *
     * @param id the id of the aDClientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDClientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-clients/{id}")
    public ResponseEntity<ADClientDTO> getADClient(@PathVariable Long id) {
        log.debug("REST request to get ADClient : {}", id);
        Optional<ADClientDTO> aDClientDTO = aDClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDClientDTO);
    }

    /**
     * {@code DELETE  /ad-clients/:id} : delete the "id" aDClient.
     *
     * @param id the id of the aDClientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-clients/{id}")
    public ResponseEntity<Void> deleteADClient(@PathVariable Long id) {
        log.debug("REST request to delete ADClient : {}", id);
        aDClientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
