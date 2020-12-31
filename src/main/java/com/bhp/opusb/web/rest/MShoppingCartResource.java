package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MShoppingCartService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MShoppingCartDTO;
import com.bhp.opusb.service.dto.MShoppingCartCriteria;
import com.bhp.opusb.service.MShoppingCartQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MShoppingCart}.
 */
@RestController
@RequestMapping("/api")
public class MShoppingCartResource {

    private final Logger log = LoggerFactory.getLogger(MShoppingCartResource.class);

    private static final String ENTITY_NAME = "mShoppingCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MShoppingCartService mShoppingCartService;

    private final MShoppingCartQueryService mShoppingCartQueryService;

    public MShoppingCartResource(MShoppingCartService mShoppingCartService, MShoppingCartQueryService mShoppingCartQueryService) {
        this.mShoppingCartService = mShoppingCartService;
        this.mShoppingCartQueryService = mShoppingCartQueryService;
    }

    /**
     * {@code POST  /m-shopping-carts} : Create a new mShoppingCart.
     *
     * @param mShoppingCartDTO the mShoppingCartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mShoppingCartDTO, or with status {@code 400 (Bad Request)} if the mShoppingCart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-shopping-carts")
    public ResponseEntity<MShoppingCartDTO> createMShoppingCart(@Valid @RequestBody MShoppingCartDTO mShoppingCartDTO) throws URISyntaxException {
        log.debug("REST request to save MShoppingCart : {}", mShoppingCartDTO);
        if (mShoppingCartDTO.getId() != null) {
            throw new BadRequestAlertException("A new mShoppingCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MShoppingCartDTO result = mShoppingCartService.save(mShoppingCartDTO);
        return ResponseEntity.created(new URI("/api/m-shopping-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-shopping-carts} : Updates an existing mShoppingCart.
     *
     * @param mShoppingCartDTO the mShoppingCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mShoppingCartDTO,
     * or with status {@code 400 (Bad Request)} if the mShoppingCartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mShoppingCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-shopping-carts")
    public ResponseEntity<MShoppingCartDTO> updateMShoppingCart(@Valid @RequestBody MShoppingCartDTO mShoppingCartDTO) throws URISyntaxException {
        log.debug("REST request to update MShoppingCart : {}", mShoppingCartDTO);
        if (mShoppingCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MShoppingCartDTO result = mShoppingCartService.save(mShoppingCartDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mShoppingCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-shopping-carts} : get all the mShoppingCarts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mShoppingCarts in body.
     */
    @GetMapping("/m-shopping-carts")
    public ResponseEntity<List<MShoppingCartDTO>> getAllMShoppingCarts(MShoppingCartCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MShoppingCarts by criteria: {}", criteria);
        Page<MShoppingCartDTO> page = mShoppingCartQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-shopping-carts/count} : count all the mShoppingCarts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-shopping-carts/count")
    public ResponseEntity<Long> countMShoppingCarts(MShoppingCartCriteria criteria) {
        log.debug("REST request to count MShoppingCarts by criteria: {}", criteria);
        return ResponseEntity.ok().body(mShoppingCartQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-shopping-carts/:id} : get the "id" mShoppingCart.
     *
     * @param id the id of the mShoppingCartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mShoppingCartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-shopping-carts/{id}")
    public ResponseEntity<MShoppingCartDTO> getMShoppingCart(@PathVariable Long id) {
        log.debug("REST request to get MShoppingCart : {}", id);
        Optional<MShoppingCartDTO> mShoppingCartDTO = mShoppingCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mShoppingCartDTO);
    }

    /**
     * {@code DELETE  /m-shopping-carts/:id} : delete the "id" mShoppingCart.
     *
     * @param id the id of the mShoppingCartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-shopping-carts/{id}")
    public ResponseEntity<Void> deleteMShoppingCart(@PathVariable Long id) {
        log.debug("REST request to delete MShoppingCart : {}", id);
        mShoppingCartService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
