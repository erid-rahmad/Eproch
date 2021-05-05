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

import com.bhp.opusb.domain.MProposalPriceLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProposalPriceLineRepository;
import com.bhp.opusb.service.dto.MProposalPriceLineCriteria;
import com.bhp.opusb.service.dto.MProposalPriceLineDTO;
import com.bhp.opusb.service.mapper.MProposalPriceLineMapper;

/**
 * Service for executing complex queries for {@link MProposalPriceLine} entities in the database.
 * The main input is a {@link MProposalPriceLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProposalPriceLineDTO} or a {@link Page} of {@link MProposalPriceLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProposalPriceLineQueryService extends QueryService<MProposalPriceLine> {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceLineQueryService.class);

    private final MProposalPriceLineRepository mProposalPriceLineRepository;

    private final MProposalPriceLineMapper mProposalPriceLineMapper;

    public MProposalPriceLineQueryService(MProposalPriceLineRepository mProposalPriceLineRepository, MProposalPriceLineMapper mProposalPriceLineMapper) {
        this.mProposalPriceLineRepository = mProposalPriceLineRepository;
        this.mProposalPriceLineMapper = mProposalPriceLineMapper;
    }

    /**
     * Return a {@link List} of {@link MProposalPriceLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProposalPriceLineDTO> findByCriteria(MProposalPriceLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProposalPriceLine> specification = createSpecification(criteria);
        return mProposalPriceLineMapper.toDto(mProposalPriceLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProposalPriceLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalPriceLineDTO> findByCriteria(MProposalPriceLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProposalPriceLine> specification = createSpecification(criteria);
        return mProposalPriceLineRepository.findAll(specification, page)
            .map(mProposalPriceLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProposalPriceLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProposalPriceLine> specification = createSpecification(criteria);
        return mProposalPriceLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MProposalPriceLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProposalPriceLine> createSpecification(MProposalPriceLineCriteria criteria) {
        Specification<MProposalPriceLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProposalPriceLine_.id));
            }
            if (criteria.getProposedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProposedPrice(), MProposalPriceLine_.proposedPrice));
            }
            if (criteria.getTotalPriceSubmission() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPriceSubmission(), MProposalPriceLine_.totalPriceSubmission));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), MProposalPriceLine_.deliveryDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProposalPriceLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProposalPriceLine_.active));
            }
            if (criteria.getMProposalPriceSubItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getMProposalPriceSubItemId(),
                    root -> root.join(MProposalPriceLine_.mProposalPriceSubItems, JoinType.LEFT).get(MProposalPriceSubItem_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProposalPriceLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getProposalPriceId() != null) {
                specification = specification.and(buildSpecification(criteria.getProposalPriceId(),
                    root -> root.join(MProposalPriceLine_.proposalPrice, JoinType.LEFT).get(MProposalPrice_.id)));
            }
            if (criteria.getBiddingLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingLineId(),
                    root -> root.join(MProposalPriceLine_.biddingLine, JoinType.LEFT).get(MBiddingLine_.id)));
            }
        }
        return specification;
    }
}
