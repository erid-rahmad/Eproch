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

import com.bhp.opusb.domain.MBidNegoPriceLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBidNegoPriceLineRepository;
import com.bhp.opusb.service.dto.MBidNegoPriceLineCriteria;
import com.bhp.opusb.service.dto.MBidNegoPriceLineDTO;
import com.bhp.opusb.service.mapper.MBidNegoPriceLineMapper;

/**
 * Service for executing complex queries for {@link MBidNegoPriceLine} entities in the database.
 * The main input is a {@link MBidNegoPriceLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBidNegoPriceLineDTO} or a {@link Page} of {@link MBidNegoPriceLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBidNegoPriceLineQueryService extends QueryService<MBidNegoPriceLine> {

    private final Logger log = LoggerFactory.getLogger(MBidNegoPriceLineQueryService.class);

    private final MBidNegoPriceLineRepository mBidNegoPriceLineRepository;

    private final MBidNegoPriceLineMapper mBidNegoPriceLineMapper;

    public MBidNegoPriceLineQueryService(MBidNegoPriceLineRepository mBidNegoPriceLineRepository, MBidNegoPriceLineMapper mBidNegoPriceLineMapper) {
        this.mBidNegoPriceLineRepository = mBidNegoPriceLineRepository;
        this.mBidNegoPriceLineMapper = mBidNegoPriceLineMapper;
    }

    /**
     * Return a {@link List} of {@link MBidNegoPriceLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBidNegoPriceLineDTO> findByCriteria(MBidNegoPriceLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBidNegoPriceLine> specification = createSpecification(criteria);
        return mBidNegoPriceLineMapper.toDto(mBidNegoPriceLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBidNegoPriceLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBidNegoPriceLineDTO> findByCriteria(MBidNegoPriceLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBidNegoPriceLine> specification = createSpecification(criteria);
        return mBidNegoPriceLineRepository.findAll(specification, page)
            .map(mBidNegoPriceLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBidNegoPriceLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBidNegoPriceLine> specification = createSpecification(criteria);
        return mBidNegoPriceLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MBidNegoPriceLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBidNegoPriceLine> createSpecification(MBidNegoPriceLineCriteria criteria) {
        Specification<MBidNegoPriceLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBidNegoPriceLine_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBidNegoPriceLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBidNegoPriceLine_.active));
            }
            if (criteria.getPriceNegotiation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceNegotiation(), MBidNegoPriceLine_.priceNegotiation));
            }
            if (criteria.getTotalNegotiationPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalNegotiationPrice(), MBidNegoPriceLine_.totalNegotiationPrice));
            }
            if (criteria.getNegotiationPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNegotiationPercentage(), MBidNegoPriceLine_.negotiationPercentage));
            }
            if (criteria.getBidNegoPriceId() != null) {
                specification = specification.and(buildSpecification(criteria.getBidNegoPriceId(),
                    root -> root.join(MBidNegoPriceLine_.bidNegoPrice, JoinType.LEFT).get(MBidNegoPrice_.id)));
            }
            if (criteria.getBiddingLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingLineId(),
                    root -> root.join(MBidNegoPriceLine_.biddingLine, JoinType.LEFT).get(MBiddingLine_.id)));
            }
            if (criteria.getProposalLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getProposalLineId(),
                    root -> root.join(MBidNegoPriceLine_.proposalLine, JoinType.LEFT).get(MProposalPriceLine_.id)));
            }
        }
        return specification;
    }
}
