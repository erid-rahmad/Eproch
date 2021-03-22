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

import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingLineRepository;
import com.bhp.opusb.service.dto.MBiddingLineCriteria;
import com.bhp.opusb.service.dto.MBiddingLineDTO;
import com.bhp.opusb.service.mapper.MBiddingLineMapper;

/**
 * Service for executing complex queries for {@link MBiddingLine} entities in the database.
 * The main input is a {@link MBiddingLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingLineDTO} or a {@link Page} of {@link MBiddingLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingLineQueryService extends QueryService<MBiddingLine> {

    private final Logger log = LoggerFactory.getLogger(MBiddingLineQueryService.class);

    private final MBiddingLineRepository mBiddingLineRepository;

    private final MBiddingLineMapper mBiddingLineMapper;

    public MBiddingLineQueryService(MBiddingLineRepository mBiddingLineRepository, MBiddingLineMapper mBiddingLineMapper) {
        this.mBiddingLineRepository = mBiddingLineRepository;
        this.mBiddingLineMapper = mBiddingLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingLineDTO> findByCriteria(MBiddingLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingLine> specification = createSpecification(criteria);
        return mBiddingLineMapper.toDto(mBiddingLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingLineDTO> findByCriteria(MBiddingLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingLine> specification = createSpecification(criteria);
        return mBiddingLineRepository.findAll(specification, page)
            .map(mBiddingLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingLine> specification = createSpecification(criteria);
        return mBiddingLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingLine> createSpecification(MBiddingLineCriteria criteria) {
        Specification<MBiddingLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingLine_.id));
            }
            if (criteria.getLineNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNo(), MBiddingLine_.lineNo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), MBiddingLine_.quantity));
            }
            if (criteria.getCeilingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCeilingPrice(), MBiddingLine_.ceilingPrice));
            }
            if (criteria.getTotalCeilingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalCeilingPrice(), MBiddingLine_.totalCeilingPrice));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), MBiddingLine_.deliveryDate));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), MBiddingLine_.remark));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingLine_.active));
            }
            if (criteria.getSubItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubItemId(),
                    root -> root.join(MBiddingLine_.subItem, JoinType.LEFT).get(MBiddingSubItem_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MBiddingLine_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MBiddingLine_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MBiddingLine_.product, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MBiddingLine_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
        }
        return specification;
    }
}
