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

import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CTaxCategoryRepository;
import com.bhp.opusb.service.dto.CTaxCategoryCriteria;
import com.bhp.opusb.service.dto.CTaxCategoryDTO;
import com.bhp.opusb.service.mapper.CTaxCategoryMapper;

/**
 * Service for executing complex queries for {@link CTaxCategory} entities in the database.
 * The main input is a {@link CTaxCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CTaxCategoryDTO} or a {@link Page} of {@link CTaxCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CTaxCategoryQueryService extends QueryService<CTaxCategory> {

    private final Logger log = LoggerFactory.getLogger(CTaxCategoryQueryService.class);

    private final CTaxCategoryRepository cTaxCategoryRepository;

    private final CTaxCategoryMapper cTaxCategoryMapper;

    public CTaxCategoryQueryService(CTaxCategoryRepository cTaxCategoryRepository, CTaxCategoryMapper cTaxCategoryMapper) {
        this.cTaxCategoryRepository = cTaxCategoryRepository;
        this.cTaxCategoryMapper = cTaxCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link CTaxCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CTaxCategoryDTO> findByCriteria(CTaxCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CTaxCategory> specification = createSpecification(criteria);
        return cTaxCategoryMapper.toDto(cTaxCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CTaxCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaxCategoryDTO> findByCriteria(CTaxCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CTaxCategory> specification = createSpecification(criteria);
        return cTaxCategoryRepository.findAll(specification, page)
            .map(cTaxCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CTaxCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CTaxCategory> specification = createSpecification(criteria);
        return cTaxCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CTaxCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CTaxCategory> createSpecification(CTaxCategoryCriteria criteria) {
        Specification<CTaxCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CTaxCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CTaxCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CTaxCategory_.description));
            }
            if (criteria.getIsWithholding() != null) {
                specification = specification.and(buildSpecification(criteria.getIsWithholding(), CTaxCategory_.isWithholding));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CTaxCategory_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CTaxCategory_.active));
            }
            if (criteria.getCTaxRateId() != null) {
                specification = specification.and(buildSpecification(criteria.getCTaxRateId(),
                    root -> root.join(CTaxCategory_.cTaxRates, JoinType.LEFT).get(CTaxRate_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CTaxCategory_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
