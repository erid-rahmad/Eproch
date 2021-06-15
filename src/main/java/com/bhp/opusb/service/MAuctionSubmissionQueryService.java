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

import com.bhp.opusb.domain.MAuctionSubmission;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionSubmissionRepository;
import com.bhp.opusb.service.dto.MAuctionSubmissionCriteria;
import com.bhp.opusb.service.dto.MAuctionSubmissionDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionMapper;

/**
 * Service for executing complex queries for {@link MAuctionSubmission} entities in the database.
 * The main input is a {@link MAuctionSubmissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionSubmissionDTO} or a {@link Page} of {@link MAuctionSubmissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionSubmissionQueryService extends QueryService<MAuctionSubmission> {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionQueryService.class);

    private final MAuctionSubmissionRepository mAuctionSubmissionRepository;

    private final MAuctionSubmissionMapper mAuctionSubmissionMapper;

    public MAuctionSubmissionQueryService(MAuctionSubmissionRepository mAuctionSubmissionRepository, MAuctionSubmissionMapper mAuctionSubmissionMapper) {
        this.mAuctionSubmissionRepository = mAuctionSubmissionRepository;
        this.mAuctionSubmissionMapper = mAuctionSubmissionMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionSubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionSubmissionDTO> findByCriteria(MAuctionSubmissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionSubmission> specification = createSpecification(criteria);
        return mAuctionSubmissionMapper.toDto(mAuctionSubmissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionSubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionSubmissionDTO> findByCriteria(MAuctionSubmissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionSubmission> specification = createSpecification(criteria);
        return mAuctionSubmissionRepository.findAll(specification, page)
            .map(mAuctionSubmissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionSubmissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionSubmission> specification = createSpecification(criteria);
        return mAuctionSubmissionRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionSubmissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionSubmission> createSpecification(MAuctionSubmissionCriteria criteria) {
        Specification<MAuctionSubmission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionSubmission_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), MAuctionSubmission_.price));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuctionSubmission_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuctionSubmission_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuctionSubmission_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAuctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionId(),
                    root -> root.join(MAuctionSubmission_.auctionItem, JoinType.LEFT)
                        .join(MAuctionItem_.auction)
                        .get(MAuction_.id)));
            }
            if (criteria.getAuctionItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionItemId(),
                    root -> root.join(MAuctionSubmission_.auctionItem, JoinType.LEFT).get(MAuctionItem_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MAuctionSubmission_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
