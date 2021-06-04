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

import com.bhp.opusb.domain.MBiddingNegotiationLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingNegotiationLineRepository;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineCriteria;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationLineMapper;

/**
 * Service for executing complex queries for {@link MBiddingNegotiationLine} entities in the database.
 * The main input is a {@link MBiddingNegotiationLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingNegotiationLineDTO} or a {@link Page} of {@link MBiddingNegotiationLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingNegotiationLineQueryService extends QueryService<MBiddingNegotiationLine> {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationLineQueryService.class);

    private final MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository;

    private final MBiddingNegotiationLineMapper mBiddingNegotiationLineMapper;

    public MBiddingNegotiationLineQueryService(MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository, MBiddingNegotiationLineMapper mBiddingNegotiationLineMapper) {
        this.mBiddingNegotiationLineRepository = mBiddingNegotiationLineRepository;
        this.mBiddingNegotiationLineMapper = mBiddingNegotiationLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingNegotiationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingNegotiationLineDTO> findByCriteria(MBiddingNegotiationLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingNegotiationLine> specification = createSpecification(criteria);
        return mBiddingNegotiationLineMapper.toDto(mBiddingNegotiationLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingNegotiationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingNegotiationLineDTO> findByCriteria(MBiddingNegotiationLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingNegotiationLine> specification = createSpecification(criteria);
        return mBiddingNegotiationLineRepository.findAll(specification, page)
            .map(mBiddingNegotiationLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingNegotiationLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingNegotiationLine> specification = createSpecification(criteria);
        return mBiddingNegotiationLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingNegotiationLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingNegotiationLine> createSpecification(MBiddingNegotiationLineCriteria criteria) {
        Specification<MBiddingNegotiationLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingNegotiationLine_.id));
            }
            if (criteria.getNegotiationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNegotiationStatus(), MBiddingNegotiationLine_.negotiationStatus));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingNegotiationLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingNegotiationLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingNegotiationLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getNegotiationId() != null) {
                specification = specification.and(buildSpecification(criteria.getNegotiationId(),
                    root -> root.join(MBiddingNegotiationLine_.negotiation, JoinType.LEFT).get(MBiddingNegotiation_.id)));
            }
            if (criteria.getBiddingEvalResultId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingEvalResultId(),
                    root -> root.join(MBiddingNegotiationLine_.biddingEvalResult, JoinType.LEFT).get(MBiddingEvalResult_.id)));
            }
        }
        return specification;
    }
}
