package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorPerformanceReportService;
import com.bhp.opusb.service.MVendorPerformanceReportService.VendorPerformanceDashboard;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorPerformanceReportDTO;
import com.bhp.opusb.service.dto.MVendorPerformanceReportDetailDTO;
import com.bhp.opusb.service.dto.MVendorPerformanceReportCriteria;
import com.bhp.opusb.service.MVendorPerformanceReportQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorPerformanceReport}.
 */
@RestController
@RequestMapping("/api")
public class MVendorPerformanceReportResource {

    private final Logger log = LoggerFactory.getLogger(MVendorPerformanceReportResource.class);

    private final MVendorPerformanceReportService mVendorPerformanceReportService;

    private final MVendorPerformanceReportQueryService mVendorPerformanceReportQueryService;

    public MVendorPerformanceReportResource(MVendorPerformanceReportService mVendorPerformanceReportService, MVendorPerformanceReportQueryService mVendorPerformanceReportQueryService) {
        this.mVendorPerformanceReportService = mVendorPerformanceReportService;
        this.mVendorPerformanceReportQueryService = mVendorPerformanceReportQueryService;
    }

    /**
     * {@code GET  /m-vendor-performance-reports} : get all the mVendorPerformanceReports.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorPerformanceReports in body.
     */
    @GetMapping("/m-vendor-performance-reports")
    public ResponseEntity<List<MVendorPerformanceReportDTO>> getAllMVendorPerformanceReports(MVendorPerformanceReportCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorPerformanceReports by criteria: {}", criteria);
        Page<MVendorPerformanceReportDTO> page = mVendorPerformanceReportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-performance-report/detail} : get detail for a vendor.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the MVendorPerformanceReportDetailDTO in body.
     */
    @GetMapping("/m-vendor-performance-report/detail")
    public ResponseEntity<MVendorPerformanceReportDetailDTO> getMVendorPerformanceReportDetail(@RequestParam Integer vendorId) {
        log.debug("REST request to get MVendorPerformanceReportDetail for vendorId: {}", vendorId);
        MVendorPerformanceReportDetailDTO res = mVendorPerformanceReportService.retrieveDetail(vendorId.longValue(), "1y");
        return ResponseEntity.ok().body(res);
    }

    /**
     * {@code GET  /m-vendor-performance-report/dashboard} : get dashboard content.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of VendorPerformanceDashboards in body.
     */
    @GetMapping("/m-vendor-performance-report/dashboard")
    public ResponseEntity<List<VendorPerformanceDashboard>> getMVendorPerformanceDashboardContent() {
        log.debug("REST request to get VendorPerformanceDashboard");
        List<VendorPerformanceDashboard> res = mVendorPerformanceReportService.getTop5QualityRating();
        return ResponseEntity.ok().body(res);
    }

    /**
     * {@code GET  /m-vendor-performance-reports/count} : count all the mVendorPerformanceReports.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-performance-reports/count")
    public ResponseEntity<Long> countMVendorPerformanceReports(MVendorPerformanceReportCriteria criteria) {
        log.debug("REST request to count MVendorPerformanceReports by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorPerformanceReportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-performance-reports/:id} : get the "id" mVendorPerformanceReport.
     *
     * @param id the id of the mVendorPerformanceReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorPerformanceReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-performance-reports/{id}")
    public ResponseEntity<MVendorPerformanceReportDTO> getMVendorPerformanceReport(@PathVariable Long id) {
        log.debug("REST request to get MVendorPerformanceReport : {}", id);
        Optional<MVendorPerformanceReportDTO> mVendorPerformanceReportDTO = mVendorPerformanceReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorPerformanceReportDTO);
    }
}
