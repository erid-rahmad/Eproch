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

import com.bhp.opusb.domain.MAuctionSubmissionItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionSubmissionItemRepository;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemCriteria;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionItemMapper;

/**
 * Service for executing complex queries for {@link MAuctionSubmissionItem} entities in the database.
 * The main input is a {@link MAuctionSubmissionItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionSubmissionItemDTO} or a {@link Page} of {@link MAuctionSubmissionItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionSubmissionItemQueryService extends QueryService<MAuctionSubmissionItem> {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionItemQueryService.class);

    private final MAuctionSubmissionItemRepository mAuctionSubmissionItemRepository;

    private final MAuctionSubmissionItemMapper mAuctionSubmissionItemMapper;

    public MAuctionSubmissionItemQueryService(MAuctionSubmissionItemRepository mAuctionSubmissionItemRepository, MAuctionSubmissionItemMapper mAuctionSubmissionItemMapper) {
        this.mAuctionSubmissionItemRepository = mAuctionSubmissionItemRepository;
        this.mAuctionSubmissionItemMapper = mAuctionSubmissionItemMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionSubmissionItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionSubmissionItemDTO> findByCriteria(MAuctionSubmissionItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionSubmissionItem> specification = createSpecification(criteria);
        return mAuctionSubmissionItemMapper.toDto(mAuctionSubmissionItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionSubmissionItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionSubmissionItemDTO> findByCriteria(MAuctionSubmissionItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionSubmissionItem> specification = createSpecification(criteria);
        return mAuctionSubmissionItemRepository.findAll(specification, page)
            .map(mAuctionSubmissionItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionSubmissionItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionSubmissionItem> specification = createSpecification(criteria);
        return mAuctionSubmissionItemRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionSubmissionItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionSubmissionItem> createSpecification(MAuctionSubmissionItemCriteria criteria) {
        Specification<MAuctionSubmissionItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionSubmissionItem_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), MAuctionSubmissionItem_.price));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuctionSubmissionItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuctionSubmissionItem_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuctionSubmissionItem_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAuctionSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionSubmissionId(),
                    root -> root.join(MAuctionSubmissionItem_.auctionSubmission, JoinType.LEFT).get(MAuctionSubmission_.id)));
            }
            if (criteria.getAuctionItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionItemId(),
                    root -> root.join(MAuctionSubmissionItem_.auctionItem, JoinType.INNER).get(MAuctionItem_.id)));
            }
            if (criteria.getAuctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionId(),
                    root -> root.join(MAuctionSubmissionItem_.auctionItem, JoinType.INNER)
                        .join(MAuctionItem_.auction, JoinType.INNER)
                        .get(MAuction_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MAuctionSubmissionItem_.auctionSubmission, JoinType.INNER)
                        .join(MAuctionSubmission_.vendor, JoinType.INNER)
                        .get(CVendor_.id)));
            }
        }
        return specification;
    }
}
