package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVerificationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVerificationDTO;
import com.bhp.opusb.service.dto.VerificationDTO;
import com.bhp.opusb.service.dto.MVerificationCriteria;
import com.bhp.opusb.service.MVerificationQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.MVerification}.
 */
@RestController
@RequestMapping("/api")
public class MVerificationResource {

    private final Logger log = LoggerFactory.getLogger(MVerificationResource.class);

    private static final String ENTITY_NAME = "mVerification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVerificationService mVerificationService;

    private final MVerificationQueryService mVerificationQueryService;

    public MVerificationResource(MVerificationService mVerificationService, MVerificationQueryService mVerificationQueryService) {
        this.mVerificationService = mVerificationService;
        this.mVerificationQueryService = mVerificationQueryService;
    }

    /**
     * {@code POST  /m-verifications} : Create a new mVerification.
     *
     * @param mVerificationDTO the mVerificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new mVerificationDTO, or with status
     *         {@code 400 (Bad Request)} if the mVerification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-verifications")
    public ResponseEntity<MVerificationDTO> createMVerification(@Valid @RequestBody MVerificationDTO mVerificationDTO) throws URISyntaxException {
        log.debug("REST request to save MVerification : {}", mVerificationDTO);
        if (mVerificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVerification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVerificationDTO result = mVerificationService.save(mVerificationDTO);
        return ResponseEntity.created(new URI("/api/m-verifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/m-verifications/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public void submitEVerification(@Valid @RequestBody VerificationDTO verificationDTO) {
        mVerificationService.submitEVerification(verificationDTO);
    }

    @PutMapping("/m-verifications/submit")
    @ResponseStatus(HttpStatus.OK)
    public void submitUpdateEVerification(@Valid @RequestBody VerificationDTO verificationDTO) {
        mVerificationService.updateDocumentStatus(verificationDTO);
    }

    @GetMapping("/m-verifications/report/{verificationId}/{verificationNo}/{key}")
    public void reportEVerification(@PathVariable Long verificationId, @PathVariable Long verificationNo, @PathVariable Long key, HttpServletResponse response)
            throws IOException {

        JasperPrint jasperPrint = null;
        String fileName = "";

        if (key == 1) {
            fileName = "Verification - "+verificationNo+".pdf";
        } else if(key == 2) {
            fileName = "Summary Verification - "+verificationNo+".pdf";
        }

        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        OutputStream out = response.getOutputStream();

        try {
            jasperPrint = mVerificationService.exportVerification(verificationId, key);

            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        } catch (SQLException | JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code PUT  /m-verifications} : Updates an existing mVerification.
     *
     * @param mVerificationDTO the mVerificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVerificationDTO,
     * or with status {@code 400 (Bad Request)} if the mVerificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVerificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-verifications")
    public ResponseEntity<MVerificationDTO> updateMVerification(@Valid @RequestBody MVerificationDTO mVerificationDTO) throws URISyntaxException {
        log.debug("REST request to update MVerification : {}", mVerificationDTO);
        if (mVerificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVerificationDTO result = mVerificationService.save(mVerificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVerificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-verifications} : get all the mVerifications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVerifications in body.
     */
    @GetMapping("/m-verifications")
    public ResponseEntity<List<MVerificationDTO>> getAllMVerifications(MVerificationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVerifications by criteria: {}", criteria);
        Page<MVerificationDTO> page = mVerificationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-verifications/count} : count all the mVerifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-verifications/count")
    public ResponseEntity<Long> countMVerifications(MVerificationCriteria criteria) {
        log.debug("REST request to count MVerifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVerificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-verifications/:id} : get the "id" mVerification.
     *
     * @param id the id of the mVerificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVerificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-verifications/{id}")
    public ResponseEntity<MVerificationDTO> getMVerification(@PathVariable Long id) {
        log.debug("REST request to get MVerification : {}", id);
        Optional<MVerificationDTO> mVerificationDTO = mVerificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVerificationDTO);
    }

    /**
     * {@code DELETE  /m-verifications/:id} : delete the "id" mVerification.
     *
     * @param id the id of the mVerificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-verifications/{id}")
    public ResponseEntity<Void> deleteMVerification(@PathVariable Long id) {
        log.debug("REST request to delete MVerification : {}", id);
        mVerificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
