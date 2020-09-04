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

import com.bhp.opusb.domain.CConvertionType;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CConvertionTypeRepository;
import com.bhp.opusb.service.dto.CConvertionTypeCriteria;
import com.bhp.opusb.service.dto.CConvertionTypeDTO;
import com.bhp.opusb.service.mapper.CConvertionTypeMapper;

/**
 * Service for executing complex queries for {@link CConvertionType} entities in the database.
 * The main input is a {@link CConvertionTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CConvertionTypeDTO} or a {@link Page} of {@link CConvertionTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CConvertionTypeQueryService extends QueryService<CConvertionType> {

    private final Logger log = LoggerFactory.getLogger(CConvertionTypeQueryService.class);

    private final CConvertionTypeRepository cConvertionTypeRepository;

    private final CConvertionTypeMapper cConvertionTypeMapper;

    public CConvertionTypeQueryService(CConvertionTypeRepository cConvertionTypeRepository, CConvertionTypeMapper cConvertionTypeMapper) {
        this.cConvertionTypeRepository = cConvertionTypeRepository;
        this.cConvertionTypeMapper = cConvertionTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CConvertionTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CConvertionTypeDTO> findByCriteria(CConvertionTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CConvertionType> specification = createSpecification(criteria);
        return cConvertionTypeMapper.toDto(cConvertionTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CConvertionTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CConvertionTypeDTO> findByCriteria(CConvertionTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CConvertionType> specification = createSpecification(criteria);
        return cConvertionTypeRepository.findAll(specification, page)
            .map(cConvertionTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CConvertionTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CConvertionType> specification = createSpecification(criteria);
        return cConvertionTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CConvertionTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CConvertionType> createSpecification(CConvertionTypeCriteria criteria) {
        Specification<CConvertionType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CConvertionType_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CConvertionType_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CConvertionType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CConvertionType_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CConvertionType_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CConvertionType_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CConvertionType_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
