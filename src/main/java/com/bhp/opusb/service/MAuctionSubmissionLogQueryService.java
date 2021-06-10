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

import com.bhp.opusb.domain.MAuctionSubmissionLog;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionSubmissionLogRepository;
import com.bhp.opusb.service.dto.MAuctionSubmissionLogCriteria;
import com.bhp.opusb.service.dto.MAuctionSubmissionLogDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionLogMapper;

/**
 * Service for executing complex queries for {@link MAuctionSubmissionLog} entities in the database.
 * The main input is a {@link MAuctionSubmissionLogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionSubmissionLogDTO} or a {@link Page} of {@link MAuctionSubmissionLogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionSubmissionLogQueryService extends QueryService<MAuctionSubmissionLog> {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionLogQueryService.class);

    private final MAuctionSubmissionLogRepository mAuctionSubmissionLogRepository;

    private final MAuctionSubmissionLogMapper mAuctionSubmissionLogMapper;

    public MAuctionSubmissionLogQueryService(MAuctionSubmissionLogRepository mAuctionSubmissionLogRepository, MAuctionSubmissionLogMapper mAuctionSubmissionLogMapper) {
        this.mAuctionSubmissionLogRepository = mAuctionSubmissionLogRepository;
        this.mAuctionSubmissionLogMapper = mAuctionSubmissionLogMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionSubmissionLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionSubmissionLogDTO> findByCriteria(MAuctionSubmissionLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionSubmissionLog> specification = createSpecification(criteria);
        return mAuctionSubmissionLogMapper.toDto(mAuctionSubmissionLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionSubmissionLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionSubmissionLogDTO> findByCriteria(MAuctionSubmissionLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionSubmissionLog> specification = createSpecification(criteria);
        return mAuctionSubmissionLogRepository.findAll(specification, page)
            .map(mAuctionSubmissionLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionSubmissionLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionSubmissionLog> specification = createSpecification(criteria);
        return mAuctionSubmissionLogRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionSubmissionLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionSubmissionLog> createSpecification(MAuctionSubmissionLogCriteria criteria) {
        Specification<MAuctionSubmissionLog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionSubmissionLog_.id));
            }
            if (criteria.getAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAction(), MAuctionSubmissionLog_.action));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), MAuctionSubmissionLog_.userName));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), MAuctionSubmissionLog_.price));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MAuctionSubmissionLog_.dateTrx));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), MAuctionSubmissionLog_.message));
            }
            if (criteria.getAuctionItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuctionItemId(),
                    root -> root.join(MAuctionSubmissionLog_.auctionItem, JoinType.LEFT).get(MAuctionItem_.id)));
            }
        }
        return specification;
    }
}
