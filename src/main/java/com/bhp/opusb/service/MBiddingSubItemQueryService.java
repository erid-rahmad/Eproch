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

import com.bhp.opusb.domain.MBiddingSubItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingSubItemRepository;
import com.bhp.opusb.service.dto.MBiddingSubItemCriteria;
import com.bhp.opusb.service.dto.MBiddingSubItemDTO;
import com.bhp.opusb.service.mapper.MBiddingSubItemMapper;

/**
 * Service for executing complex queries for {@link MBiddingSubItem} entities in the database.
 * The main input is a {@link MBiddingSubItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingSubItemDTO} or a {@link Page} of {@link MBiddingSubItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingSubItemQueryService extends QueryService<MBiddingSubItem> {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubItemQueryService.class);

    private final MBiddingSubItemRepository mBiddingSubItemRepository;

    private final MBiddingSubItemMapper mBiddingSubItemMapper;

    public MBiddingSubItemQueryService(MBiddingSubItemRepository mBiddingSubItemRepository, MBiddingSubItemMapper mBiddingSubItemMapper) {
        this.mBiddingSubItemRepository = mBiddingSubItemRepository;
        this.mBiddingSubItemMapper = mBiddingSubItemMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingSubItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingSubItemDTO> findByCriteria(MBiddingSubItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingSubItem> specification = createSpecification(criteria);
        return mBiddingSubItemMapper.toDto(mBiddingSubItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingSubItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubItemDTO> findByCriteria(MBiddingSubItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingSubItem> specification = createSpecification(criteria);
        return mBiddingSubItemRepository.findAll(specification, page)
            .map(mBiddingSubItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingSubItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingSubItem> specification = createSpecification(criteria);
        return mBiddingSubItemRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingSubItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingSubItem> createSpecification(MBiddingSubItemCriteria criteria) {
        Specification<MBiddingSubItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingSubItem_.id));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), MBiddingSubItem_.totalAmount));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingSubItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingSubItem_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingSubItem_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingLineId(),
                    root -> root.join(MBiddingSubItem_.biddingLine, JoinType.LEFT).get(MBiddingLine_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MBiddingSubItem_.product, JoinType.LEFT).get(CProduct_.id)));
            }
        }
        return specification;
    }
}
