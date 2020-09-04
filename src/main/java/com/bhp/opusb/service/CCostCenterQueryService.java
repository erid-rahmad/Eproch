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

import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CCostCenterRepository;
import com.bhp.opusb.service.dto.CCostCenterCriteria;
import com.bhp.opusb.service.dto.CCostCenterDTO;
import com.bhp.opusb.service.mapper.CCostCenterMapper;

/**
 * Service for executing complex queries for {@link CCostCenter} entities in the database.
 * The main input is a {@link CCostCenterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CCostCenterDTO} or a {@link Page} of {@link CCostCenterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CCostCenterQueryService extends QueryService<CCostCenter> {

    private final Logger log = LoggerFactory.getLogger(CCostCenterQueryService.class);

    private final CCostCenterRepository cCostCenterRepository;

    private final CCostCenterMapper cCostCenterMapper;

    public CCostCenterQueryService(CCostCenterRepository cCostCenterRepository, CCostCenterMapper cCostCenterMapper) {
        this.cCostCenterRepository = cCostCenterRepository;
        this.cCostCenterMapper = cCostCenterMapper;
    }

    /**
     * Return a {@link List} of {@link CCostCenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CCostCenterDTO> findByCriteria(CCostCenterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CCostCenter> specification = createSpecification(criteria);
        return cCostCenterMapper.toDto(cCostCenterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CCostCenterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CCostCenterDTO> findByCriteria(CCostCenterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CCostCenter> specification = createSpecification(criteria);
        return cCostCenterRepository.findAll(specification, page)
            .map(cCostCenterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CCostCenterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CCostCenter> specification = createSpecification(criteria);
        return cCostCenterRepository.count(specification);
    }

    /**
     * Function to convert {@link CCostCenterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CCostCenter> createSpecification(CCostCenterCriteria criteria) {
        Specification<CCostCenter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CCostCenter_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CCostCenter_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CCostCenter_.name));
            }
            if (criteria.getDivision() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDivision(), CCostCenter_.division));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CCostCenter_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CCostCenter_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CCostCenter_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CCostCenter_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
