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

import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CCurrencyRepository;
import com.bhp.opusb.service.dto.CCurrencyCriteria;
import com.bhp.opusb.service.dto.CCurrencyDTO;
import com.bhp.opusb.service.mapper.CCurrencyMapper;

/**
 * Service for executing complex queries for {@link CCurrency} entities in the database.
 * The main input is a {@link CCurrencyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CCurrencyDTO} or a {@link Page} of {@link CCurrencyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CCurrencyQueryService extends QueryService<CCurrency> {

    private final Logger log = LoggerFactory.getLogger(CCurrencyQueryService.class);

    private final CCurrencyRepository cCurrencyRepository;

    private final CCurrencyMapper cCurrencyMapper;

    public CCurrencyQueryService(CCurrencyRepository cCurrencyRepository, CCurrencyMapper cCurrencyMapper) {
        this.cCurrencyRepository = cCurrencyRepository;
        this.cCurrencyMapper = cCurrencyMapper;
    }

    /**
     * Return a {@link List} of {@link CCurrencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CCurrencyDTO> findByCriteria(CCurrencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CCurrency> specification = createSpecification(criteria);
        return cCurrencyMapper.toDto(cCurrencyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CCurrencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CCurrencyDTO> findByCriteria(CCurrencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CCurrency> specification = createSpecification(criteria);
        return cCurrencyRepository.findAll(specification, page)
            .map(cCurrencyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CCurrencyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CCurrency> specification = createSpecification(criteria);
        return cCurrencyRepository.count(specification);
    }

    /**
     * Function to convert {@link CCurrencyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CCurrency> createSpecification(CCurrencyCriteria criteria) {
        Specification<CCurrency> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CCurrency_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CCurrency_.code));
            }
            if (criteria.getSymbol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymbol(), CCurrency_.symbol));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CCurrency_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CCurrency_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CCurrency_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CCurrency_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
