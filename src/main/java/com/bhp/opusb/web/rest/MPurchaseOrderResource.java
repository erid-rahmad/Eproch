package com.bhp.opusb.web.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.bhp.opusb.service.MPurchaseOrderQueryService;
import com.bhp.opusb.service.MPurchaseOrderService;
import com.bhp.opusb.service.dto.MPurchaseOrderCriteria;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

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
     * {@code POST  /m-purchase-orders/generate} : Generate mPurchaseOrder from Purchase Requisition.
     *
     * @param mPurchaseOrderDTO the mPurchaseOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPurchaseOrderDTO, or with status {@code 400 (Bad Request)} if the mPurchaseOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-purchase-orders/generate")
    public ResponseEntity<String> generateMPurchaseOrders(@Valid @RequestBody MPurchaseOrderDTO mPurchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to save MPurchaseOrder : {}", mPurchaseOrderDTO);

        if (mPurchaseOrderDTO.getRequisitionLines() == null) {
            throw new BadRequestAlertException("The submitted purchase order has no lines", ENTITY_NAME, "poNoLines");
        }
//        List<MPurchaseOrderDTO> result = mPurchaseOrderService.saveFromRequisition(mPurchaseOrderDTO);
        mPurchaseOrderService.generatePoFromQuatation(mPurchaseOrderDTO);
//        String queryString = result.stream().map(po -> "id.equals=" + po.getId()).collect(Collectors.joining("&"));

        return ResponseEntity.ok("ok");
//        return ResponseEntity.created(new URI("/api/m-purchase-orders/" + queryString))
//            .body(result);
    }

    /**
     * {@code POST  /m-purchase-orders/generate} : Generate mPurchaseOrder from Vendor Confirmation.
     *
     * @param mPurchaseOrderDTO the mPurchaseOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPurchaseOrderDTO, or with status {@code 400 (Bad Request)} if the mPurchaseOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-purchase-orders/generate-from-vc")
    public ResponseEntity<MPurchaseOrderDTO> generateMPurchaseOrdersFromVendor(@Valid @RequestBody MPurchaseOrderDTO mPurchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to save MPurchaseOrder : {}", mPurchaseOrderDTO);
        if (mPurchaseOrderDTO.getPoLines() == null) {
            throw new BadRequestAlertException("The submitted purchase order has no lines", ENTITY_NAME, "poNoLines");
        }
        if (mPurchaseOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPurchaseOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPurchaseOrderDTO result = mPurchaseOrderService.generatePurchaseOrderFromVendor(mPurchaseOrderDTO);
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
        Page<MPurchaseOrderDTO> page = mPurchaseOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-purchase-orders/dashbordView")
    public ResponseEntity< List<MPurchaseOrderLineDTO>> getAllMPurchaseOrdersDashbord() {
        log.debug("REST request to get MPurchaseOrders by dashbord: {}");
        return ResponseEntity.ok(mPurchaseOrderService.findAllForDashbord());
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
    public void reportPurchaseOrder(@PathVariable Long poNo, HttpServletResponse response) throws IOException, JRException, SQLException {
        String fileName = "Purchase Order - " + poNo + ".pdf";

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        OutputStream out = response.getOutputStream();

        JasperPrint jasperPrint = mPurchaseOrderService.exportPurchaseOrder(poNo);
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
    }

    /**
     * TODO Make a single endpoint for document status update.
     * {@code PUT  /c-purchase-orders} : Apply the document action to an existing mRequisitions.
     *
     * @param mPurchaseOrderDTO the mPurchaseOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPurchaseOrderDTO,
     * or with status {@code 400 (Bad Request)} if the mPurchaseOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPurchaseOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-purchase-orders/update-doc-status")
    @ResponseStatus(HttpStatus.OK)
    public void applyDocumentAction(@Valid @RequestBody MPurchaseOrderDTO mPurchaseOrderDTO) {
        log.debug("REST request to apply MPurchaseOrderDTO's document action : {}", mPurchaseOrderDTO);
        if (mPurchaseOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        mPurchaseOrderService.updateDocumentStatus(mPurchaseOrderDTO);
    }
}
