package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CTaxInvoiceService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CTaxInvoiceDTO;
import com.bhp.opusb.service.dto.CTaxInvoiceCriteria;
import com.bhp.opusb.service.CTaxInvoiceQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CTaxInvoice}.
 */
@RestController
@RequestMapping("/api")
public class CTaxInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(CTaxInvoiceResource.class);

    private static final String ENTITY_NAME = "cTaxInvoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CTaxInvoiceService cTaxInvoiceService;

    private final CTaxInvoiceQueryService cTaxInvoiceQueryService;

    public CTaxInvoiceResource(CTaxInvoiceService cTaxInvoiceService, CTaxInvoiceQueryService cTaxInvoiceQueryService) {
        this.cTaxInvoiceService = cTaxInvoiceService;
        this.cTaxInvoiceQueryService = cTaxInvoiceQueryService;
    }

    /**
     * {@code POST  /c-tax-invoices} : Create a new cTaxInvoice.
     *
     * @param cTaxInvoiceDTO the cTaxInvoiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cTaxInvoiceDTO, or with status {@code 400 (Bad Request)} if the cTaxInvoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-tax-invoices")
    public ResponseEntity<CTaxInvoiceDTO> createCTaxInvoice(@Valid @RequestBody CTaxInvoiceDTO cTaxInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to save CTaxInvoice : {}", cTaxInvoiceDTO);
        if (cTaxInvoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new cTaxInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CTaxInvoiceDTO result = cTaxInvoiceService.save(cTaxInvoiceDTO);
        return ResponseEntity.created(new URI("/api/c-tax-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-tax-invoices} : Updates an existing cTaxInvoice.
     *
     * @param cTaxInvoiceDTO the cTaxInvoiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cTaxInvoiceDTO,
     * or with status {@code 400 (Bad Request)} if the cTaxInvoiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cTaxInvoiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-tax-invoices")
    public ResponseEntity<CTaxInvoiceDTO> updateCTaxInvoice(@Valid @RequestBody CTaxInvoiceDTO cTaxInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to update CTaxInvoice : {}", cTaxInvoiceDTO);
        if (cTaxInvoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CTaxInvoiceDTO result = cTaxInvoiceService.save(cTaxInvoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cTaxInvoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-tax-invoices} : get all the cTaxInvoices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cTaxInvoices in body.
     */
    @GetMapping("/c-tax-invoices")
    public ResponseEntity<List<CTaxInvoiceDTO>> getAllCTaxInvoices(CTaxInvoiceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CTaxInvoices by criteria: {}", criteria);
        Page<CTaxInvoiceDTO> page = cTaxInvoiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-tax-invoices/count} : count all the cTaxInvoices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-tax-invoices/count")
    public ResponseEntity<Long> countCTaxInvoices(CTaxInvoiceCriteria criteria) {
        log.debug("REST request to count CTaxInvoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(cTaxInvoiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-tax-invoices/:id} : get the "id" cTaxInvoice.
     *
     * @param id the id of the cTaxInvoiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cTaxInvoiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-tax-invoices/{id}")
    public ResponseEntity<CTaxInvoiceDTO> getCTaxInvoice(@PathVariable Long id) {
        log.debug("REST request to get CTaxInvoice : {}", id);
        Optional<CTaxInvoiceDTO> cTaxInvoiceDTO = cTaxInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cTaxInvoiceDTO);
    }

    /**
     * {@code DELETE  /c-tax-invoices/:id} : delete the "id" cTaxInvoice.
     *
     * @param id the id of the cTaxInvoiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-tax-invoices/{id}")
    public ResponseEntity<Void> deleteCTaxInvoice(@PathVariable Long id) {
        log.debug("REST request to delete CTaxInvoice : {}", id);
        cTaxInvoiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
