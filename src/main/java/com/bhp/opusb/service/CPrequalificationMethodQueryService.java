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

import com.bhp.opusb.domain.CPrequalificationMethod;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPrequalificationMethodRepository;
import com.bhp.opusb.service.dto.CPrequalificationMethodCriteria;
import com.bhp.opusb.service.dto.CPrequalificationMethodDTO;
import com.bhp.opusb.service.mapper.CPrequalificationMethodMapper;

/**
 * Service for executing complex queries for {@link CPrequalificationMethod} entities in the database.
 * The main input is a {@link CPrequalificationMethodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPrequalificationMethodDTO} or a {@link Page} of {@link CPrequalificationMethodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPrequalificationMethodQueryService extends QueryService<CPrequalificationMethod> {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationMethodQueryService.class);

    private final CPrequalificationMethodRepository cPrequalificationMethodRepository;

    private final CPrequalificationMethodMapper cPrequalificationMethodMapper;

    public CPrequalificationMethodQueryService(CPrequalificationMethodRepository cPrequalificationMethodRepository, CPrequalificationMethodMapper cPrequalificationMethodMapper) {
        this.cPrequalificationMethodRepository = cPrequalificationMethodRepository;
        this.cPrequalificationMethodMapper = cPrequalificationMethodMapper;
    }

    /**
     * Return a {@link List} of {@link CPrequalificationMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPrequalificationMethodDTO> findByCriteria(CPrequalificationMethodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPrequalificationMethod> specification = createSpecification(criteria);
        return cPrequalificationMethodMapper.toDto(cPrequalificationMethodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPrequalificationMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalificationMethodDTO> findByCriteria(CPrequalificationMethodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPrequalificationMethod> specification = createSpecification(criteria);
        return cPrequalificationMethodRepository.findAll(specification, page)
            .map(cPrequalificationMethodMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPrequalificationMethodCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPrequalificationMethod> specification = createSpecification(criteria);
        return cPrequalificationMethodRepository.count(specification);
    }

    /**
     * Function to convert {@link CPrequalificationMethodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPrequalificationMethod> createSpecification(CPrequalificationMethodCriteria criteria) {
        Specification<CPrequalificationMethod> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPrequalificationMethod_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CPrequalificationMethod_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPrequalificationMethod_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPrequalificationMethod_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPrequalificationMethod_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
