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

import com.bhp.opusb.domain.CClause;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CClauseRepository;
import com.bhp.opusb.service.dto.CClauseCriteria;
import com.bhp.opusb.service.dto.CClauseDTO;
import com.bhp.opusb.service.mapper.CClauseMapper;

/**
 * Service for executing complex queries for {@link CClause} entities in the database.
 * The main input is a {@link CClauseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CClauseDTO} or a {@link Page} of {@link CClauseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CClauseQueryService extends QueryService<CClause> {

    private final Logger log = LoggerFactory.getLogger(CClauseQueryService.class);

    private final CClauseRepository cClauseRepository;

    private final CClauseMapper cClauseMapper;

    public CClauseQueryService(CClauseRepository cClauseRepository, CClauseMapper cClauseMapper) {
        this.cClauseRepository = cClauseRepository;
        this.cClauseMapper = cClauseMapper;
    }

    /**
     * Return a {@link List} of {@link CClauseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CClauseDTO> findByCriteria(CClauseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CClause> specification = createSpecification(criteria);
        return cClauseMapper.toDto(cClauseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CClauseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CClauseDTO> findByCriteria(CClauseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CClause> specification = createSpecification(criteria);
        return cClauseRepository.findAll(specification, page)
            .map(cClauseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CClauseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CClause> specification = createSpecification(criteria);
        return cClauseRepository.count(specification);
    }

    /**
     * Function to convert {@link CClauseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CClause> createSpecification(CClauseCriteria criteria) {
        Specification<CClause> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CClause_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CClause_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CClause_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CClause_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CClause_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
