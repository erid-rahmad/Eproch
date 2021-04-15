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

import com.bhp.opusb.domain.CEvalMethodCriteriaLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEvalMethodCriteriaLineRepository;
import com.bhp.opusb.service.dto.CEvalMethodCriteriaLineCriteria;
import com.bhp.opusb.service.dto.CEvalMethodCriteriaLineDTO;
import com.bhp.opusb.service.mapper.CEvalMethodCriteriaLineMapper;

/**
 * Service for executing complex queries for {@link CEvalMethodCriteriaLine} entities in the database.
 * The main input is a {@link CEvalMethodCriteriaLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEvalMethodCriteriaLineDTO} or a {@link Page} of {@link CEvalMethodCriteriaLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEvalMethodCriteriaLineQueryService extends QueryService<CEvalMethodCriteriaLine> {

    private final Logger log = LoggerFactory.getLogger(CEvalMethodCriteriaLineQueryService.class);

    private final CEvalMethodCriteriaLineRepository cEvalMethodCriteriaLineRepository;

    private final CEvalMethodCriteriaLineMapper cEvalMethodCriteriaLineMapper;

    public CEvalMethodCriteriaLineQueryService(CEvalMethodCriteriaLineRepository cEvalMethodCriteriaLineRepository, CEvalMethodCriteriaLineMapper cEvalMethodCriteriaLineMapper) {
        this.cEvalMethodCriteriaLineRepository = cEvalMethodCriteriaLineRepository;
        this.cEvalMethodCriteriaLineMapper = cEvalMethodCriteriaLineMapper;
    }

    /**
     * Return a {@link List} of {@link CEvalMethodCriteriaLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEvalMethodCriteriaLineDTO> findByCriteria(CEvalMethodCriteriaLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEvalMethodCriteriaLine> specification = createSpecification(criteria);
        return cEvalMethodCriteriaLineMapper.toDto(cEvalMethodCriteriaLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEvalMethodCriteriaLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvalMethodCriteriaLineDTO> findByCriteria(CEvalMethodCriteriaLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CEvalMethodCriteriaLine> specification = createSpecification(criteria);
        return cEvalMethodCriteriaLineRepository.findAll(specification, page)
            .map(cEvalMethodCriteriaLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEvalMethodCriteriaLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEvalMethodCriteriaLine> specification = createSpecification(criteria);
        return cEvalMethodCriteriaLineRepository.count(specification);
    }

    /**
     * Function to convert {@link CEvalMethodCriteriaLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEvalMethodCriteriaLine> createSpecification(CEvalMethodCriteriaLineCriteria criteria) {
        Specification<CEvalMethodCriteriaLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEvalMethodCriteriaLine_.id));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), CEvalMethodCriteriaLine_.weight));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEvalMethodCriteriaLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEvalMethodCriteriaLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEvalMethodCriteriaLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingCriteriaId(),
                    root -> root.join(CEvalMethodCriteriaLine_.biddingCriteria, JoinType.LEFT).get(CBiddingCriteria_.id)));
            }
        }
        return specification;
    }
}
