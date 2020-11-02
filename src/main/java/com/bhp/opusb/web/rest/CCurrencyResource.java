package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CCurrencyService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CCurrencyDTO;
import com.bhp.opusb.service.dto.CCurrencyCriteria;
import com.bhp.opusb.service.CCurrencyQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CCurrency}.
 */
@RestController
@RequestMapping("/api")
public class CCurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CCurrencyResource.class);

    private static final String ENTITY_NAME = "cCurrency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CCurrencyService cCurrencyService;

    private final CCurrencyQueryService cCurrencyQueryService;

    public CCurrencyResource(CCurrencyService cCurrencyService, CCurrencyQueryService cCurrencyQueryService) {
        this.cCurrencyService = cCurrencyService;
        this.cCurrencyQueryService = cCurrencyQueryService;
    }

    /**
     * {@code POST  /c-currencies} : Create a new cCurrency.
     *
     * @param cCurrencyDTO the cCurrencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cCurrencyDTO, or with status {@code 400 (Bad Request)} if the cCurrency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-currencies")
    public ResponseEntity<CCurrencyDTO> createCCurrency(@Valid @RequestBody CCurrencyDTO cCurrencyDTO) throws URISyntaxException {
        log.debug("REST request to save CCurrency : {}", cCurrencyDTO);
        if (cCurrencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new cCurrency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCurrencyDTO result = cCurrencyService.save(cCurrencyDTO);
        return ResponseEntity.created(new URI("/api/c-currencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-currencies} : Updates an existing cCurrency.
     *
     * @param cCurrencyDTO the cCurrencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCurrencyDTO,
     * or with status {@code 400 (Bad Request)} if the cCurrencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cCurrencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-currencies")
    public ResponseEntity<CCurrencyDTO> updateCCurrency(@Valid @RequestBody CCurrencyDTO cCurrencyDTO) throws URISyntaxException {
        log.debug("REST request to update CCurrency : {}", cCurrencyDTO);
        if (cCurrencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CCurrencyDTO result = cCurrencyService.save(cCurrencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCurrencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-currencies} : get all the cCurrencies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cCurrencies in body.
     */
    @GetMapping("/c-currencies")
    public ResponseEntity<List<CCurrencyDTO>> getAllCCurrencies(CCurrencyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CCurrencies by criteria: {}", criteria);
        Page<CCurrencyDTO> page = cCurrencyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-currencies/count} : count all the cCurrencies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-currencies/count")
    public ResponseEntity<Long> countCCurrencies(CCurrencyCriteria criteria) {
        log.debug("REST request to count CCurrencies by criteria: {}", criteria);
        return ResponseEntity.ok().body(cCurrencyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-currencies/:id} : get the "id" cCurrency.
     *
     * @param id the id of the cCurrencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cCurrencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-currencies/{id}")
    public ResponseEntity<CCurrencyDTO> getCCurrency(@PathVariable Long id) {
        log.debug("REST request to get CCurrency : {}", id);
        Optional<CCurrencyDTO> cCurrencyDTO = cCurrencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCurrencyDTO);
    }

    /**
     * {@code DELETE  /c-currencies/:id} : delete the "id" cCurrency.
     *
     * @param id the id of the cCurrencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-currencies/{id}")
    public ResponseEntity<Void> deleteCCurrency(@PathVariable Long id) {
        log.debug("REST request to delete CCurrency : {}", id);
        cCurrencyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
