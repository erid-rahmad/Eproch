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

import com.bhp.opusb.domain.BusinessCategory;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.BusinessCategoryRepository;
import com.bhp.opusb.service.dto.BusinessCategoryCriteria;
import com.bhp.opusb.service.dto.BusinessCategoryDTO;
import com.bhp.opusb.service.mapper.BusinessCategoryMapper;

/**
 * Service for executing complex queries for {@link BusinessCategory} entities in the database.
 * The main input is a {@link BusinessCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessCategoryDTO} or a {@link Page} of {@link BusinessCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessCategoryQueryService extends QueryService<BusinessCategory> {

    private final Logger log = LoggerFactory.getLogger(BusinessCategoryQueryService.class);

    private final BusinessCategoryRepository businessCategoryRepository;

    private final BusinessCategoryMapper businessCategoryMapper;

    public BusinessCategoryQueryService(BusinessCategoryRepository businessCategoryRepository, BusinessCategoryMapper businessCategoryMapper) {
        this.businessCategoryRepository = businessCategoryRepository;
        this.businessCategoryMapper = businessCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link BusinessCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessCategoryDTO> findByCriteria(BusinessCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BusinessCategory> specification = createSpecification(criteria);
        return businessCategoryMapper.toDto(businessCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusinessCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessCategoryDTO> findByCriteria(BusinessCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BusinessCategory> specification = createSpecification(criteria);
        return businessCategoryRepository.findAll(specification, page)
            .map(businessCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusinessCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BusinessCategory> specification = createSpecification(criteria);
        return businessCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link BusinessCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BusinessCategory> createSpecification(BusinessCategoryCriteria criteria) {
        Specification<BusinessCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BusinessCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BusinessCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), BusinessCategory_.description));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(BusinessCategory_.businessCategories, JoinType.LEFT).get(BusinessCategory_.id)));
            }
            if (criteria.getDocumentTypeBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeBusinessCategoryId(),
                    root -> root.join(BusinessCategory_.documentTypeBusinessCategories, JoinType.LEFT).get(DocumentTypeBusinessCategory_.id)));
            }
            if (criteria.getParentCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentCategoryId(),
                    root -> root.join(BusinessCategory_.parentCategory, JoinType.LEFT).get(BusinessCategory_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(BusinessCategory_.vendors, JoinType.LEFT).get(Vendor_.id)));
            }
        }
        return specification;
    }
}
