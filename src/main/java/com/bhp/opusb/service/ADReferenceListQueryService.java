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

import com.bhp.opusb.domain.ADReferenceList;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADReferenceListRepository;
import com.bhp.opusb.service.dto.ADReferenceListCriteria;
import com.bhp.opusb.service.dto.ADReferenceListDTO;
import com.bhp.opusb.service.mapper.ADReferenceListMapper;

/**
 * Service for executing complex queries for {@link ADReferenceList} entities in the database.
 * The main input is a {@link ADReferenceListCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADReferenceListDTO} or a {@link Page} of {@link ADReferenceListDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADReferenceListQueryService extends QueryService<ADReferenceList> {

    private final Logger log = LoggerFactory.getLogger(ADReferenceListQueryService.class);

    private final ADReferenceListRepository aDReferenceListRepository;

    private final ADReferenceListMapper aDReferenceListMapper;

    public ADReferenceListQueryService(ADReferenceListRepository aDReferenceListRepository, ADReferenceListMapper aDReferenceListMapper) {
        this.aDReferenceListRepository = aDReferenceListRepository;
        this.aDReferenceListMapper = aDReferenceListMapper;
    }

    /**
     * Return a {@link List} of {@link ADReferenceListDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADReferenceListDTO> findByCriteria(ADReferenceListCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADReferenceList> specification = createSpecification(criteria);
        return aDReferenceListMapper.toDto(aDReferenceListRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADReferenceListDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADReferenceListDTO> findByCriteria(ADReferenceListCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADReferenceList> specification = createSpecification(criteria);
        return aDReferenceListRepository.findAll(specification, page)
            .map(aDReferenceListMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADReferenceListCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADReferenceList> specification = createSpecification(criteria);
        return aDReferenceListRepository.count(specification);
    }

    /**
     * Function to convert {@link ADReferenceListCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADReferenceList> createSpecification(ADReferenceListCriteria criteria) {
        Specification<ADReferenceList> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADReferenceList_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADReferenceList_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ADReferenceList_.value));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ADReferenceList_.description));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADReferenceList_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ADReferenceList_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdReferenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdReferenceId(),
                    root -> root.join(ADReferenceList_.adReference, JoinType.LEFT).get(ADReference_.id)));
            }
        }
        return specification;
    }
}
