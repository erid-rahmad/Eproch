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

import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CTaxRepository;
import com.bhp.opusb.service.dto.CTaxCriteria;
import com.bhp.opusb.service.dto.CTaxDTO;
import com.bhp.opusb.service.mapper.CTaxMapper;

/**
 * Service for executing complex queries for {@link CTax} entities in the database.
 * The main input is a {@link CTaxCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CTaxDTO} or a {@link Page} of {@link CTaxDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CTaxQueryService extends QueryService<CTax> {

    private final Logger log = LoggerFactory.getLogger(CTaxQueryService.class);

    private final CTaxRepository cTaxRepository;

    private final CTaxMapper cTaxMapper;

    public CTaxQueryService(CTaxRepository cTaxRepository, CTaxMapper cTaxMapper) {
        this.cTaxRepository = cTaxRepository;
        this.cTaxMapper = cTaxMapper;
    }

    /**
     * Return a {@link List} of {@link CTaxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CTaxDTO> findByCriteria(CTaxCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CTax> specification = createSpecification(criteria);
        return cTaxMapper.toDto(cTaxRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CTaxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaxDTO> findByCriteria(CTaxCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CTax> specification = createSpecification(criteria);
        return cTaxRepository.findAll(specification, page)
            .map(cTaxMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CTaxCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CTax> specification = createSpecification(criteria);
        return cTaxRepository.count(specification);
    }

    /**
     * Function to convert {@link CTaxCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CTax> createSpecification(CTaxCriteria criteria) {
        Specification<CTax> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CTax_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CTax_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CTax_.description));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), CTax_.rate));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), CTax_.validFrom));
            }
            if (criteria.getTransactionType() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionType(), CTax_.transactionType));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CTax_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CTax_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CTax_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getTaxCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxCategoryId(),
                    root -> root.join(CTax_.taxCategory, JoinType.LEFT).get(CTaxCategory_.id)));
            }
        }
        return specification;
    }
}
