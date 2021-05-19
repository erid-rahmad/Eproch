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

import com.bhp.opusb.domain.MBiddingResult;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingResultRepository;
import com.bhp.opusb.service.dto.MBiddingResultCriteria;
import com.bhp.opusb.service.dto.MBiddingResultDTO;
import com.bhp.opusb.service.mapper.MBiddingResultMapper;

/**
 * Service for executing complex queries for {@link MBiddingResult} entities in the database.
 * The main input is a {@link MBiddingResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingResultDTO} or a {@link Page} of {@link MBiddingResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingResultQueryService extends QueryService<MBiddingResult> {

    private final Logger log = LoggerFactory.getLogger(MBiddingResultQueryService.class);

    private final MBiddingResultRepository mBiddingResultRepository;

    private final MBiddingResultMapper mBiddingResultMapper;

    public MBiddingResultQueryService(MBiddingResultRepository mBiddingResultRepository, MBiddingResultMapper mBiddingResultMapper) {
        this.mBiddingResultRepository = mBiddingResultRepository;
        this.mBiddingResultMapper = mBiddingResultMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingResultDTO> findByCriteria(MBiddingResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingResult> specification = createSpecification(criteria);
        return mBiddingResultMapper.toDto(mBiddingResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingResultDTO> findByCriteria(MBiddingResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingResult> specification = createSpecification(criteria);
        return mBiddingResultRepository.findAll(specification, page)
            .map(mBiddingResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingResult> specification = createSpecification(criteria);
        return mBiddingResultRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingResult> createSpecification(MBiddingResultCriteria criteria) {
        Specification<MBiddingResult> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingResult_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingResult_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingResult_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingResult_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAnnouncementResultId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnnouncementResultId(),
                    root -> root.join(MBiddingResult_.announcementResult, JoinType.LEFT).get(CAnnouncementResult_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MBiddingResult_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MBiddingResult_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getBiddingEvalResultId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingEvalResultId(),
                    root -> root.join(MBiddingResult_.biddingEvalResult, JoinType.LEFT).get(MBiddingEvalResult_.id)));
            }
        }
        return specification;
    }
}
