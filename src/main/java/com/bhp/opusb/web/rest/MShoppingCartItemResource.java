package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MShoppingCartItemService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MShoppingCartItemDTO;
import com.bhp.opusb.service.dto.MShoppingCartItemCriteria;
import com.bhp.opusb.service.MShoppingCartItemQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MShoppingCartItem}.
 */
@RestController
@RequestMapping("/api")
public class MShoppingCartItemResource {

    private final Logger log = LoggerFactory.getLogger(MShoppingCartItemResource.class);

    private static final String ENTITY_NAME = "mShoppingCartItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MShoppingCartItemService mShoppingCartItemService;

    private final MShoppingCartItemQueryService mShoppingCartItemQueryService;

    public MShoppingCartItemResource(MShoppingCartItemService mShoppingCartItemService, MShoppingCartItemQueryService mShoppingCartItemQueryService) {
        this.mShoppingCartItemService = mShoppingCartItemService;
        this.mShoppingCartItemQueryService = mShoppingCartItemQueryService;
    }

    /**
     * {@code POST  /m-shopping-cart-items} : Create a new mShoppingCartItem.
     *
     * @param mShoppingCartItemDTO the mShoppingCartItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mShoppingCartItemDTO, or with status {@code 400 (Bad Request)} if the mShoppingCartItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-shopping-cart-items")
    public ResponseEntity<MShoppingCartItemDTO> createMShoppingCartItem(@Valid @RequestBody MShoppingCartItemDTO mShoppingCartItemDTO) throws URISyntaxException {
        log.debug("REST request to save MShoppingCartItem : {}", mShoppingCartItemDTO);
        if (mShoppingCartItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new mShoppingCartItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MShoppingCartItemDTO result = mShoppingCartItemService.save(mShoppingCartItemDTO);
        return ResponseEntity.created(new URI("/api/m-shopping-cart-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-shopping-cart-items} : Updates an existing mShoppingCartItem.
     *
     * @param mShoppingCartItemDTO the mShoppingCartItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mShoppingCartItemDTO,
     * or with status {@code 400 (Bad Request)} if the mShoppingCartItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mShoppingCartItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-shopping-cart-items")
    public ResponseEntity<MShoppingCartItemDTO> updateMShoppingCartItem(@Valid @RequestBody MShoppingCartItemDTO mShoppingCartItemDTO) throws URISyntaxException {
        log.debug("REST request to update MShoppingCartItem : {}", mShoppingCartItemDTO);
        if (mShoppingCartItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MShoppingCartItemDTO result = mShoppingCartItemService.save(mShoppingCartItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mShoppingCartItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-shopping-cart-items} : get all the mShoppingCartItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mShoppingCartItems in body.
     */
    @GetMapping("/m-shopping-cart-items")
    public ResponseEntity<List<MShoppingCartItemDTO>> getAllMShoppingCartItems(MShoppingCartItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MShoppingCartItems by criteria: {}", criteria);
        Page<MShoppingCartItemDTO> page = mShoppingCartItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-shopping-cart-items/count} : count all the mShoppingCartItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-shopping-cart-items/count")
    public ResponseEntity<Long> countMShoppingCartItems(MShoppingCartItemCriteria criteria) {
        log.debug("REST request to count MShoppingCartItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(mShoppingCartItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-shopping-cart-items/:id} : get the "id" mShoppingCartItem.
     *
     * @param id the id of the mShoppingCartItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mShoppingCartItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-shopping-cart-items/{id}")
    public ResponseEntity<MShoppingCartItemDTO> getMShoppingCartItem(@PathVariable Long id) {
        log.debug("REST request to get MShoppingCartItem : {}", id);
        Optional<MShoppingCartItemDTO> mShoppingCartItemDTO = mShoppingCartItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mShoppingCartItemDTO);
    }

    /**
     * {@code DELETE  /m-shopping-cart-items/:id} : delete the "id" mShoppingCartItem.
     *
     * @param id the id of the mShoppingCartItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-shopping-cart-items/{id}")
    public ResponseEntity<Void> deleteMShoppingCartItem(@PathVariable Long id) {
        log.debug("REST request to delete MShoppingCartItem : {}", id);
        mShoppingCartItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
