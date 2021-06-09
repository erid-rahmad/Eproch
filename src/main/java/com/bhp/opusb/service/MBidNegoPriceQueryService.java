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

import com.bhp.opusb.domain.MBidNegoPrice;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBidNegoPriceRepository;
import com.bhp.opusb.service.dto.MBidNegoPriceCriteria;
import com.bhp.opusb.service.dto.MBidNegoPriceDTO;
import com.bhp.opusb.service.mapper.MBidNegoPriceMapper;

/**
 * Service for executing complex queries for {@link MBidNegoPrice} entities in the database.
 * The main input is a {@link MBidNegoPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBidNegoPriceDTO} or a {@link Page} of {@link MBidNegoPriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBidNegoPriceQueryService extends QueryService<MBidNegoPrice> {

    private final Logger log = LoggerFactory.getLogger(MBidNegoPriceQueryService.class);

    private final MBidNegoPriceRepository mBidNegoPriceRepository;

    private final MBidNegoPriceMapper mBidNegoPriceMapper;

    public MBidNegoPriceQueryService(MBidNegoPriceRepository mBidNegoPriceRepository, MBidNegoPriceMapper mBidNegoPriceMapper) {
        this.mBidNegoPriceRepository = mBidNegoPriceRepository;
        this.mBidNegoPriceMapper = mBidNegoPriceMapper;
    }

    /**
     * Return a {@link List} of {@link MBidNegoPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBidNegoPriceDTO> findByCriteria(MBidNegoPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBidNegoPrice> specification = createSpecification(criteria);
        return mBidNegoPriceMapper.toDto(mBidNegoPriceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBidNegoPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBidNegoPriceDTO> findByCriteria(MBidNegoPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBidNegoPrice> specification = createSpecification(criteria);
        return mBidNegoPriceRepository.findAll(specification, page)
            .map(mBidNegoPriceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBidNegoPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBidNegoPrice> specification = createSpecification(criteria);
        return mBidNegoPriceRepository.count(specification);
    }

    /**
     * Function to convert {@link MBidNegoPriceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBidNegoPrice> createSpecification(MBidNegoPriceCriteria criteria) {
        Specification<MBidNegoPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBidNegoPrice_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBidNegoPrice_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBidNegoPrice_.active));
            }
            if (criteria.getNegotiationPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNegotiationPrice(), MBidNegoPrice_.negotiationPrice));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MBidNegoPrice_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getPriceProposalId() != null) {
                specification = specification.and(buildSpecification(criteria.getPriceProposalId(),
                    root -> root.join(MBidNegoPrice_.priceProposal, JoinType.LEFT).get(MProposalPrice_.id)));
            }
            if (criteria.getNegotiationLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getNegotiationLineId(),
                    root -> root.join(MBidNegoPrice_.negotiationLine, JoinType.LEFT).get(MBiddingNegotiationLine_.id)));
            }
        }
        return specification;
    }
}
