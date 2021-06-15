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

import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionItemRepository;
import com.bhp.opusb.service.dto.MAuctionItemCriteria;
import com.bhp.opusb.service.dto.MAuctionItemDTO;
import com.bhp.opusb.service.mapper.MAuctionItemMapper;

/**
 * Service for executing complex queries for {@link MAuctionItem} entities in the database.
 * The main input is a {@link MAuctionItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionItemDTO} or a {@link Page} of {@link MAuctionItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionItemQueryService extends QueryService<MAuctionItem> {

    private final Logger log = LoggerFactory.getLogger(MAuctionItemQueryService.class);

    private final MAuctionItemRepository mAuctionItemRepository;

    private final MAuctionItemMapper mAuctionItemMapper;

    public MAuctionItemQueryService(MAuctionItemRepository mAuctionItemRepository, MAuctionItemMapper mAuctionItemMapper) {
        this.mAuctionItemRepository = mAuctionItemRepository;
        this.mAuctionItemMapper = mAuctionItemMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionItemDTO> findByCriteria(MAuctionItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionItem> specification = createSpecification(criteria);
        return mAuctionItemMapper.toDto(mAuctionItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionItemDTO> findByCriteria(MAuctionItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionItem> specification = createSpecification(criteria);
        return mAuctionItemRepository.findAll(specification, page)
            .map(mAuctionItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionItem> specification = createSpecification(criteria);
        return mAuctionItemRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionItem> createSpecification(MAuctionItemCriteria criteria) {
        Specification<MAuctionItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionItem_.id));
            }
            if (criteria.getAuctionStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuctionStatus(), MAuctionItem_.auctionStatus));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), MAuctionItem_.quantity));
            }
            if (criteria.getCeilingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCeilingPrice(), MAuctionItem_.ceilingPrice));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), MAuctionItem_.amount));
            }
            if (criteria.getBidDecrement() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBidDecrement(), MAuctionItem_.bidDecrement));
            }
            if (criteria.getProtectBackBuffer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProtectBackBuffer(), MAuctionItem_.protectBackBuffer));
            }
            if (criteria.getProtectFrontBuffer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProtectFrontBuffer(), MAuctionItem_.protectFrontBuffer));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuctionItem_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuctionItem_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuctionItem_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAuctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionId(),
                    root -> root.join(MAuctionItem_.auction, JoinType.LEFT).get(MAuction_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MAuctionItem_.product, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MAuctionItem_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
        }
        return specification;
    }
}
