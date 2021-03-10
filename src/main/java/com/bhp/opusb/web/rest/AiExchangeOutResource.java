package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AiExchangeOutService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AiExchangeOutDTO;
import com.bhp.opusb.service.dto.AiExchangeOutCriteria;
import com.bhp.opusb.service.AiExchangeOutQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AiExchangeOut}.
 */
@RestController
@RequestMapping("/api")
public class AiExchangeOutResource {

    private final Logger log = LoggerFactory.getLogger(AiExchangeOutResource.class);

    private static final String ENTITY_NAME = "aiExchangeOut";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AiExchangeOutService aiExchangeOutService;

    private final AiExchangeOutQueryService aiExchangeOutQueryService;

    public AiExchangeOutResource(AiExchangeOutService aiExchangeOutService, AiExchangeOutQueryService aiExchangeOutQueryService) {
        this.aiExchangeOutService = aiExchangeOutService;
        this.aiExchangeOutQueryService = aiExchangeOutQueryService;
    }

    /**
     * {@code POST  /ai-exchange-outs} : Create a new aiExchangeOut.
     *
     * @param aiExchangeOutDTO the aiExchangeOutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aiExchangeOutDTO, or with status {@code 400 (Bad Request)} if the aiExchangeOut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ai-exchange-outs")
    public ResponseEntity<AiExchangeOutDTO> createAiExchangeOut(@Valid @RequestBody AiExchangeOutDTO aiExchangeOutDTO) throws URISyntaxException {
        log.debug("REST request to save AiExchangeOut : {}", aiExchangeOutDTO);
        if (aiExchangeOutDTO.getId() != null) {
            throw new BadRequestAlertException("A new aiExchangeOut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AiExchangeOutDTO result = aiExchangeOutService.save(aiExchangeOutDTO);
        return ResponseEntity.created(new URI("/api/ai-exchange-outs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ai-exchange-outs} : Updates an existing aiExchangeOut.
     *
     * @param aiExchangeOutDTO the aiExchangeOutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aiExchangeOutDTO,
     * or with status {@code 400 (Bad Request)} if the aiExchangeOutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aiExchangeOutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ai-exchange-outs")
    public ResponseEntity<AiExchangeOutDTO> updateAiExchangeOut(@Valid @RequestBody AiExchangeOutDTO aiExchangeOutDTO) throws URISyntaxException {
        log.debug("REST request to update AiExchangeOut : {}", aiExchangeOutDTO);
        if (aiExchangeOutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AiExchangeOutDTO result = aiExchangeOutService.save(aiExchangeOutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aiExchangeOutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ai-exchange-outs} : get all the aiExchangeOuts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aiExchangeOuts in body.
     */
    @GetMapping("/ai-exchange-outs")
    public ResponseEntity<List<AiExchangeOutDTO>> getAllAiExchangeOuts(AiExchangeOutCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AiExchangeOuts by criteria: {}", criteria);
        Page<AiExchangeOutDTO> page = aiExchangeOutQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ai-exchange-outs/count} : count all the aiExchangeOuts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ai-exchange-outs/count")
    public ResponseEntity<Long> countAiExchangeOuts(AiExchangeOutCriteria criteria) {
        log.debug("REST request to count AiExchangeOuts by criteria: {}", criteria);
        return ResponseEntity.ok().body(aiExchangeOutQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ai-exchange-outs/:id} : get the "id" aiExchangeOut.
     *
     * @param id the id of the aiExchangeOutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aiExchangeOutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ai-exchange-outs/{id}")
    public ResponseEntity<AiExchangeOutDTO> getAiExchangeOut(@PathVariable Long id) {
        log.debug("REST request to get AiExchangeOut : {}", id);
        Optional<AiExchangeOutDTO> aiExchangeOutDTO = aiExchangeOutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aiExchangeOutDTO);
    }

    /**
     * {@code DELETE  /ai-exchange-outs/:id} : delete the "id" aiExchangeOut.
     *
     * @param id the id of the aiExchangeOutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ai-exchange-outs/{id}")
    public ResponseEntity<Void> deleteAiExchangeOut(@PathVariable Long id) {
        log.debug("REST request to delete AiExchangeOut : {}", id);
        aiExchangeOutService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
