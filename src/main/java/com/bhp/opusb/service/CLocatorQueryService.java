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

import com.bhp.opusb.domain.CLocator;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CLocatorRepository;
import com.bhp.opusb.service.dto.CLocatorCriteria;
import com.bhp.opusb.service.dto.CLocatorDTO;
import com.bhp.opusb.service.mapper.CLocatorMapper;

/**
 * Service for executing complex queries for {@link CLocator} entities in the database.
 * The main input is a {@link CLocatorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CLocatorDTO} or a {@link Page} of {@link CLocatorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CLocatorQueryService extends QueryService<CLocator> {

    private final Logger log = LoggerFactory.getLogger(CLocatorQueryService.class);

    private final CLocatorRepository cLocatorRepository;

    private final CLocatorMapper cLocatorMapper;

    public CLocatorQueryService(CLocatorRepository cLocatorRepository, CLocatorMapper cLocatorMapper) {
        this.cLocatorRepository = cLocatorRepository;
        this.cLocatorMapper = cLocatorMapper;
    }

    /**
     * Return a {@link List} of {@link CLocatorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CLocatorDTO> findByCriteria(CLocatorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CLocator> specification = createSpecification(criteria);
        return cLocatorMapper.toDto(cLocatorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CLocatorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CLocatorDTO> findByCriteria(CLocatorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CLocator> specification = createSpecification(criteria);
        return cLocatorRepository.findAll(specification, page)
            .map(cLocatorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CLocatorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CLocator> specification = createSpecification(criteria);
        return cLocatorRepository.count(specification);
    }

    /**
     * Function to convert {@link CLocatorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CLocator> createSpecification(CLocatorCriteria criteria) {
        Specification<CLocator> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CLocator_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CLocator_.code));
            }
            if (criteria.getLocatorType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocatorType(), CLocator_.locatorType));
            }
            if (criteria.getAisle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAisle(), CLocator_.aisle));
            }
            if (criteria.getBin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBin(), CLocator_.bin));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLevel(), CLocator_.level));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CLocator_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CLocator_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CLocator_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarehouseId(),
                    root -> root.join(CLocator_.warehouse, JoinType.LEFT).get(CWarehouse_.id)));
            }
        }
        return specification;
    }
}
