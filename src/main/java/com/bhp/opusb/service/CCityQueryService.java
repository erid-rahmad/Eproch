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

import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CCityRepository;
import com.bhp.opusb.service.dto.CCityCriteria;
import com.bhp.opusb.service.dto.CCityDTO;
import com.bhp.opusb.service.mapper.CCityMapper;

/**
 * Service for executing complex queries for {@link CCity} entities in the database.
 * The main input is a {@link CCityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CCityDTO} or a {@link Page} of {@link CCityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CCityQueryService extends QueryService<CCity> {

    private final Logger log = LoggerFactory.getLogger(CCityQueryService.class);

    private final CCityRepository cCityRepository;

    private final CCityMapper cCityMapper;

    public CCityQueryService(CCityRepository cCityRepository, CCityMapper cCityMapper) {
        this.cCityRepository = cCityRepository;
        this.cCityMapper = cCityMapper;
    }

    /**
     * Return a {@link List} of {@link CCityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CCityDTO> findByCriteria(CCityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CCity> specification = createSpecification(criteria);
        return cCityMapper.toDto(cCityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CCityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CCityDTO> findByCriteria(CCityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CCity> specification = createSpecification(criteria);
        return cCityRepository.findAll(specification, page)
            .map(cCityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CCityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CCity> specification = createSpecification(criteria);
        return cCityRepository.count(specification);
    }

    /**
     * Function to convert {@link CCityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CCity> createSpecification(CCityCriteria criteria) {
        Specification<CCity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CCity_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CCity_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CCity_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CCity_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CCity_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CCity_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(CCity_.country, JoinType.LEFT).get(CCountry_.id)));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(),
                    root -> root.join(CCity_.region, JoinType.LEFT).get(CRegion_.id)));
            }
        }
        return specification;
    }
}
