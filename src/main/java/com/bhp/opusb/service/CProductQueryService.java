package com.bhp.opusb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CProductRepository;
import com.bhp.opusb.service.dto.CProductCriteria;
import com.bhp.opusb.service.dto.CProductDTO;
import com.bhp.opusb.service.mapper.CProductMapper;

/**
 * Service for executing complex queries for {@link CProduct} entities in the database.
 * The main input is a {@link CProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CProductDTO} or a {@link Page} of {@link CProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CProductQueryService extends QueryService<CProduct> {

    private final Logger log = LoggerFactory.getLogger(CProductQueryService.class);

    private final CProductRepository cProductRepository;

    private final CProductMapper cProductMapper;

    public CProductQueryService(CProductRepository cProductRepository, CProductMapper cProductMapper) {
        this.cProductRepository = cProductRepository;
        this.cProductMapper = cProductMapper;
    }

    /**
     * Return a {@link List} of {@link CProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CProductDTO> findByCriteria(CProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CProduct> specification = createSpecification(criteria);
        return cProductMapper.toDto(cProductRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductDTO> findByCriteria(CProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CProduct> specification = createSpecification(criteria);
        return cProductRepository.findAll(specification, page)
            .map(cProductMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CProduct> specification = createSpecification(criteria);
        return cProductRepository.count(specification);
    }

    /**
     * Function to convert {@link CProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CProduct> createSpecification(CProductCriteria criteria) {
        Specification<CProduct> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CProduct_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CProduct_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CProduct_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CProduct_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CProduct_.type));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CProduct_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CProduct_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CProduct_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getProductClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductClassificationId(),
                    root -> root.join(CProduct_.productClassification, JoinType.LEFT).get(CProductClassification_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(CProduct_.productCategory, JoinType.LEFT).get(CProductCategory_.id)));
            }
            if (criteria.getProductSubCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductSubCategoryId(),
                    root -> root.join(CProduct_.productSubCategory, JoinType.LEFT).get(CProductCategory_.id)));
            }
            if (criteria.getAssetAcctId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetAcctId(),
                    root -> root.join(CProduct_.assetAcct, JoinType.LEFT).get(CElementValue_.id)));
            }
            if (criteria.getExpenseAcctId() != null) {
                specification = specification.and(buildSpecification(criteria.getExpenseAcctId(),
                    root -> root.join(CProduct_.expenseAcct, JoinType.LEFT).get(CElementValue_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(CProduct_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
        }
        return specification;
    }
}
