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

import com.bhp.opusb.domain.ADFieldGroup;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADFieldGroupRepository;
import com.bhp.opusb.service.dto.ADFieldGroupCriteria;
import com.bhp.opusb.service.dto.ADFieldGroupDTO;
import com.bhp.opusb.service.mapper.ADFieldGroupMapper;

/**
 * Service for executing complex queries for {@link ADFieldGroup} entities in the database.
 * The main input is a {@link ADFieldGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADFieldGroupDTO} or a {@link Page} of {@link ADFieldGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADFieldGroupQueryService extends QueryService<ADFieldGroup> {

    private final Logger log = LoggerFactory.getLogger(ADFieldGroupQueryService.class);

    private final ADFieldGroupRepository aDFieldGroupRepository;

    private final ADFieldGroupMapper aDFieldGroupMapper;

    public ADFieldGroupQueryService(ADFieldGroupRepository aDFieldGroupRepository, ADFieldGroupMapper aDFieldGroupMapper) {
        this.aDFieldGroupRepository = aDFieldGroupRepository;
        this.aDFieldGroupMapper = aDFieldGroupMapper;
    }

    /**
     * Return a {@link List} of {@link ADFieldGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADFieldGroupDTO> findByCriteria(ADFieldGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADFieldGroup> specification = createSpecification(criteria);
        return aDFieldGroupMapper.toDto(aDFieldGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADFieldGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADFieldGroupDTO> findByCriteria(ADFieldGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADFieldGroup> specification = createSpecification(criteria);
        return aDFieldGroupRepository.findAll(specification, page)
            .map(aDFieldGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADFieldGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADFieldGroup> specification = createSpecification(criteria);
        return aDFieldGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link ADFieldGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADFieldGroup> createSpecification(ADFieldGroupCriteria criteria) {
        Specification<ADFieldGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADFieldGroup_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADFieldGroup_.name));
            }
            if (criteria.getCollapsible() != null) {
                specification = specification.and(buildSpecification(criteria.getCollapsible(), ADFieldGroup_.collapsible));
            }
            if (criteria.getCollapseByDefault() != null) {
                specification = specification.and(buildSpecification(criteria.getCollapseByDefault(), ADFieldGroup_.collapseByDefault));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADFieldGroup_.active));
            }
        }
        return specification;
    }
}
