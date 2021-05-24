package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionContentQueryService;
import com.bhp.opusb.service.MAuctionContentService;
import com.bhp.opusb.service.dto.MAuctionContentCriteria;
import com.bhp.opusb.service.dto.MAuctionContentDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionContent}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionContentResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionContentResource.class);

    private static final String ENTITY_NAME = "mAuctionContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionContentService mAuctionContentService;

    private final MAuctionContentQueryService mAuctionContentQueryService;

    public MAuctionContentResource(MAuctionContentService mAuctionContentService, MAuctionContentQueryService mAuctionContentQueryService) {
        this.mAuctionContentService = mAuctionContentService;
        this.mAuctionContentQueryService = mAuctionContentQueryService;
    }

    /**
     * {@code POST  /m-auction-contents} : Create a new mAuctionContent.
     *
     * @param mAuctionContentDTO the mAuctionContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionContentDTO, or with status {@code 400 (Bad Request)} if the mAuctionContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-contents")
    public ResponseEntity<MAuctionContentDTO> createMAuctionContent(@Valid @RequestBody MAuctionContentDTO mAuctionContentDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionContent : {}", mAuctionContentDTO);
        if (mAuctionContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionContentDTO result = mAuctionContentService.save(mAuctionContentDTO);
        return ResponseEntity.created(new URI("/api/m-auction-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-contents} : Updates an existing mAuctionContent.
     *
     * @param mAuctionContentDTO the mAuctionContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionContentDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-contents")
    public ResponseEntity<MAuctionContentDTO> updateMAuctionContent(@Valid @RequestBody MAuctionContentDTO mAuctionContentDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionContent : {}", mAuctionContentDTO);
        if (mAuctionContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionContentDTO result = mAuctionContentService.save(mAuctionContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-contents} : get all the mAuctionContents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionContents in body.
     */
    @GetMapping("/m-auction-contents")
    public ResponseEntity<List<MAuctionContentDTO>> getAllMAuctionContents(MAuctionContentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionContents by criteria: {}", criteria);
        Page<MAuctionContentDTO> page = mAuctionContentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-contents/count} : count all the mAuctionContents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-contents/count")
    public ResponseEntity<Long> countMAuctionContents(MAuctionContentCriteria criteria) {
        log.debug("REST request to count MAuctionContents by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionContentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-contents/:id} : get the "id" mAuctionContent.
     *
     * @param id the id of the mAuctionContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-contents/{id}")
    public ResponseEntity<MAuctionContentDTO> getMAuctionContent(@PathVariable Long id) {
        log.debug("REST request to get MAuctionContent : {}", id);
        Optional<MAuctionContentDTO> mAuctionContentDTO = mAuctionContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionContentDTO);
    }

    /**
     * {@code DELETE  /m-auction-contents/:id} : delete the "id" mAuctionContent.
     *
     * @param id the id of the mAuctionContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-contents/{id}")
    public ResponseEntity<Void> deleteMAuctionContent(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionContent : {}", id);
        mAuctionContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
