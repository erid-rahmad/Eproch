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

import com.bhp.opusb.domain.CVendorGroup;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CVendorGroupRepository;
import com.bhp.opusb.service.dto.CVendorGroupCriteria;
import com.bhp.opusb.service.dto.CVendorGroupDTO;
import com.bhp.opusb.service.mapper.CVendorGroupMapper;

/**
 * Service for executing complex queries for {@link CVendorGroup} entities in the database.
 * The main input is a {@link CVendorGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVendorGroupDTO} or a {@link Page} of {@link CVendorGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVendorGroupQueryService extends QueryService<CVendorGroup> {

    private final Logger log = LoggerFactory.getLogger(CVendorGroupQueryService.class);

    private final CVendorGroupRepository cVendorGroupRepository;

    private final CVendorGroupMapper cVendorGroupMapper;

    public CVendorGroupQueryService(CVendorGroupRepository cVendorGroupRepository, CVendorGroupMapper cVendorGroupMapper) {
        this.cVendorGroupRepository = cVendorGroupRepository;
        this.cVendorGroupMapper = cVendorGroupMapper;
    }

    /**
     * Return a {@link List} of {@link CVendorGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVendorGroupDTO> findByCriteria(CVendorGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVendorGroup> specification = createSpecification(criteria);
        return cVendorGroupMapper.toDto(cVendorGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVendorGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorGroupDTO> findByCriteria(CVendorGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVendorGroup> specification = createSpecification(criteria);
        return cVendorGroupRepository.findAll(specification, page)
            .map(cVendorGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVendorGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVendorGroup> specification = createSpecification(criteria);
        return cVendorGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link CVendorGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVendorGroup> createSpecification(CVendorGroupCriteria criteria) {
        Specification<CVendorGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVendorGroup_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CVendorGroup_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CVendorGroup_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CVendorGroup_.description));
            }
            if (criteria.getIsDefault() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDefault(), CVendorGroup_.isDefault));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CVendorGroup_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CVendorGroup_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CVendorGroup_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
