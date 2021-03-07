package com.bhp.opusb.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.bhp.opusb.service.ADWindowQueryService;
import com.bhp.opusb.service.ADWindowService;
import com.bhp.opusb.service.dto.ADWindowCriteria;
import com.bhp.opusb.service.dto.ADWindowDTO;
import com.bhp.opusb.service.dto.ExportParameterDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
 * REST controller for managing {@link com.bhp.opusb.domain.ADWindow}.
 */
@RestController
@RequestMapping("/api")
public class ADWindowResource {

    private final Logger log = LoggerFactory.getLogger(ADWindowResource.class);

    private static final String ENTITY_NAME = "aDWindow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADWindowService aDWindowService;

    private final ADWindowQueryService aDWindowQueryService;

    public ADWindowResource(ADWindowService aDWindowService, ADWindowQueryService aDWindowQueryService) {
        this.aDWindowService = aDWindowService;
        this.aDWindowQueryService = aDWindowQueryService;
    }

    /**
     * {@code POST  /ad-windows} : Create a new aDWindow.
     *
     * @param aDWindowDTO the aDWindowDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDWindowDTO, or with status {@code 400 (Bad Request)} if the aDWindow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-windows")
    public ResponseEntity<ADWindowDTO> createADWindow(@Valid @RequestBody ADWindowDTO aDWindowDTO) throws URISyntaxException {
        log.debug("REST request to save ADWindow : {}", aDWindowDTO);
        if (aDWindowDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDWindow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADWindowDTO result = aDWindowService.save(aDWindowDTO);
        return ResponseEntity.created(new URI("/api/ad-windows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /ad-windows/export} : Export aDWindow record(s) and optionally its related tabs.
     *
     * @param aDWindowDTO the aDWindowDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDWindowDTO, or with status {@code 400 (Bad Request)} if the aDWindow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws IOException
     */
    @GetMapping("/ad-windows/export")
    public ResponseEntity<byte[]> exportADWindow(@Valid ExportParameterDTO parameter, HttpServletResponse response) throws IOException {
        log.debug("REST request to export ADWindow. parameter : {}", parameter);
        if (parameter.getWindowId() == 0) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String windowName = aDWindowService.getWindowName(parameter.getWindowId());
        byte[] bytes =aDWindowService.export(parameter, response);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", windowName + ".csv");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(httpHeaders).body(bytes);
    }

    /**
     * {@code PUT  /ad-windows} : Updates an existing aDWindow.
     *
     * @param aDWindowDTO the aDWindowDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDWindowDTO,
     * or with status {@code 400 (Bad Request)} if the aDWindowDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDWindowDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-windows")
    public ResponseEntity<ADWindowDTO> updateADWindow(@Valid @RequestBody ADWindowDTO aDWindowDTO) throws URISyntaxException {
        log.debug("REST request to update ADWindow : {}", aDWindowDTO);
        if (aDWindowDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADWindowDTO result = aDWindowService.save(aDWindowDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDWindowDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-windows} : get all the aDWindows.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDWindows in body.
     */
    @GetMapping("/ad-windows")
    public ResponseEntity<List<ADWindowDTO>> getAllADWindows(ADWindowCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADWindows by criteria: {}", criteria);
        Page<ADWindowDTO> page = aDWindowQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/ad-windows/tab-tree/{windowId}")
    public ResponseEntity<Map<String, Object>> getTabTree(@PathVariable Long windowId) {
        log.debug("REST request to get tab tree of window ID : {}", windowId);
        Map<String, Object> tabTree = aDWindowService.getTabTree(windowId);
        return ResponseEntity.ok().body(tabTree);
    }

    /**
     * {@code GET  /ad-windows/count} : count all the aDWindows.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-windows/count")
    public ResponseEntity<Long> countADWindows(ADWindowCriteria criteria) {
        log.debug("REST request to count ADWindows by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDWindowQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-windows/:id} : get the "id" aDWindow.
     *
     * @param id the id of the aDWindowDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDWindowDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-windows/{id}")
    public ResponseEntity<ADWindowDTO> getADWindow(@PathVariable Long id) {
        log.debug("REST request to get ADWindow : {}", id);
        Optional<ADWindowDTO> aDWindowDTO = aDWindowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDWindowDTO);
    }

    /**
     * {@code DELETE  /ad-windows/:id} : delete the "id" aDWindow.
     *
     * @param id the id of the aDWindowDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-windows/{id}")
    public ResponseEntity<Void> deleteADWindow(@PathVariable Long id) {
        log.debug("REST request to delete ADWindow : {}", id);
        aDWindowService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
