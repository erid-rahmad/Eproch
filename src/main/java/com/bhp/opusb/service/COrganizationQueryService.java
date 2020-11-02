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

import com.bhp.opusb.domain.COrganization;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.COrganizationRepository;
import com.bhp.opusb.service.dto.COrganizationCriteria;
import com.bhp.opusb.service.dto.COrganizationDTO;
import com.bhp.opusb.service.mapper.COrganizationMapper;

/**
 * Service for executing complex queries for {@link COrganization} entities in the database.
 * The main input is a {@link COrganizationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link COrganizationDTO} or a {@link Page} of {@link COrganizationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class COrganizationQueryService extends QueryService<COrganization> {

    private final Logger log = LoggerFactory.getLogger(COrganizationQueryService.class);

    private final COrganizationRepository cOrganizationRepository;

    private final COrganizationMapper cOrganizationMapper;

    public COrganizationQueryService(COrganizationRepository cOrganizationRepository, COrganizationMapper cOrganizationMapper) {
        this.cOrganizationRepository = cOrganizationRepository;
        this.cOrganizationMapper = cOrganizationMapper;
    }

    /**
     * Return a {@link List} of {@link COrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<COrganizationDTO> findByCriteria(COrganizationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<COrganization> specification = createSpecification(criteria);
        return cOrganizationMapper.toDto(cOrganizationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link COrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<COrganizationDTO> findByCriteria(COrganizationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<COrganization> specification = createSpecification(criteria);
        return cOrganizationRepository.findAll(specification, page)
            .map(cOrganizationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(COrganizationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<COrganization> specification = createSpecification(criteria);
        return cOrganizationRepository.count(specification);
    }

    /**
     * Function to convert {@link COrganizationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<COrganization> createSpecification(COrganizationCriteria criteria) {
        Specification<COrganization> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), COrganization_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), COrganization_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), COrganization_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), COrganization_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), COrganization_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), COrganization_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(COrganization_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
