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

import com.bhp.opusb.domain.CClauseLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CClauseLineRepository;
import com.bhp.opusb.service.dto.CClauseLineCriteria;
import com.bhp.opusb.service.dto.CClauseLineDTO;
import com.bhp.opusb.service.mapper.CClauseLineMapper;

/**
 * Service for executing complex queries for {@link CClauseLine} entities in the database.
 * The main input is a {@link CClauseLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CClauseLineDTO} or a {@link Page} of {@link CClauseLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CClauseLineQueryService extends QueryService<CClauseLine> {

    private final Logger log = LoggerFactory.getLogger(CClauseLineQueryService.class);

    private final CClauseLineRepository cClauseLineRepository;

    private final CClauseLineMapper cClauseLineMapper;

    public CClauseLineQueryService(CClauseLineRepository cClauseLineRepository, CClauseLineMapper cClauseLineMapper) {
        this.cClauseLineRepository = cClauseLineRepository;
        this.cClauseLineMapper = cClauseLineMapper;
    }

    /**
     * Return a {@link List} of {@link CClauseLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CClauseLineDTO> findByCriteria(CClauseLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CClauseLine> specification = createSpecification(criteria);
        return cClauseLineMapper.toDto(cClauseLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CClauseLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CClauseLineDTO> findByCriteria(CClauseLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CClauseLine> specification = createSpecification(criteria);
        return cClauseLineRepository.findAll(specification, page)
            .map(cClauseLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CClauseLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CClauseLine> specification = createSpecification(criteria);
        return cClauseLineRepository.count(specification);
    }

    /**
     * Function to convert {@link CClauseLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CClauseLine> createSpecification(CClauseLineCriteria criteria) {
        Specification<CClauseLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CClauseLine_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CClauseLine_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CClauseLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CClauseLine_.active));
            }
            if (criteria.getClauseId() != null) {
                specification = specification.and(buildSpecification(criteria.getClauseId(),
                    root -> root.join(CClauseLine_.clause, JoinType.LEFT).get(CClause_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CClauseLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
