package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.domain.enumeration.MBiddingProcess;
import com.bhp.opusb.service.MBiddingQueryService;
import com.bhp.opusb.service.MBiddingService;
import com.bhp.opusb.service.dto.MBiddingCriteria;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MBiddingFormDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MBidding}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingResource.class);

    private static final String ENTITY_NAME = "mBidding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingService mBiddingService;

    private final MBiddingQueryService mBiddingQueryService;

    public MBiddingResource(MBiddingService mBiddingService, MBiddingQueryService mBiddingQueryService) {
        this.mBiddingService = mBiddingService;
        this.mBiddingQueryService = mBiddingQueryService;
    }

    /**
     * {@code POST  /m-biddings} : Create a new mBidding.
     *
     * @param mBiddingDTO the mBiddingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingDTO, or with status {@code 400 (Bad Request)} if the mBidding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-biddings")
    public ResponseEntity<MBiddingDTO> createMBidding(@Valid @RequestBody MBiddingDTO mBiddingDTO) throws URISyntaxException {
        log.debug("REST request to save MBidding : {}", mBiddingDTO);
        if (mBiddingDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBidding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingDTO result = mBiddingService.save(mBiddingDTO);
        return ResponseEntity.created(new URI("/api/m-biddings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-biddings} : Create a new mBidding.
     *
     * @param mBiddingDTO the mBiddingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingDTO, or with status {@code 400 (Bad Request)} if the mBidding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-biddings/save-form")
    public ResponseEntity<MBiddingDTO> saveMBiddingForm(@RequestBody MBiddingFormDTO mBiddingDTO) throws URISyntaxException {
        log.debug("REST request to save MBidding form : {}", mBiddingDTO);
        MBiddingDTO result = mBiddingService.saveForm(mBiddingDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /m-biddings} : Updates an existing mBidding.
     *
     * @param mBiddingDTO the mBiddingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-biddings")
    public ResponseEntity<MBiddingDTO> updateMBidding(@Valid @RequestBody MBiddingDTO mBiddingDTO) throws URISyntaxException {
        log.debug("REST request to update MBidding : {}", mBiddingDTO);
        if (mBiddingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingDTO result = mBiddingService.save(mBiddingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-biddings} : get all the mBiddings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddings in body.
     */
    @GetMapping("/m-biddings")
    public ResponseEntity<List<MBiddingDTO>> getAllMBiddings(MBiddingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddings by criteria: {}", criteria);
        Page<MBiddingDTO> page = mBiddingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

//    @GetMapping("/m-biddings-sendemail")
//    public String getAllMBiddings() {
//        log.debug("");
//        log.info("testing send email");
//        mBiddingService.sendEmail("erid.rahmad@gmail.com","testing","testing",false,false);
//        return null;
//    }

    /**
     * {@code GET  /m-biddings/count} : count all the mBiddings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-biddings/count")
    public ResponseEntity<Long> countMBiddings(MBiddingCriteria criteria) {
        log.debug("REST request to count MBiddings by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-biddings/:id} : get the "id" mBidding.
     *
     * @param id the id of the mBiddingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-biddings/{id}")
    public ResponseEntity<MBiddingDTO> getMBidding(@PathVariable Long id) {
        log.debug("REST request to get MBidding : {}", id);
        Optional<MBiddingDTO> mBiddingDTO = mBiddingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingDTO);
    }

    /**
     * {@code GET  /m-biddings/form/:id} : get the "id" mBidding (for the form).
     *
     * @param id the id of the mBiddingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-biddings/{stepName}/{id}")
    public ResponseEntity<MBiddingFormDTO> getMBiddingForm(@PathVariable String stepName, @PathVariable Long id) {
        log.debug("REST request to get MBidding : {}", id);
        MBiddingProcess step;
        
        try {
            step = MBiddingProcess.valueOf(stepName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException("Invalid step name", ENTITY_NAME, "invalidstep");
        }
        
        Optional<MBiddingFormDTO> mBiddingDTO = mBiddingService.findOneForm(id, step);
        return ResponseUtil.wrapOrNotFound(mBiddingDTO);
    }

    /**
     * {@code DELETE  /m-biddings/:id} : delete the "id" mBidding.
     *
     * @param id the id of the mBiddingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-biddings/{id}")
    public ResponseEntity<Void> deleteMBidding(@PathVariable Long id) {
        log.debug("REST request to delete MBidding : {}", id);
        mBiddingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
