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

import com.bhp.opusb.domain.MProposalPrice;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProposalPriceRepository;
import com.bhp.opusb.service.dto.MProposalPriceCriteria;
import com.bhp.opusb.service.dto.MProposalPriceDTO;
import com.bhp.opusb.service.mapper.MProposalPriceMapper;

/**
 * Service for executing complex queries for {@link MProposalPrice} entities in the database.
 * The main input is a {@link MProposalPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProposalPriceDTO} or a {@link Page} of {@link MProposalPriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProposalPriceQueryService extends QueryService<MProposalPrice> {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceQueryService.class);

    private final MProposalPriceRepository mProposalPriceRepository;

    private final MProposalPriceMapper mProposalPriceMapper;

    public MProposalPriceQueryService(MProposalPriceRepository mProposalPriceRepository, MProposalPriceMapper mProposalPriceMapper) {
        this.mProposalPriceRepository = mProposalPriceRepository;
        this.mProposalPriceMapper = mProposalPriceMapper;
    }

    /**
     * Return a {@link List} of {@link MProposalPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProposalPriceDTO> findByCriteria(MProposalPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProposalPrice> specification = createSpecification(criteria);
        return mProposalPriceMapper.toDto(mProposalPriceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProposalPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalPriceDTO> findByCriteria(MProposalPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProposalPrice> specification = createSpecification(criteria);
        return mProposalPriceRepository.findAll(specification, page)
            .map(mProposalPriceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProposalPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProposalPrice> specification = createSpecification(criteria);
        return mProposalPriceRepository.count(specification);
    }

    /**
     * Function to convert {@link MProposalPriceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProposalPrice> createSpecification(MProposalPriceCriteria criteria) {
        Specification<MProposalPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProposalPrice_.id));
            }
            if (criteria.getProposedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProposedPrice(), MProposalPrice_.proposedPrice));
            }
            if (criteria.getCeilingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCeilingPrice(), MProposalPrice_.ceilingPrice));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProposalPrice_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProposalPrice_.active));
            }
            if (criteria.getBiddingSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubmissionId(),
                    root -> root.join(MProposalPrice_.biddingSubmission, JoinType.LEFT).get(MBiddingSubmission_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProposalPrice_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
