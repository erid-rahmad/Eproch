package com.bhp.opusb.service;

import java.util.List;

// for static metamodels
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADOrganization_;
import com.bhp.opusb.repository.ADOrganizationRepository;
import com.bhp.opusb.service.dto.ADOrganizationCriteria;
import com.bhp.opusb.service.dto.ADOrganizationDTO;
import com.bhp.opusb.service.mapper.ADOrganizationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ADOrganization} entities in the database.
 * The main input is a {@link ADOrganizationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADOrganizationDTO} or a {@link Page} of {@link ADOrganizationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADOrganizationQueryService extends QueryService<ADOrganization> {

    private final Logger log = LoggerFactory.getLogger(ADOrganizationQueryService.class);

    private final ADOrganizationRepository aDOrganizationRepository;

    private final ADOrganizationMapper aDOrganizationMapper;

    public ADOrganizationQueryService(ADOrganizationRepository aDOrganizationRepository, ADOrganizationMapper aDOrganizationMapper) {
        this.aDOrganizationRepository = aDOrganizationRepository;
        this.aDOrganizationMapper = aDOrganizationMapper;
    }

    /**
     * Return a {@link List} of {@link ADOrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADOrganizationDTO> findByCriteria(ADOrganizationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADOrganization> specification = createSpecification(criteria);
        return aDOrganizationMapper.toDto(aDOrganizationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADOrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADOrganizationDTO> findByCriteria(ADOrganizationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADOrganization> specification = createSpecification(criteria);
        return aDOrganizationRepository.findAll(specification, page)
            .map(aDOrganizationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADOrganizationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADOrganization> specification = createSpecification(criteria);
        return aDOrganizationRepository.count(specification);
    }

    /**
     * Function to convert {@link ADOrganizationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADOrganization> createSpecification(ADOrganizationCriteria criteria) {
        Specification<ADOrganization> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADOrganization_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADOrganization_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ADOrganization_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ADOrganization_.description));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADOrganization_.active));
            }
        }
        return specification;
    }
}
