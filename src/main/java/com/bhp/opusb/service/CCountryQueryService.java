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

import com.bhp.opusb.domain.CCountry;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CCountryRepository;
import com.bhp.opusb.service.dto.CCountryCriteria;
import com.bhp.opusb.service.dto.CCountryDTO;
import com.bhp.opusb.service.mapper.CCountryMapper;

/**
 * Service for executing complex queries for {@link CCountry} entities in the database.
 * The main input is a {@link CCountryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CCountryDTO} or a {@link Page} of {@link CCountryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CCountryQueryService extends QueryService<CCountry> {

    private final Logger log = LoggerFactory.getLogger(CCountryQueryService.class);

    private final CCountryRepository cCountryRepository;

    private final CCountryMapper cCountryMapper;

    public CCountryQueryService(CCountryRepository cCountryRepository, CCountryMapper cCountryMapper) {
        this.cCountryRepository = cCountryRepository;
        this.cCountryMapper = cCountryMapper;
    }

    /**
     * Return a {@link List} of {@link CCountryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CCountryDTO> findByCriteria(CCountryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CCountry> specification = createSpecification(criteria);
        return cCountryMapper.toDto(cCountryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CCountryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CCountryDTO> findByCriteria(CCountryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CCountry> specification = createSpecification(criteria);
        return cCountryRepository.findAll(specification, page)
            .map(cCountryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CCountryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CCountry> specification = createSpecification(criteria);
        return cCountryRepository.count(specification);
    }

    /**
     * Function to convert {@link CCountryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CCountry> createSpecification(CCountryCriteria criteria) {
        Specification<CCountry> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CCountry_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CCountry_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CCountry_.code));
            }
            if (criteria.getWithRegion() != null) {
                specification = specification.and(buildSpecification(criteria.getWithRegion(), CCountry_.withRegion));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CCountry_.active));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(CCountry_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getCRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getCRegionId(),
                    root -> root.join(CCountry_.cRegions, JoinType.LEFT).get(CRegion_.id)));
            }
            if (criteria.getCCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCCityId(),
                    root -> root.join(CCountry_.cCities, JoinType.LEFT).get(CCity_.id)));
            }
        }
        return specification;
    }
}
