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

import com.bhp.opusb.domain.MAuctionRule;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionRuleRepository;
import com.bhp.opusb.service.dto.MAuctionRuleCriteria;
import com.bhp.opusb.service.dto.MAuctionRuleDTO;
import com.bhp.opusb.service.mapper.MAuctionRuleMapper;

/**
 * Service for executing complex queries for {@link MAuctionRule} entities in the database.
 * The main input is a {@link MAuctionRuleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionRuleDTO} or a {@link Page} of {@link MAuctionRuleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionRuleQueryService extends QueryService<MAuctionRule> {

    private final Logger log = LoggerFactory.getLogger(MAuctionRuleQueryService.class);

    private final MAuctionRuleRepository mAuctionRuleRepository;

    private final MAuctionRuleMapper mAuctionRuleMapper;

    public MAuctionRuleQueryService(MAuctionRuleRepository mAuctionRuleRepository, MAuctionRuleMapper mAuctionRuleMapper) {
        this.mAuctionRuleRepository = mAuctionRuleRepository;
        this.mAuctionRuleMapper = mAuctionRuleMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionRuleDTO> findByCriteria(MAuctionRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuctionRule> specification = createSpecification(criteria);
        return mAuctionRuleMapper.toDto(mAuctionRuleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionRuleDTO> findByCriteria(MAuctionRuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuctionRule> specification = createSpecification(criteria);
        return mAuctionRuleRepository.findAll(specification, page)
            .map(mAuctionRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionRuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuctionRule> specification = createSpecification(criteria);
        return mAuctionRuleRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionRuleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuctionRule> createSpecification(MAuctionRuleCriteria criteria) {
        Specification<MAuctionRule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuctionRule_.id));
            }
            if (criteria.getBidPrevPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBidPrevPeriod(), MAuctionRule_.bidPrevPeriod));
            }
            if (criteria.getPreBidEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreBidEndDate(), MAuctionRule_.preBidEndDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), MAuctionRule_.startDate));
            }
            if (criteria.getFirstLotRunTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstLotRunTime(), MAuctionRule_.firstLotRunTime));
            }
            if (criteria.getBidRankOvertime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBidRankOvertime(), MAuctionRule_.bidRankOvertime));
            }
            if (criteria.getStartOvertimeWithin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartOvertimeWithin(), MAuctionRule_.startOvertimeWithin));
            }
            if (criteria.getOvertimePeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOvertimePeriod(), MAuctionRule_.overtimePeriod));
            }
            if (criteria.getEstAwardDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstAwardDate(), MAuctionRule_.estAwardDate));
            }
            if (criteria.getBidImprovementUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBidImprovementUnit(), MAuctionRule_.bidImprovementUnit));
            }
            if (criteria.getTieBidsRule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTieBidsRule(), MAuctionRule_.tieBidsRule));
            }
            if (criteria.getShowResponse() != null) {
                specification = specification.and(buildSpecification(criteria.getShowResponse(), MAuctionRule_.showResponse));
            }
            if (criteria.getShowLeader() != null) {
                specification = specification.and(buildSpecification(criteria.getShowLeader(), MAuctionRule_.showLeader));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuctionRule_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuctionRule_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuctionRule_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
