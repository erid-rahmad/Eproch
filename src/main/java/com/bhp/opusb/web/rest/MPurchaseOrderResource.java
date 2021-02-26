package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPurchaseOrderService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.service.dto.MPurchaseOrderCriteria;
import com.bhp.opusb.service.MPurchaseOrderQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPurchaseOrder}.
 */
@RestController
@RequestMapping("/api")
public class MPurchaseOrderResource {

    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderResource.class);

    private static final String ENTITY_NAME = "mPurchaseOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPurchaseOrderService mPurchaseOrderService;

    private final MPurchaseOrderQueryService mPurchaseOrderQueryService;

    public MPurchaseOrderResource(MPurchaseOrderService mPurchaseOrderService, MPurchaseOrderQueryService mPurchaseOrderQueryService) {
        this.mPurchaseOrderService = mPurchaseOrderService;
        this.mPurchaseOrderQueryService = mPurchaseOrderQueryService;
    }

    /**
     * {@code POST  /m-purchase-orders} : Create a new mPurchaseOrder.
     *
     * @param mPurchaseOrderDTO the mPurchaseOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPurchaseOrderDTO, or with status {@code 400 (Bad Request)} if the mPurchaseOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-purchase-orders")
    public ResponseEntity<MPurchaseOrderDTO> createMPurchaseOrder(@Valid @RequestBody MPurchaseOrderDTO mPurchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to save MPurchaseOrder : {}", mPurchaseOrderDTO);
        if (mPurchaseOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPurchaseOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPurchaseOrderDTO result = mPurchaseOrderService.save(mPurchaseOrderDTO);
        return ResponseEntity.created(new URI("/api/m-purchase-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-purchase-orders} : Updates an existing mPurchaseOrder.
     *
     * @param mPurchaseOrderDTO the mPurchaseOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPurchaseOrderDTO,
     * or with status {@code 400 (Bad Request)} if the mPurchaseOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPurchaseOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-purchase-orders")
    public ResponseEntity<MPurchaseOrderDTO> updateMPurchaseOrder(@Valid @RequestBody MPurchaseOrderDTO mPurchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to update MPurchaseOrder : {}", mPurchaseOrderDTO);
        if (mPurchaseOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPurchaseOrderDTO result = mPurchaseOrderService.save(mPurchaseOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPurchaseOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-purchase-orders} : get all the mPurchaseOrders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPurchaseOrders in body.
     */
    @GetMapping("/m-purchase-orders")
    public ResponseEntity<List<MPurchaseOrderDTO>> getAllMPurchaseOrders(MPurchaseOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPurchaseOrders by criteria: {}", criteria);
        log.debug("this get 1");
        Page<MPurchaseOrderDTO> page = mPurchaseOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-purchase-orders/count} : count all the mPurchaseOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-purchase-orders/count")
    public ResponseEntity<Long> countMPurchaseOrders(MPurchaseOrderCriteria criteria) {
        log.debug("REST request to count MPurchaseOrders by criteria: {}", criteria);
        log.info("this get 2");
        return ResponseEntity.ok().body(mPurchaseOrderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-purchase-orders/:id} : get the "id" mPurchaseOrder.
     *
     * @param id the id of the mPurchaseOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPurchaseOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-purchase-orders/{id}")
    public ResponseEntity<MPurchaseOrderDTO> getMPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to get MPurchaseOrder : {}", id);
        Optional<MPurchaseOrderDTO> mPurchaseOrderDTO = mPurchaseOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPurchaseOrderDTO);
    }

    /**
     * {@code DELETE  /m-purchase-orders/:id} : delete the "id" mPurchaseOrder.
     *
     * @param id the id of the mPurchaseOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-purchase-orders/{id}")
    public ResponseEntity<Void> deleteMPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to delete MPurchaseOrder : {}", id);
        mPurchaseOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/m-purchase-orders/report/{poNo}")
    public void reportPurchaseOrder(@PathVariable Long poNo, HttpServletResponse response) throws IOException {

        JasperPrint jasperPrint = null;
        String fileName = "";

        fileName = "Purchase Order - "+poNo+".pdf";

        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        OutputStream out = response.getOutputStream();

        try {
            jasperPrint = mPurchaseOrderService.exportPurchaseOrder(poNo);

            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        } catch (SQLException | JRException e) {
            e.printStackTrace();
        }
    }
}
