package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractMessageService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractMessageDTO;
import com.bhp.opusb.service.dto.MContractMessageCriteria;
import com.bhp.opusb.service.MContractMessageQueryService;

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

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.MContractMessage}.
 */
@RestController
@RequestMapping("/api")
public class MContractMessageResource {

    private final Logger log = LoggerFactory.getLogger(MContractMessageResource.class);

    private static final String ENTITY_NAME = "mContractMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractMessageService mContractMessageService;

    private final MContractMessageQueryService mContractMessageQueryService;

    public MContractMessageResource(MContractMessageService mContractMessageService, MContractMessageQueryService mContractMessageQueryService) {
        this.mContractMessageService = mContractMessageService;
        this.mContractMessageQueryService = mContractMessageQueryService;
    }

    /**
     * {@code POST  /m-contract-messages} : Create a new mContractMessage.
     *
     * @param mContractMessageDTO the mContractMessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractMessageDTO, or with status {@code 400 (Bad Request)} if the mContractMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-messages")
    public ResponseEntity<MContractMessageDTO> createMContractMessage(@Valid @RequestBody MContractMessageDTO mContractMessageDTO) throws URISyntaxException, MessagingException, IOException {
        log.debug("REST request to save MContractMessage : {}", mContractMessageDTO);
        if (mContractMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContractMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractMessageDTO result = mContractMessageService.save(mContractMessageDTO);
        return ResponseEntity.created(new URI("/api/m-contract-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-messages} : Updates an existing mContractMessage.
     *
     * @param mContractMessageDTO the mContractMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractMessageDTO,
     * or with status {@code 400 (Bad Request)} if the mContractMessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-messages")
    public ResponseEntity<MContractMessageDTO> updateMContractMessage(@Valid @RequestBody MContractMessageDTO mContractMessageDTO)  throws URISyntaxException, MessagingException, IOException {
        log.debug("REST request to update MContractMessage : {}", mContractMessageDTO);
        if (mContractMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractMessageDTO result = mContractMessageService.save(mContractMessageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-messages} : get all the mContractMessages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractMessages in body.
     */
    @GetMapping("/m-contract-messages")
    public ResponseEntity<List<MContractMessageDTO>> getAllMContractMessages(MContractMessageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractMessages by criteria: {}", criteria);
        Page<MContractMessageDTO> page = mContractMessageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-messages/count} : count all the mContractMessages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-messages/count")
    public ResponseEntity<Long> countMContractMessages(MContractMessageCriteria criteria) {
        log.debug("REST request to count MContractMessages by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractMessageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-messages/:id} : get the "id" mContractMessage.
     *
     * @param id the id of the mContractMessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractMessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-messages/{id}")
    public ResponseEntity<MContractMessageDTO> getMContractMessage(@PathVariable Long id) {
        log.debug("REST request to get MContractMessage : {}", id);
        Optional<MContractMessageDTO> mContractMessageDTO = mContractMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractMessageDTO);
    }

    /**
     * {@code DELETE  /m-contract-messages/:id} : delete the "id" mContractMessage.
     *
     * @param id the id of the mContractMessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-messages/{id}")
    public ResponseEntity<Void> deleteMContractMessage(@PathVariable Long id) {
        log.debug("REST request to delete MContractMessage : {}", id);
        mContractMessageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
