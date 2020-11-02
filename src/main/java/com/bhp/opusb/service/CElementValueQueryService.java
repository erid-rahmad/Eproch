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

import com.bhp.opusb.domain.CElementValue;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CElementValueRepository;
import com.bhp.opusb.service.dto.CElementValueCriteria;
import com.bhp.opusb.service.dto.CElementValueDTO;
import com.bhp.opusb.service.mapper.CElementValueMapper;

/**
 * Service for executing complex queries for {@link CElementValue} entities in the database.
 * The main input is a {@link CElementValueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CElementValueDTO} or a {@link Page} of {@link CElementValueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CElementValueQueryService extends QueryService<CElementValue> {

    private final Logger log = LoggerFactory.getLogger(CElementValueQueryService.class);

    private final CElementValueRepository cElementValueRepository;

    private final CElementValueMapper cElementValueMapper;

    public CElementValueQueryService(CElementValueRepository cElementValueRepository, CElementValueMapper cElementValueMapper) {
        this.cElementValueRepository = cElementValueRepository;
        this.cElementValueMapper = cElementValueMapper;
    }

    /**
     * Return a {@link List} of {@link CElementValueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CElementValueDTO> findByCriteria(CElementValueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CElementValue> specification = createSpecification(criteria);
        return cElementValueMapper.toDto(cElementValueRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CElementValueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CElementValueDTO> findByCriteria(CElementValueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CElementValue> specification = createSpecification(criteria);
        return cElementValueRepository.findAll(specification, page)
            .map(cElementValueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CElementValueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CElementValue> specification = createSpecification(criteria);
        return cElementValueRepository.count(specification);
    }

    /**
     * Function to convert {@link CElementValueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CElementValue> createSpecification(CElementValueCriteria criteria) {
        Specification<CElementValue> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CElementValue_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CElementValue_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CElementValue_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CElementValue_.type));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CElementValue_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CElementValue_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CElementValue_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CElementValue_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
