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

import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CDocumentTypeRepository;
import com.bhp.opusb.service.dto.CDocumentTypeCriteria;
import com.bhp.opusb.service.dto.CDocumentTypeDTO;
import com.bhp.opusb.service.mapper.CDocumentTypeMapper;

/**
 * Service for executing complex queries for {@link CDocumentType} entities in the database.
 * The main input is a {@link CDocumentTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CDocumentTypeDTO} or a {@link Page} of {@link CDocumentTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CDocumentTypeQueryService extends QueryService<CDocumentType> {

    private final Logger log = LoggerFactory.getLogger(CDocumentTypeQueryService.class);

    private final CDocumentTypeRepository cDocumentTypeRepository;

    private final CDocumentTypeMapper cDocumentTypeMapper;

    public CDocumentTypeQueryService(CDocumentTypeRepository cDocumentTypeRepository, CDocumentTypeMapper cDocumentTypeMapper) {
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.cDocumentTypeMapper = cDocumentTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CDocumentTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CDocumentTypeDTO> findByCriteria(CDocumentTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CDocumentType> specification = createSpecification(criteria);
        return cDocumentTypeMapper.toDto(cDocumentTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CDocumentTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CDocumentTypeDTO> findByCriteria(CDocumentTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CDocumentType> specification = createSpecification(criteria);
        return cDocumentTypeRepository.findAll(specification, page)
            .map(cDocumentTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CDocumentTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CDocumentType> specification = createSpecification(criteria);
        return cDocumentTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CDocumentTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CDocumentType> createSpecification(CDocumentTypeCriteria criteria) {
        Specification<CDocumentType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CDocumentType_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CDocumentType_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CDocumentType_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CDocumentType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CDocumentType_.description));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CDocumentType_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
