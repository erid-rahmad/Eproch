package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.AiExchangeInQueryService;
import com.bhp.opusb.service.AiExchangeInService;
import com.bhp.opusb.service.dto.AiExchangeInCriteria;
import com.bhp.opusb.service.dto.AiExchangeInDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.AiExchangeIn}.
 */
@RestController
@RequestMapping("/api")
public class AiExchangeInResource {

    private final Logger log = LoggerFactory.getLogger(AiExchangeInResource.class);

    private static final String ENTITY_NAME = "aiExchangeIn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AiExchangeInService aiExchangeInService;

    private final AiExchangeInQueryService aiExchangeInQueryService;

    public AiExchangeInResource(AiExchangeInService aiExchangeInService, AiExchangeInQueryService aiExchangeInQueryService) {
        this.aiExchangeInService = aiExchangeInService;
        this.aiExchangeInQueryService = aiExchangeInQueryService;
    }

    /**
     * {@code POST  /ai-exchange-ins} : Create a new aiExchangeIn.
     *
     * @param aiExchangeInDTO the aiExchangeInDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aiExchangeInDTO, or with status {@code 400 (Bad Request)} if the aiExchangeIn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ai-exchange-ins")
    public ResponseEntity<AiExchangeInDTO> createAiExchangeIn(@Valid @RequestBody AiExchangeInDTO aiExchangeInDTO) throws URISyntaxException {
        log.debug("REST request to save AiExchangeIn : {}", aiExchangeInDTO);
        if (aiExchangeInDTO.getId() != null) {
            throw new BadRequestAlertException("A new aiExchangeIn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AiExchangeInDTO result = aiExchangeInService.save(aiExchangeInDTO);
        return ResponseEntity.created(new URI("/api/ai-exchange-ins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ai-exchange-ins} : Updates an existing aiExchangeIn.
     *
     * @param aiExchangeInDTO the aiExchangeInDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aiExchangeInDTO,
     * or with status {@code 400 (Bad Request)} if the aiExchangeInDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aiExchangeInDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ai-exchange-ins")
    public ResponseEntity<AiExchangeInDTO> updateAiExchangeIn(@Valid @RequestBody AiExchangeInDTO aiExchangeInDTO) throws URISyntaxException {
        log.debug("REST request to update AiExchangeIn : {}", aiExchangeInDTO);
        if (aiExchangeInDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AiExchangeInDTO result = aiExchangeInService.save(aiExchangeInDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aiExchangeInDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ai-exchange-ins} : get all the aiExchangeIns.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aiExchangeIns in body.
     */
    @GetMapping("/ai-exchange-ins")
    public ResponseEntity<List<AiExchangeInDTO>> getAllAiExchangeIns(AiExchangeInCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AiExchangeIns by criteria: {}", criteria);
        Page<AiExchangeInDTO> page = aiExchangeInQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ai-exchange-ins/count} : count all the aiExchangeIns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ai-exchange-ins/count")
    public ResponseEntity<Long> countAiExchangeIns(AiExchangeInCriteria criteria) {
        log.debug("REST request to count AiExchangeIns by criteria: {}", criteria);
        return ResponseEntity.ok().body(aiExchangeInQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ai-exchange-ins/:id} : get the "id" aiExchangeIn.
     *
     * @param id the id of the aiExchangeInDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aiExchangeInDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ai-exchange-ins/{id}")
    public ResponseEntity<AiExchangeInDTO> getAiExchangeIn(@PathVariable Long id) {
        log.debug("REST request to get AiExchangeIn : {}", id);
        Optional<AiExchangeInDTO> aiExchangeInDTO = aiExchangeInService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aiExchangeInDTO);
    }

    /**
     * {@code DELETE  /ai-exchange-ins/:id} : delete the "id" aiExchangeIn.
     *
     * @param id the id of the aiExchangeInDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ai-exchange-ins/{id}")
    public ResponseEntity<Void> deleteAiExchangeIn(@PathVariable Long id) {
        log.debug("REST request to delete AiExchangeIn : {}", id);
        aiExchangeInService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
