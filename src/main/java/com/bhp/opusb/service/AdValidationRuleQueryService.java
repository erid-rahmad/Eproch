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

import com.bhp.opusb.domain.AdValidationRule;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdValidationRuleRepository;
import com.bhp.opusb.service.dto.AdValidationRuleCriteria;
import com.bhp.opusb.service.dto.AdValidationRuleDTO;
import com.bhp.opusb.service.mapper.AdValidationRuleMapper;

/**
 * Service for executing complex queries for {@link AdValidationRule} entities in the database.
 * The main input is a {@link AdValidationRuleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdValidationRuleDTO} or a {@link Page} of {@link AdValidationRuleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdValidationRuleQueryService extends QueryService<AdValidationRule> {

    private final Logger log = LoggerFactory.getLogger(AdValidationRuleQueryService.class);

    private final AdValidationRuleRepository adValidationRuleRepository;

    private final AdValidationRuleMapper adValidationRuleMapper;

    public AdValidationRuleQueryService(AdValidationRuleRepository adValidationRuleRepository, AdValidationRuleMapper adValidationRuleMapper) {
        this.adValidationRuleRepository = adValidationRuleRepository;
        this.adValidationRuleMapper = adValidationRuleMapper;
    }

    /**
     * Return a {@link List} of {@link AdValidationRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdValidationRuleDTO> findByCriteria(AdValidationRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdValidationRule> specification = createSpecification(criteria);
        return adValidationRuleMapper.toDto(adValidationRuleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdValidationRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdValidationRuleDTO> findByCriteria(AdValidationRuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdValidationRule> specification = createSpecification(criteria);
        return adValidationRuleRepository.findAll(specification, page)
            .map(adValidationRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdValidationRuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdValidationRule> specification = createSpecification(criteria);
        return adValidationRuleRepository.count(specification);
    }

    /**
     * Function to convert {@link AdValidationRuleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdValidationRule> createSpecification(AdValidationRuleCriteria criteria) {
        Specification<AdValidationRule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdValidationRule_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdValidationRule_.uid));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdValidationRule_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdValidationRule_.description));
            }
            if (criteria.getQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuery(), AdValidationRule_.query));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdValidationRule_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdValidationRule_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
