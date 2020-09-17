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

import com.bhp.opusb.domain.ScAccessType;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ScAccessTypeRepository;
import com.bhp.opusb.service.dto.ScAccessTypeCriteria;
import com.bhp.opusb.service.dto.ScAccessTypeDTO;
import com.bhp.opusb.service.mapper.ScAccessTypeMapper;

/**
 * Service for executing complex queries for {@link ScAccessType} entities in the database.
 * The main input is a {@link ScAccessTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScAccessTypeDTO} or a {@link Page} of {@link ScAccessTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScAccessTypeQueryService extends QueryService<ScAccessType> {

    private final Logger log = LoggerFactory.getLogger(ScAccessTypeQueryService.class);

    private final ScAccessTypeRepository scAccessTypeRepository;

    private final ScAccessTypeMapper scAccessTypeMapper;

    public ScAccessTypeQueryService(ScAccessTypeRepository scAccessTypeRepository, ScAccessTypeMapper scAccessTypeMapper) {
        this.scAccessTypeRepository = scAccessTypeRepository;
        this.scAccessTypeMapper = scAccessTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ScAccessTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScAccessTypeDTO> findByCriteria(ScAccessTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ScAccessType> specification = createSpecification(criteria);
        return scAccessTypeMapper.toDto(scAccessTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScAccessTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScAccessTypeDTO> findByCriteria(ScAccessTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ScAccessType> specification = createSpecification(criteria);
        return scAccessTypeRepository.findAll(specification, page)
            .map(scAccessTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScAccessTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ScAccessType> specification = createSpecification(criteria);
        return scAccessTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ScAccessTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ScAccessType> createSpecification(ScAccessTypeCriteria criteria) {
        Specification<ScAccessType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ScAccessType_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), ScAccessType_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ScAccessType_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ScAccessType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ScAccessType_.description));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ScAccessType_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
