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

import com.bhp.opusb.domain.MBiddingSubmissionLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingSubmissionLineRepository;
import com.bhp.opusb.service.dto.MBiddingSubmissionLineCriteria;
import com.bhp.opusb.service.dto.MBiddingSubmissionLineDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionLineMapper;

/**
 * Service for executing complex queries for {@link MBiddingSubmissionLine} entities in the database.
 * The main input is a {@link MBiddingSubmissionLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingSubmissionLineDTO} or a {@link Page} of {@link MBiddingSubmissionLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingSubmissionLineQueryService extends QueryService<MBiddingSubmissionLine> {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubmissionLineQueryService.class);

    private final MBiddingSubmissionLineRepository mBiddingSubmissionLineRepository;

    private final MBiddingSubmissionLineMapper mBiddingSubmissionLineMapper;

    public MBiddingSubmissionLineQueryService(MBiddingSubmissionLineRepository mBiddingSubmissionLineRepository, MBiddingSubmissionLineMapper mBiddingSubmissionLineMapper) {
        this.mBiddingSubmissionLineRepository = mBiddingSubmissionLineRepository;
        this.mBiddingSubmissionLineMapper = mBiddingSubmissionLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingSubmissionLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingSubmissionLineDTO> findByCriteria(MBiddingSubmissionLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingSubmissionLine> specification = createSpecification(criteria);
        return mBiddingSubmissionLineMapper.toDto(mBiddingSubmissionLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingSubmissionLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubmissionLineDTO> findByCriteria(MBiddingSubmissionLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingSubmissionLine> specification = createSpecification(criteria);
        return mBiddingSubmissionLineRepository.findAll(specification, page)
            .map(mBiddingSubmissionLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingSubmissionLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingSubmissionLine> specification = createSpecification(criteria);
        return mBiddingSubmissionLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingSubmissionLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingSubmissionLine> createSpecification(MBiddingSubmissionLineCriteria criteria) {
        Specification<MBiddingSubmissionLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingSubmissionLine_.id));
            }
            if (criteria.getProposedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProposedPrice(), MBiddingSubmissionLine_.proposedPrice));
            }
            if (criteria.getTotalPriceSubmission() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPriceSubmission(), MBiddingSubmissionLine_.totalPriceSubmission));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), MBiddingSubmissionLine_.deliveryDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingSubmissionLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingSubmissionLine_.active));
            }
            if (criteria.getMSubmissionSubItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getMSubmissionSubItemId(),
                    root -> root.join(MBiddingSubmissionLine_.mSubmissionSubItems, JoinType.LEFT).get(MSubmissionSubItem_.id)));
            }
            if (criteria.getBiddingLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingLineId(),
                    root -> root.join(MBiddingSubmissionLine_.biddingLine, JoinType.LEFT).get(MBiddingLine_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingSubmissionLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getMBiddingSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getMBiddingSubmissionId(),
                    root -> root.join(MBiddingSubmissionLine_.mBiddingSubmission, JoinType.LEFT).get(MBiddingSubmission_.id)));
            }
        }
        return specification;
    }
}
