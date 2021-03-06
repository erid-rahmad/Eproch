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

import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CUnitOfMeasureRepository;
import com.bhp.opusb.service.dto.CUnitOfMeasureCriteria;
import com.bhp.opusb.service.dto.CUnitOfMeasureDTO;
import com.bhp.opusb.service.mapper.CUnitOfMeasureMapper;

/**
 * Service for executing complex queries for {@link CUnitOfMeasure} entities in the database.
 * The main input is a {@link CUnitOfMeasureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CUnitOfMeasureDTO} or a {@link Page} of {@link CUnitOfMeasureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CUnitOfMeasureQueryService extends QueryService<CUnitOfMeasure> {

    private final Logger log = LoggerFactory.getLogger(CUnitOfMeasureQueryService.class);

    private final CUnitOfMeasureRepository cUnitOfMeasureRepository;

    private final CUnitOfMeasureMapper cUnitOfMeasureMapper;

    public CUnitOfMeasureQueryService(CUnitOfMeasureRepository cUnitOfMeasureRepository, CUnitOfMeasureMapper cUnitOfMeasureMapper) {
        this.cUnitOfMeasureRepository = cUnitOfMeasureRepository;
        this.cUnitOfMeasureMapper = cUnitOfMeasureMapper;
    }

    /**
     * Return a {@link List} of {@link CUnitOfMeasureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CUnitOfMeasureDTO> findByCriteria(CUnitOfMeasureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CUnitOfMeasure> specification = createSpecification(criteria);
        return cUnitOfMeasureMapper.toDto(cUnitOfMeasureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CUnitOfMeasureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CUnitOfMeasureDTO> findByCriteria(CUnitOfMeasureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CUnitOfMeasure> specification = createSpecification(criteria);
        return cUnitOfMeasureRepository.findAll(specification, page)
            .map(cUnitOfMeasureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CUnitOfMeasureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CUnitOfMeasure> specification = createSpecification(criteria);
        return cUnitOfMeasureRepository.count(specification);
    }

    /**
     * Function to convert {@link CUnitOfMeasureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CUnitOfMeasure> createSpecification(CUnitOfMeasureCriteria criteria) {
        Specification<CUnitOfMeasure> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CUnitOfMeasure_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CUnitOfMeasure_.code));
            }
            if (criteria.getSymbol() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymbol(), CUnitOfMeasure_.symbol));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CUnitOfMeasure_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CUnitOfMeasure_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CUnitOfMeasure_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CUnitOfMeasure_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CUnitOfMeasure_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
