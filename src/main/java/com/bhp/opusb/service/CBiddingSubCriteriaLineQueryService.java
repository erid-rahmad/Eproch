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

import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CBiddingSubCriteriaLineRepository;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaLineCriteria;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaLineDTO;
import com.bhp.opusb.service.mapper.CBiddingSubCriteriaLineMapper;

/**
 * Service for executing complex queries for {@link CBiddingSubCriteriaLine} entities in the database.
 * The main input is a {@link CBiddingSubCriteriaLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CBiddingSubCriteriaLineDTO} or a {@link Page} of {@link CBiddingSubCriteriaLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CBiddingSubCriteriaLineQueryService extends QueryService<CBiddingSubCriteriaLine> {

    private final Logger log = LoggerFactory.getLogger(CBiddingSubCriteriaLineQueryService.class);

    private final CBiddingSubCriteriaLineRepository cBiddingSubCriteriaLineRepository;

    private final CBiddingSubCriteriaLineMapper cBiddingSubCriteriaLineMapper;

    public CBiddingSubCriteriaLineQueryService(CBiddingSubCriteriaLineRepository cBiddingSubCriteriaLineRepository, CBiddingSubCriteriaLineMapper cBiddingSubCriteriaLineMapper) {
        this.cBiddingSubCriteriaLineRepository = cBiddingSubCriteriaLineRepository;
        this.cBiddingSubCriteriaLineMapper = cBiddingSubCriteriaLineMapper;
    }

    /**
     * Return a {@link List} of {@link CBiddingSubCriteriaLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CBiddingSubCriteriaLineDTO> findByCriteria(CBiddingSubCriteriaLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CBiddingSubCriteriaLine> specification = createSpecification(criteria);
        return cBiddingSubCriteriaLineMapper.toDto(cBiddingSubCriteriaLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CBiddingSubCriteriaLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CBiddingSubCriteriaLineDTO> findByCriteria(CBiddingSubCriteriaLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CBiddingSubCriteriaLine> specification = createSpecification(criteria);
        return cBiddingSubCriteriaLineRepository.findAll(specification, page)
            .map(cBiddingSubCriteriaLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CBiddingSubCriteriaLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CBiddingSubCriteriaLine> specification = createSpecification(criteria);
        return cBiddingSubCriteriaLineRepository.count(specification);
    }

    /**
     * Function to convert {@link CBiddingSubCriteriaLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CBiddingSubCriteriaLine> createSpecification(CBiddingSubCriteriaLineCriteria criteria) {
        Specification<CBiddingSubCriteriaLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CBiddingSubCriteriaLine_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CBiddingSubCriteriaLine_.name));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), CBiddingSubCriteriaLine_.score));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CBiddingSubCriteriaLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CBiddingSubCriteriaLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CBiddingSubCriteriaLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaId(),
                    root -> root.join(CBiddingSubCriteriaLine_.biddingSubCriteria, JoinType.LEFT).get(CBiddingSubCriteria_.id)));
            }
        }
        return specification;
    }
}
