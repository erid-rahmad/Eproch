package com.bhp.opusb.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

// for static metamodels
import com.bhp.opusb.domain.CVendor_;
import com.bhp.opusb.domain.MAuctionEventLog;
import com.bhp.opusb.domain.MAuctionEventLog_;
import com.bhp.opusb.domain.MAuctionItem_;
import com.bhp.opusb.domain.MAuction_;
import com.bhp.opusb.domain.view.AmountView;
import com.bhp.opusb.repository.MAuctionEventLogRepository;
import com.bhp.opusb.service.dto.MAuctionEventLogCriteria;
import com.bhp.opusb.service.dto.MAuctionEventLogDTO;
import com.bhp.opusb.service.mapper.MAuctionEventLogMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MAuctionEventLog} entities in the database.
 * The main input is a {@link MAuctionEventLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionEventLogDTO} or a {@link Page} of {@link MAuctionEventLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionEventLogQueryService extends QueryService<MAuctionEventLog> {

    private final Logger log = LoggerFactory.getLogger(MAuctionEventLogQueryService.class);

    private final MAuctionEventLogRepository mAuctionEventLogRepository;

    private final MAuctionEventLogMapper mAuctionEventLogMapper;

    public MAuctionEventLogQueryService(MAuctionEventLogRepository mAuctionEventLogRepository, MAuctionEventLogMapper mAuctionEventLogMapper) {
        this.mAuctionEventLogRepository = mAuctionEventLogRepository;
        this.mAuctionEventLogMapper = mAuctionEventLogMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionEventLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionEventLogDTO> findByCriteria(MAuctionEventLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionEventLog> specification = createSpecification(criteria);
        return mAuctionEventLogMapper.toDto(mAuctionEventLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionEventLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionEventLogDTO> findByCriteria(MAuctionEventLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionEventLog> specification = createSpecification(criteria);
        return mAuctionEventLogRepository.findAll(specification, page)
            .map(mAuctionEventLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionEventLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionEventLog> specification = createSpecification(criteria);
        return mAuctionEventLogRepository.count(specification);
    }

    @Transactional(readOnly = true)
    public AmountView getItemsMinPrice(Long itemId) {
        return mAuctionEventLogRepository.getMinPriceByAuctionItemId(itemId);
    }

    /**
     * Function to convert {@link MAuctionEventLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionEventLog> createSpecification(MAuctionEventLogCriteria criteria) {
        Specification<MAuctionEventLog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionEventLog_.id));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), MAuctionEventLog_.action));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MAuctionEventLog_.dateTrx));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), MAuctionEventLog_.username));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), MAuctionEventLog_.price));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), MAuctionEventLog_.note));
            }
            if (criteria.getAuctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionId(),
                    root -> root.join(MAuctionEventLog_.auction, JoinType.LEFT).get(MAuction_.id)));
            }
            if (criteria.getAuctionItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionItemId(),
                    root -> root.join(MAuctionEventLog_.auctionItem, JoinType.LEFT).get(MAuctionItem_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MAuctionEventLog_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
