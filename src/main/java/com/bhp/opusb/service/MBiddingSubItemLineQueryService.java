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

import com.bhp.opusb.domain.MBiddingSubItemLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBiddingSubItemLineRepository;
import com.bhp.opusb.service.dto.MBiddingSubItemLineCriteria;
import com.bhp.opusb.service.dto.MBiddingSubItemLineDTO;
import com.bhp.opusb.service.mapper.MBiddingSubItemLineMapper;

/**
 * Service for executing complex queries for {@link MBiddingSubItemLine} entities in the database.
 * The main input is a {@link MBiddingSubItemLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBiddingSubItemLineDTO} or a {@link Page} of {@link MBiddingSubItemLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBiddingSubItemLineQueryService extends QueryService<MBiddingSubItemLine> {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubItemLineQueryService.class);

    private final MBiddingSubItemLineRepository mBiddingSubItemLineRepository;

    private final MBiddingSubItemLineMapper mBiddingSubItemLineMapper;

    public MBiddingSubItemLineQueryService(MBiddingSubItemLineRepository mBiddingSubItemLineRepository, MBiddingSubItemLineMapper mBiddingSubItemLineMapper) {
        this.mBiddingSubItemLineRepository = mBiddingSubItemLineRepository;
        this.mBiddingSubItemLineMapper = mBiddingSubItemLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBiddingSubItemLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBiddingSubItemLineDTO> findByCriteria(MBiddingSubItemLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBiddingSubItemLine> specification = createSpecification(criteria);
        return mBiddingSubItemLineMapper.toDto(mBiddingSubItemLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBiddingSubItemLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubItemLineDTO> findByCriteria(MBiddingSubItemLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBiddingSubItemLine> specification = createSpecification(criteria);
        return mBiddingSubItemLineRepository.findAll(specification, page)
            .map(mBiddingSubItemLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBiddingSubItemLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBiddingSubItemLine> specification = createSpecification(criteria);
        return mBiddingSubItemLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBiddingSubItemLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBiddingSubItemLine> createSpecification(MBiddingSubItemLineCriteria criteria) {
        Specification<MBiddingSubItemLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBiddingSubItemLine_.id));
            }
            if (criteria.getLineNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNo(), MBiddingSubItemLine_.lineNo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), MBiddingSubItemLine_.quantity));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), MBiddingSubItemLine_.price));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), MBiddingSubItemLine_.amount));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBiddingSubItemLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBiddingSubItemLine_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MBiddingSubItemLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MBiddingSubItemLine_.product, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MBiddingSubItemLine_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
            if (criteria.getBiddingSubItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubItemId(),
                    root -> root.join(MBiddingSubItemLine_.biddingSubItem, JoinType.LEFT).get(MBiddingSubItem_.id)));
            }
        }
        return specification;
    }
}
