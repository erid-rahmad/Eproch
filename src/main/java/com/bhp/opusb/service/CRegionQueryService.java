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

import com.bhp.opusb.domain.CRegion;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CRegionRepository;
import com.bhp.opusb.service.dto.CRegionCriteria;
import com.bhp.opusb.service.dto.CRegionDTO;
import com.bhp.opusb.service.mapper.CRegionMapper;

/**
 * Service for executing complex queries for {@link CRegion} entities in the database.
 * The main input is a {@link CRegionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CRegionDTO} or a {@link Page} of {@link CRegionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CRegionQueryService extends QueryService<CRegion> {

    private final Logger log = LoggerFactory.getLogger(CRegionQueryService.class);

    private final CRegionRepository cRegionRepository;

    private final CRegionMapper cRegionMapper;

    public CRegionQueryService(CRegionRepository cRegionRepository, CRegionMapper cRegionMapper) {
        this.cRegionRepository = cRegionRepository;
        this.cRegionMapper = cRegionMapper;
    }

    /**
     * Return a {@link List} of {@link CRegionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CRegionDTO> findByCriteria(CRegionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CRegion> specification = createSpecification(criteria);
        return cRegionMapper.toDto(cRegionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CRegionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CRegionDTO> findByCriteria(CRegionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CRegion> specification = createSpecification(criteria);
        return cRegionRepository.findAll(specification, page)
            .map(cRegionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CRegionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CRegion> specification = createSpecification(criteria);
        return cRegionRepository.count(specification);
    }

    /**
     * Function to convert {@link CRegionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CRegion> createSpecification(CRegionCriteria criteria) {
        Specification<CRegion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CRegion_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CRegion_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CRegion_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CRegion_.active));
            }
            if (criteria.getCCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCityId(),
                    root -> root.join(CRegion_.cCities, JoinType.LEFT).get(CCity_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CRegion_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(CRegion_.country, JoinType.LEFT).get(CCountry_.id)));
            }
        }
        return specification;
    }
}
