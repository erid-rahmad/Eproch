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

import com.bhp.opusb.domain.CProductGroup;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CProductGroupRepository;
import com.bhp.opusb.service.dto.CProductGroupCriteria;
import com.bhp.opusb.service.dto.CProductGroupDTO;
import com.bhp.opusb.service.mapper.CProductGroupMapper;

/**
 * Service for executing complex queries for {@link CProductGroup} entities in the database.
 * The main input is a {@link CProductGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CProductGroupDTO} or a {@link Page} of {@link CProductGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CProductGroupQueryService extends QueryService<CProductGroup> {

    private final Logger log = LoggerFactory.getLogger(CProductGroupQueryService.class);

    private final CProductGroupRepository cProductGroupRepository;

    private final CProductGroupMapper cProductGroupMapper;

    public CProductGroupQueryService(CProductGroupRepository cProductGroupRepository, CProductGroupMapper cProductGroupMapper) {
        this.cProductGroupRepository = cProductGroupRepository;
        this.cProductGroupMapper = cProductGroupMapper;
    }

    /**
     * Return a {@link List} of {@link CProductGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CProductGroupDTO> findByCriteria(CProductGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CProductGroup> specification = createSpecification(criteria);
        return cProductGroupMapper.toDto(cProductGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CProductGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductGroupDTO> findByCriteria(CProductGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CProductGroup> specification = createSpecification(criteria);
        return cProductGroupRepository.findAll(specification, page)
            .map(cProductGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CProductGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CProductGroup> specification = createSpecification(criteria);
        return cProductGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link CProductGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CProductGroup> createSpecification(CProductGroupCriteria criteria) {
        Specification<CProductGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CProductGroup_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CProductGroup_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CProductGroup_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CProductGroup_.type));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CProductGroup_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CProductGroup_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CProductGroup_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
