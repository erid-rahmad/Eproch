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

import com.bhp.opusb.domain.CEventType;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CEventTypeRepository;
import com.bhp.opusb.service.dto.CEventTypeCriteria;
import com.bhp.opusb.service.dto.CEventTypeDTO;
import com.bhp.opusb.service.mapper.CEventTypeMapper;

/**
 * Service for executing complex queries for {@link CEventType} entities in the database.
 * The main input is a {@link CEventTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CEventTypeDTO} or a {@link Page} of {@link CEventTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CEventTypeQueryService extends QueryService<CEventType> {

    private final Logger log = LoggerFactory.getLogger(CEventTypeQueryService.class);

    private final CEventTypeRepository cEventTypeRepository;

    private final CEventTypeMapper cEventTypeMapper;

    public CEventTypeQueryService(CEventTypeRepository cEventTypeRepository, CEventTypeMapper cEventTypeMapper) {
        this.cEventTypeRepository = cEventTypeRepository;
        this.cEventTypeMapper = cEventTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CEventTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CEventTypeDTO> findByCriteria(CEventTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CEventType> specification = createSpecification(criteria);
        return cEventTypeMapper.toDto(cEventTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CEventTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CEventTypeDTO> findByCriteria(CEventTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CEventType> specification = createSpecification(criteria);
        return cEventTypeRepository.findAll(specification, page)
            .map(cEventTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CEventTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CEventType> specification = createSpecification(criteria);
        return cEventTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CEventTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CEventType> createSpecification(CEventTypeCriteria criteria) {
        Specification<CEventType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CEventType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CEventType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CEventType_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CEventType_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CEventType_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CEventType_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBindingTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getBindingTypeId(),
                    root -> root.join(CEventType_.bindingType, JoinType.LEFT).get(CBiddingType_.id)));
            }
        }
        return specification;
    }
}
