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

import com.bhp.opusb.domain.MRfqView;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MRfqViewRepository;
import com.bhp.opusb.service.dto.MRfqViewCriteria;
import com.bhp.opusb.service.dto.MRfqViewDTO;
import com.bhp.opusb.service.mapper.MRfqViewMapper;

/**
 * Service for executing complex queries for {@link MRfqView} entities in the database.
 * The main input is a {@link MRfqViewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MRfqViewDTO} or a {@link Page} of {@link MRfqViewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MRfqViewQueryService extends QueryService<MRfqView> {

    private final Logger log = LoggerFactory.getLogger(MRfqViewQueryService.class);

    private final MRfqViewRepository mRfqViewRepository;

    private final MRfqViewMapper mRfqViewMapper;

    public MRfqViewQueryService(MRfqViewRepository mRfqViewRepository, MRfqViewMapper mRfqViewMapper) {
        this.mRfqViewRepository = mRfqViewRepository;
        this.mRfqViewMapper = mRfqViewMapper;
    }

    /**
     * Return a {@link List} of {@link MRfqViewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MRfqViewDTO> findByCriteria(MRfqViewCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MRfqView> specification = createSpecification(criteria);
        return mRfqViewMapper.toDto(mRfqViewRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MRfqViewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqViewDTO> findByCriteria(MRfqViewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MRfqView> specification = createSpecification(criteria);
        return mRfqViewRepository.findAll(specification, page)
            .map(mRfqViewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MRfqViewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MRfqView> specification = createSpecification(criteria);
        return mRfqViewRepository.count(specification);
    }

    /**
     * Function to convert {@link MRfqViewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MRfqView> createSpecification(MRfqViewCriteria criteria) {
        Specification<MRfqView> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MRfqView_.id));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MRfqView_.documentNo));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), MRfqView_.title));
            }
            if (criteria.getDateRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRequired(), MRfqView_.dateRequired));
            }
            if (criteria.getSelectionMethod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSelectionMethod(), MRfqView_.selectionMethod));
            }
            if (criteria.getJoinedVendorCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJoinedVendorCount(), MRfqView_.joinedVendorCount));
            }
            if (criteria.getQuotationId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuotationId(),
                    root -> root.join(MRfqView_.quotation, JoinType.LEFT).get(MRfq_.id)));
            }
        }
        return specification;
    }
}
