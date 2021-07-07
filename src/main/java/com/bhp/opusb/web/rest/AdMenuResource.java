package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.AdMenuQueryService;
import com.bhp.opusb.service.AdMenuService;
import com.bhp.opusb.service.dto.AdMenuCriteria;
import com.bhp.opusb.service.dto.AdMenuDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.AdMenu}.
 */
@RestController
@RequestMapping("/api")
public class AdMenuResource {

    private final Logger log = LoggerFactory.getLogger(AdMenuResource.class);

    private static final String ENTITY_NAME = "adMenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdMenuService adMenuService;

    private final AdMenuQueryService adMenuQueryService;

    public AdMenuResource(AdMenuService adMenuService, AdMenuQueryService adMenuQueryService) {
        this.adMenuService = adMenuService;
        this.adMenuQueryService = adMenuQueryService;
    }

    /**
     * {@code POST  /ad-menus} : Create a new adMenu.
     *
     * @param adMenuDTO the adMenuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adMenuDTO, or with status {@code 400 (Bad Request)} if the adMenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-menus")
    public ResponseEntity<AdMenuDTO> createAdMenu(@Valid @RequestBody AdMenuDTO adMenuDTO) throws URISyntaxException {
        log.debug("REST request to save AdMenu : {}", adMenuDTO);
        if (adMenuDTO.getId() != null) {
            throw new BadRequestAlertException("A new adMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdMenuDTO result = adMenuService.save(adMenuDTO);
        return ResponseEntity.created(new URI("/api/ad-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-menus} : Updates an existing adMenu.
     *
     * @param adMenuDTO the adMenuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adMenuDTO,
     * or with status {@code 400 (Bad Request)} if the adMenuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adMenuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-menus")
    public ResponseEntity<AdMenuDTO> updateAdMenu(@Valid @RequestBody AdMenuDTO adMenuDTO) throws URISyntaxException {
        log.debug("REST request to update AdMenu : {}", adMenuDTO);
        if (adMenuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdMenuDTO result = adMenuService.save(adMenuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adMenuDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/ad-menus/sequence")
    public ResponseEntity<List<AdMenuDTO>> updateSequence(@Valid @RequestBody List<AdMenuDTO> adMenuDTOs) throws URISyntaxException {
        List<AdMenuDTO> result = adMenuService.saveAll(adMenuDTOs);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /ad-menus} : get all the adMenus.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adMenus in body.
     */
    @GetMapping("/ad-menus")
    public ResponseEntity<List<AdMenuDTO>> getAllAdMenus(AdMenuCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdMenus by criteria: {}", criteria);
        Page<AdMenuDTO> page = adMenuQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-menus/main-menu} : get all the adMenus.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adMenus in body.
     */
    @GetMapping("/ad-menus/main-menu")
    public ResponseEntity<List<AdMenuDTO>> getMainMenu() {
        log.debug("REST request to get parent AdMenus");
        List<AdMenuDTO> menus = adMenuQueryService.getMainMenu();
        return ResponseEntity.ok().body(menus);
    }

    /**
     * {@code GET /ad-menus/full-path/:id} : get the fullpath to the specific menu.
     *
     * @param id the ID of the target menu.
     */
    @GetMapping("/ad-menus/full-path/{id}")
    public ResponseEntity<String> getFullPath(@PathVariable Long id) {
        log.debug("REST request to get fullpath to submenu : {}", id);
        String fullPath = adMenuService.getFullPath(id);
        return ResponseEntity.ok().body(fullPath);
    }

    /**
     * {@code GET  /ad-menus/count} : count all the adMenus.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-menus/count")
    public ResponseEntity<Long> countAdMenus(AdMenuCriteria criteria) {
        log.debug("REST request to count AdMenus by criteria: {}", criteria);
        return ResponseEntity.ok().body(adMenuQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-menus/:id} : get the "id" adMenu.
     *
     * @param id the id of the adMenuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adMenuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-menus/{id}")
    public ResponseEntity<AdMenuDTO> getAdMenu(@PathVariable Long id) {
        log.debug("REST request to get AdMenu : {}", id);
        Optional<AdMenuDTO> adMenuDTO = adMenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adMenuDTO);
    }

    /**
     * {@code DELETE  /ad-menus/:id} : delete the "id" adMenu.
     *
     * @param id the id of the adMenuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-menus/{id}")
    public ResponseEntity<Void> deleteAdMenu(@PathVariable Long id) {
        log.debug("REST request to delete AdMenu : {}", id);
        adMenuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
