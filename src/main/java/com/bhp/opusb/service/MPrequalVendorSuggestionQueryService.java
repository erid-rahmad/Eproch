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

import com.bhp.opusb.domain.MPrequalVendorSuggestion;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalVendorSuggestionRepository;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionCriteria;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionDTO;
import com.bhp.opusb.service.mapper.MPrequalVendorSuggestionMapper;

/**
 * Service for executing complex queries for {@link MPrequalVendorSuggestion} entities in the database.
 * The main input is a {@link MPrequalVendorSuggestionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalVendorSuggestionDTO} or a {@link Page} of {@link MPrequalVendorSuggestionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalVendorSuggestionQueryService extends QueryService<MPrequalVendorSuggestion> {

    private final Logger log = LoggerFactory.getLogger(MPrequalVendorSuggestionQueryService.class);

    private final MPrequalVendorSuggestionRepository mPrequalVendorSuggestionRepository;

    private final MPrequalVendorSuggestionMapper mPrequalVendorSuggestionMapper;

    public MPrequalVendorSuggestionQueryService(MPrequalVendorSuggestionRepository mPrequalVendorSuggestionRepository, MPrequalVendorSuggestionMapper mPrequalVendorSuggestionMapper) {
        this.mPrequalVendorSuggestionRepository = mPrequalVendorSuggestionRepository;
        this.mPrequalVendorSuggestionMapper = mPrequalVendorSuggestionMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalVendorSuggestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalVendorSuggestionDTO> findByCriteria(MPrequalVendorSuggestionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalVendorSuggestion> specification = createSpecification(criteria);
        return mPrequalVendorSuggestionMapper.toDto(mPrequalVendorSuggestionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalVendorSuggestionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalVendorSuggestionDTO> findByCriteria(MPrequalVendorSuggestionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalVendorSuggestion> specification = createSpecification(criteria);
        return mPrequalVendorSuggestionRepository.findAll(specification, page)
            .map(mPrequalVendorSuggestionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalVendorSuggestionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalVendorSuggestion> specification = createSpecification(criteria);
        return mPrequalVendorSuggestionRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalVendorSuggestionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalVendorSuggestion> createSpecification(MPrequalVendorSuggestionCriteria criteria) {
        Specification<MPrequalVendorSuggestion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalVendorSuggestion_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalVendorSuggestion_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalVendorSuggestion_.active));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalVendorSuggestion_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalVendorSuggestion_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBusinessSubCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessSubCategoryId(),
                    root -> root.join(MPrequalVendorSuggestion_.businessSubCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MPrequalVendorSuggestion_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
