package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CProductQueryService;
import com.bhp.opusb.service.CProductService;
import com.bhp.opusb.service.dto.CProductCriteria;
import com.bhp.opusb.service.dto.CProductDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CProduct}.
 */
@RestController
@RequestMapping("/api")
public class CProductResource {

    private final Logger log = LoggerFactory.getLogger(CProductResource.class);

    private static final String ENTITY_NAME = "cProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CProductService cProductService;

    private final CProductQueryService cProductQueryService;

    public CProductResource(CProductService cProductService, CProductQueryService cProductQueryService) {
        this.cProductService = cProductService;
        this.cProductQueryService = cProductQueryService;
    }

    /**
     * {@code POST  /c-products} : Create a new cProduct.
     *
     * @param cProductDTO the cProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cProductDTO, or with status {@code 400 (Bad Request)} if the cProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-products")
    public ResponseEntity<CProductDTO> createCProduct(@Valid @RequestBody CProductDTO cProductDTO) throws URISyntaxException {
        log.debug("REST request to save CProduct : {}", cProductDTO);
        if (cProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new cProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CProductDTO result = cProductService.save(cProductDTO);
        return ResponseEntity.created(new URI("/api/c-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /c-products/init-categories} : Create a new cProduct along with its categories.
     * This method is used to initialize the marketplace categories.
     *
     * @param cProductDTO the cProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cProductDTO, or with status {@code 400 (Bad Request)} if the cProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-products/init-categories")
    public ResponseEntity<CProductDTO> initCategories(@Valid @RequestBody Map<String, String> data) throws URISyntaxException {
        log.debug("REST request to initialize categories : {}", data);
        final String productCode = data.get("productCode");
        final String productName = data.get("productName");
        final String categoryCode = data.get("categoryCode");
        final String categoryName = data.get("categoryName");
        final String subcategoryCode = data.get("subcategoryCode");
        final String subcategoryName = data.get("subcategoryName");

        if (productCode == null || productName == null || categoryCode == null || categoryName == null
                || subcategoryCode == null || subcategoryName == null) {
            throw new BadRequestAlertException(
                    "Missing some mandatory fields: product/category/subcategory code and name.", ENTITY_NAME,
                    "mandatoryCodeName");
        }

        CProductDTO result = cProductService.save(productCode, productName, categoryCode, categoryName, subcategoryCode,
                subcategoryName);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /c-products} : Updates an existing cProduct.
     *
     * @param cProductDTO the cProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cProductDTO,
     * or with status {@code 400 (Bad Request)} if the cProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-products")
    public ResponseEntity<CProductDTO> updateCProduct(@Valid @RequestBody CProductDTO cProductDTO) throws URISyntaxException {
        log.debug("REST request to update CProduct : {}", cProductDTO);
        if (cProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CProductDTO result = cProductService.save(cProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-products} : get all the cProducts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cProducts in body.
     */
    @GetMapping("/c-products")
    public ResponseEntity<List<CProductDTO>> getAllCProducts(CProductCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CProducts by criteria: {}", criteria);
        Page<CProductDTO> page = cProductQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-products/count} : count all the cProducts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-products/count")
    public ResponseEntity<Long> countCProducts(CProductCriteria criteria) {
        log.debug("REST request to count CProducts by criteria: {}", criteria);
        return ResponseEntity.ok().body(cProductQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-products/:id} : get the "id" cProduct.
     *
     * @param id the id of the cProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-products/{id}")
    public ResponseEntity<CProductDTO> getCProduct(@PathVariable Long id) {
        log.debug("REST request to get CProduct : {}", id);
        Optional<CProductDTO> cProductDTO = cProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cProductDTO);
    }

    /**
     * {@code DELETE  /c-products/:id} : delete the "id" cProduct.
     *
     * @param id the id of the cProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-products/{id}")
    public ResponseEntity<Void> deleteCProduct(@PathVariable Long id) {
        log.debug("REST request to delete CProduct : {}", id);
        cProductService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
