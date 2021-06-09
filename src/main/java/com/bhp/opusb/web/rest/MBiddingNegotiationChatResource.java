package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingNegotiationChatService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatDTO;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatCriteria;
import com.bhp.opusb.service.MBiddingNegotiationChatQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingNegotiationChat}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingNegotiationChatResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationChatResource.class);

    private static final String ENTITY_NAME = "mBiddingNegotiationChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingNegotiationChatService mBiddingNegotiationChatService;

    private final MBiddingNegotiationChatQueryService mBiddingNegotiationChatQueryService;

    public MBiddingNegotiationChatResource(MBiddingNegotiationChatService mBiddingNegotiationChatService, MBiddingNegotiationChatQueryService mBiddingNegotiationChatQueryService) {
        this.mBiddingNegotiationChatService = mBiddingNegotiationChatService;
        this.mBiddingNegotiationChatQueryService = mBiddingNegotiationChatQueryService;
    }

    /**
     * {@code POST  /m-bidding-negotiation-chats} : Create a new mBiddingNegotiationChat.
     *
     * @param mBiddingNegotiationChatDTO the mBiddingNegotiationChatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingNegotiationChatDTO, or with status {@code 400 (Bad Request)} if the mBiddingNegotiationChat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-negotiation-chats")
    public ResponseEntity<MBiddingNegotiationChatDTO> createMBiddingNegotiationChat(@Valid @RequestBody MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO) throws URISyntaxException, MessagingException, IOException {
        log.debug("REST request to save MBiddingNegotiationChat : {}", mBiddingNegotiationChatDTO);
        if (mBiddingNegotiationChatDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingNegotiationChat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingNegotiationChatDTO result = mBiddingNegotiationChatService.save(mBiddingNegotiationChatDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-negotiation-chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-negotiation-chats} : Updates an existing mBiddingNegotiationChat.
     *
     * @param mBiddingNegotiationChatDTO the mBiddingNegotiationChatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingNegotiationChatDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingNegotiationChatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingNegotiationChatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-negotiation-chats")
    public ResponseEntity<MBiddingNegotiationChatDTO> updateMBiddingNegotiationChat(@Valid @RequestBody MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO) throws URISyntaxException, MessagingException, IOException {
        log.debug("REST request to update MBiddingNegotiationChat : {}", mBiddingNegotiationChatDTO);
        if (mBiddingNegotiationChatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingNegotiationChatDTO result = mBiddingNegotiationChatService.save(mBiddingNegotiationChatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingNegotiationChatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-negotiation-chats} : get all the mBiddingNegotiationChats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingNegotiationChats in body.
     */
    @GetMapping("/m-bidding-negotiation-chats")
    public ResponseEntity<List<MBiddingNegotiationChatDTO>> getAllMBiddingNegotiationChats(MBiddingNegotiationChatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingNegotiationChats by criteria: {}", criteria);
        Page<MBiddingNegotiationChatDTO> page = mBiddingNegotiationChatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-negotiation-chats/count} : count all the mBiddingNegotiationChats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-negotiation-chats/count")
    public ResponseEntity<Long> countMBiddingNegotiationChats(MBiddingNegotiationChatCriteria criteria) {
        log.debug("REST request to count MBiddingNegotiationChats by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingNegotiationChatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-negotiation-chats/:id} : get the "id" mBiddingNegotiationChat.
     *
     * @param id the id of the mBiddingNegotiationChatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingNegotiationChatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-negotiation-chats/{id}")
    public ResponseEntity<MBiddingNegotiationChatDTO> getMBiddingNegotiationChat(@PathVariable Long id) {
        log.debug("REST request to get MBiddingNegotiationChat : {}", id);
        Optional<MBiddingNegotiationChatDTO> mBiddingNegotiationChatDTO = mBiddingNegotiationChatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingNegotiationChatDTO);
    }

    /**
     * {@code DELETE  /m-bidding-negotiation-chats/:id} : delete the "id" mBiddingNegotiationChat.
     *
     * @param id the id of the mBiddingNegotiationChatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-negotiation-chats/{id}")
    public ResponseEntity<Void> deleteMBiddingNegotiationChat(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingNegotiationChat : {}", id);
        mBiddingNegotiationChatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
