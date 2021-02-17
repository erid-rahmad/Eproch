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

import com.bhp.opusb.domain.MVendorSuggestion;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorSuggestionRepository;
import com.bhp.opusb.service.dto.MVendorSuggestionCriteria;
import com.bhp.opusb.service.dto.MVendorSuggestionDTO;
import com.bhp.opusb.service.mapper.MVendorSuggestionMapper;

/**
 * Service for executing complex queries for {@link MVendorSuggestion} entities in the database.
 * The main input is a {@link MVendorSuggestionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorSuggestionDTO} or a {@link Page} of {@link MVendorSuggestionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorSuggestionQueryService extends QueryService<MVendorSuggestion> {

    private final Logger log = LoggerFactory.getLogger(MVendorSuggestionQueryService.class);

    private final MVendorSuggestionRepository mVendorSuggestionRepository;

    private final MVendorSuggestionMapper mVendorSuggestionMapper;

    public MVendorSuggestionQueryService(MVendorSuggestionRepository mVendorSuggestionRepository, MVendorSuggestionMapper mVendorSuggestionMapper) {
        this.mVendorSuggestionRepository = mVendorSuggestionRepository;
        this.mVendorSuggestionMapper = mVendorSuggestionMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorSuggestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorSuggestionDTO> findByCriteria(MVendorSuggestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorSuggestion> specification = createSpecification(criteria);
        return mVendorSuggestionMapper.toDto(mVendorSuggestionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorSuggestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorSuggestionDTO> findByCriteria(MVendorSuggestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorSuggestion> specification = createSpecification(criteria);
        return mVendorSuggestionRepository.findAll(specification, page)
            .map(mVendorSuggestionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorSuggestionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorSuggestion> specification = createSpecification(criteria);
        return mVendorSuggestionRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorSuggestionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorSuggestion> createSpecification(MVendorSuggestionCriteria criteria) {
        Specification<MVendorSuggestion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorSuggestion_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorSuggestion_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorSuggestion_.active));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MVendorSuggestion_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorSuggestion_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBusinessSubCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessSubCategoryId(),
                    root -> root.join(MVendorSuggestion_.businessSubCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MVendorSuggestion_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
