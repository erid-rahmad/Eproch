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

import com.bhp.opusb.domain.MProposalPriceSubItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProposalPriceSubItemRepository;
import com.bhp.opusb.service.dto.MProposalPriceSubItemCriteria;
import com.bhp.opusb.service.dto.MProposalPriceSubItemDTO;
import com.bhp.opusb.service.mapper.MProposalPriceSubItemMapper;

/**
 * Service for executing complex queries for {@link MProposalPriceSubItem} entities in the database.
 * The main input is a {@link MProposalPriceSubItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProposalPriceSubItemDTO} or a {@link Page} of {@link MProposalPriceSubItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProposalPriceSubItemQueryService extends QueryService<MProposalPriceSubItem> {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceSubItemQueryService.class);

    private final MProposalPriceSubItemRepository mProposalPriceSubItemRepository;

    private final MProposalPriceSubItemMapper mProposalPriceSubItemMapper;

    public MProposalPriceSubItemQueryService(MProposalPriceSubItemRepository mProposalPriceSubItemRepository, MProposalPriceSubItemMapper mProposalPriceSubItemMapper) {
        this.mProposalPriceSubItemRepository = mProposalPriceSubItemRepository;
        this.mProposalPriceSubItemMapper = mProposalPriceSubItemMapper;
    }

    /**
     * Return a {@link List} of {@link MProposalPriceSubItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProposalPriceSubItemDTO> findByCriteria(MProposalPriceSubItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProposalPriceSubItem> specification = createSpecification(criteria);
        return mProposalPriceSubItemMapper.toDto(mProposalPriceSubItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProposalPriceSubItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalPriceSubItemDTO> findByCriteria(MProposalPriceSubItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProposalPriceSubItem> specification = createSpecification(criteria);
        return mProposalPriceSubItemRepository.findAll(specification, page)
            .map(mProposalPriceSubItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProposalPriceSubItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProposalPriceSubItem> specification = createSpecification(criteria);
        return mProposalPriceSubItemRepository.count(specification);
    }

    /**
     * Function to convert {@link MProposalPriceSubItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProposalPriceSubItem> createSpecification(MProposalPriceSubItemCriteria criteria) {
        Specification<MProposalPriceSubItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProposalPriceSubItem_.id));
            }
            if (criteria.getProposedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProposedPrice(), MProposalPriceSubItem_.proposedPrice));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProposalPriceSubItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProposalPriceSubItem_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProposalPriceSubItem_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingSubItemLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubItemLineId(),
                    root -> root.join(MProposalPriceSubItem_.biddingSubItemLine, JoinType.LEFT).get(MBiddingSubItemLine_.id)));
            }
            if (criteria.getProposalPriceLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getProposalPriceLineId(),
                    root -> root.join(MProposalPriceSubItem_.proposalPriceLine, JoinType.LEFT).get(MProposalPriceLine_.id)));
            }
        }
        return specification;
    }
}
