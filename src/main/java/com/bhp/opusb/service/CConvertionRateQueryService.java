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

import com.bhp.opusb.domain.CConvertionRate;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CConvertionRateRepository;
import com.bhp.opusb.service.dto.CConvertionRateCriteria;
import com.bhp.opusb.service.dto.CConvertionRateDTO;
import com.bhp.opusb.service.mapper.CConvertionRateMapper;

/**
 * Service for executing complex queries for {@link CConvertionRate} entities in the database.
 * The main input is a {@link CConvertionRateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CConvertionRateDTO} or a {@link Page} of {@link CConvertionRateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CConvertionRateQueryService extends QueryService<CConvertionRate> {

    private final Logger log = LoggerFactory.getLogger(CConvertionRateQueryService.class);

    private final CConvertionRateRepository cConvertionRateRepository;

    private final CConvertionRateMapper cConvertionRateMapper;

    public CConvertionRateQueryService(CConvertionRateRepository cConvertionRateRepository, CConvertionRateMapper cConvertionRateMapper) {
        this.cConvertionRateRepository = cConvertionRateRepository;
        this.cConvertionRateMapper = cConvertionRateMapper;
    }

    /**
     * Return a {@link List} of {@link CConvertionRateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CConvertionRateDTO> findByCriteria(CConvertionRateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CConvertionRate> specification = createSpecification(criteria);
        return cConvertionRateMapper.toDto(cConvertionRateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CConvertionRateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CConvertionRateDTO> findByCriteria(CConvertionRateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CConvertionRate> specification = createSpecification(criteria);
        return cConvertionRateRepository.findAll(specification, page)
            .map(cConvertionRateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CConvertionRateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CConvertionRate> specification = createSpecification(criteria);
        return cConvertionRateRepository.count(specification);
    }

    /**
     * Function to convert {@link CConvertionRateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CConvertionRate> createSpecification(CConvertionRateCriteria criteria) {
        Specification<CConvertionRate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CConvertionRate_.id));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), CConvertionRate_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), CConvertionRate_.validTo));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), CConvertionRate_.rate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CConvertionRate_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CConvertionRate_.active));
            }
            if (criteria.getSourceCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceCurrencyId(),
                    root -> root.join(CConvertionRate_.sourceCurrency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getTargetCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getTargetCurrencyId(),
                    root -> root.join(CConvertionRate_.targetCurrency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getConvertionTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getConvertionTypeId(),
                    root -> root.join(CConvertionRate_.convertionType, JoinType.LEFT).get(CConvertionType_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CConvertionRate_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
