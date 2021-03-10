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

import com.bhp.opusb.domain.MSubmissionSubItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MSubmissionSubItemRepository;
import com.bhp.opusb.service.dto.MSubmissionSubItemCriteria;
import com.bhp.opusb.service.dto.MSubmissionSubItemDTO;
import com.bhp.opusb.service.mapper.MSubmissionSubItemMapper;

/**
 * Service for executing complex queries for {@link MSubmissionSubItem} entities in the database.
 * The main input is a {@link MSubmissionSubItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MSubmissionSubItemDTO} or a {@link Page} of {@link MSubmissionSubItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MSubmissionSubItemQueryService extends QueryService<MSubmissionSubItem> {

    private final Logger log = LoggerFactory.getLogger(MSubmissionSubItemQueryService.class);

    private final MSubmissionSubItemRepository mSubmissionSubItemRepository;

    private final MSubmissionSubItemMapper mSubmissionSubItemMapper;

    public MSubmissionSubItemQueryService(MSubmissionSubItemRepository mSubmissionSubItemRepository, MSubmissionSubItemMapper mSubmissionSubItemMapper) {
        this.mSubmissionSubItemRepository = mSubmissionSubItemRepository;
        this.mSubmissionSubItemMapper = mSubmissionSubItemMapper;
    }

    /**
     * Return a {@link List} of {@link MSubmissionSubItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MSubmissionSubItemDTO> findByCriteria(MSubmissionSubItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MSubmissionSubItem> specification = createSpecification(criteria);
        return mSubmissionSubItemMapper.toDto(mSubmissionSubItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MSubmissionSubItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MSubmissionSubItemDTO> findByCriteria(MSubmissionSubItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MSubmissionSubItem> specification = createSpecification(criteria);
        return mSubmissionSubItemRepository.findAll(specification, page)
            .map(mSubmissionSubItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MSubmissionSubItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MSubmissionSubItem> specification = createSpecification(criteria);
        return mSubmissionSubItemRepository.count(specification);
    }

    /**
     * Function to convert {@link MSubmissionSubItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MSubmissionSubItem> createSpecification(MSubmissionSubItemCriteria criteria) {
        Specification<MSubmissionSubItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MSubmissionSubItem_.id));
            }
            if (criteria.getProposedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProposedPrice(), MSubmissionSubItem_.proposedPrice));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MSubmissionSubItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MSubmissionSubItem_.active));
            }
            if (criteria.getBiddingSubItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubItemId(),
                    root -> root.join(MSubmissionSubItem_.biddingSubItem, JoinType.LEFT).get(MBiddingSubItem_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MSubmissionSubItem_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getMBiddingSubmissionLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getMBiddingSubmissionLineId(),
                    root -> root.join(MSubmissionSubItem_.mBiddingSubmissionLine, JoinType.LEFT).get(MBiddingSubmissionLine_.id)));
            }
        }
        return specification;
    }
}
