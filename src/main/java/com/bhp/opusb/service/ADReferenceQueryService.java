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

import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADReferenceRepository;
import com.bhp.opusb.service.dto.ADReferenceCriteria;
import com.bhp.opusb.service.dto.ADReferenceDTO;
import com.bhp.opusb.service.mapper.ADReferenceMapper;

/**
 * Service for executing complex queries for {@link ADReference} entities in the database.
 * The main input is a {@link ADReferenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADReferenceDTO} or a {@link Page} of {@link ADReferenceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADReferenceQueryService extends QueryService<ADReference> {

    private final Logger log = LoggerFactory.getLogger(ADReferenceQueryService.class);

    private final ADReferenceRepository aDReferenceRepository;

    private final ADReferenceMapper aDReferenceMapper;

    public ADReferenceQueryService(ADReferenceRepository aDReferenceRepository, ADReferenceMapper aDReferenceMapper) {
        this.aDReferenceRepository = aDReferenceRepository;
        this.aDReferenceMapper = aDReferenceMapper;
    }

    /**
     * Return a {@link List} of {@link ADReferenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADReferenceDTO> findByCriteria(ADReferenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADReference> specification = createSpecification(criteria);
        return aDReferenceMapper.toDto(aDReferenceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADReferenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADReferenceDTO> findByCriteria(ADReferenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADReference> specification = createSpecification(criteria);
        return aDReferenceRepository.findAll(specification, page)
            .map(aDReferenceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADReferenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADReference> specification = createSpecification(criteria);
        return aDReferenceRepository.count(specification);
    }

    /**
     * Function to convert {@link ADReferenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADReference> createSpecification(ADReferenceCriteria criteria) {
        Specification<ADReference> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADReference_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADReference_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ADReference_.value));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ADReference_.description));
            }
            if (criteria.getReferenceType() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenceType(), ADReference_.referenceType));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADReference_.active));
            }
            if (criteria.getADReferenceListId() != null) {
                specification = specification.and(buildSpecification(criteria.getADReferenceListId(),
                    root -> root.join(ADReference_.aDReferenceLists, JoinType.LEFT).get(ADReferenceList_.id)));
            }
        }
        return specification;
    }
}
