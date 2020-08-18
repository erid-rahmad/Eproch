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

import com.bhp.opusb.domain.CLocation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CLocationRepository;
import com.bhp.opusb.service.dto.CLocationCriteria;
import com.bhp.opusb.service.dto.CLocationDTO;
import com.bhp.opusb.service.mapper.CLocationMapper;

/**
 * Service for executing complex queries for {@link CLocation} entities in the database.
 * The main input is a {@link CLocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CLocationDTO} or a {@link Page} of {@link CLocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CLocationQueryService extends QueryService<CLocation> {

    private final Logger log = LoggerFactory.getLogger(CLocationQueryService.class);

    private final CLocationRepository cLocationRepository;

    private final CLocationMapper cLocationMapper;

    public CLocationQueryService(CLocationRepository cLocationRepository, CLocationMapper cLocationMapper) {
        this.cLocationRepository = cLocationRepository;
        this.cLocationMapper = cLocationMapper;
    }

    /**
     * Return a {@link List} of {@link CLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CLocationDTO> findByCriteria(CLocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CLocation> specification = createSpecification(criteria);
        return cLocationMapper.toDto(cLocationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CLocationDTO> findByCriteria(CLocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CLocation> specification = createSpecification(criteria);
        return cLocationRepository.findAll(specification, page)
            .map(cLocationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CLocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CLocation> specification = createSpecification(criteria);
        return cLocationRepository.count(specification);
    }

    /**
     * Function to convert {@link CLocationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CLocation> createSpecification(CLocationCriteria criteria) {
        Specification<CLocation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CLocation_.id));
            }
            if (criteria.getStreetAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetAddress(), CLocation_.streetAddress));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), CLocation_.postalCode));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CLocation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CLocation_.active));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(CLocation_.city, JoinType.LEFT).get(CCity_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CLocation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
