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

import com.bhp.opusb.domain.CRegistrationDocType;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CRegistrationDocTypeRepository;
import com.bhp.opusb.service.dto.CRegistrationDocTypeCriteria;
import com.bhp.opusb.service.dto.CRegistrationDocTypeDTO;
import com.bhp.opusb.service.mapper.CRegistrationDocTypeMapper;

/**
 * Service for executing complex queries for {@link CRegistrationDocType} entities in the database.
 * The main input is a {@link CRegistrationDocTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CRegistrationDocTypeDTO} or a {@link Page} of {@link CRegistrationDocTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CRegistrationDocTypeQueryService extends QueryService<CRegistrationDocType> {

    private final Logger log = LoggerFactory.getLogger(CRegistrationDocTypeQueryService.class);

    private final CRegistrationDocTypeRepository cRegistrationDocTypeRepository;

    private final CRegistrationDocTypeMapper cRegistrationDocTypeMapper;

    public CRegistrationDocTypeQueryService(CRegistrationDocTypeRepository cRegistrationDocTypeRepository, CRegistrationDocTypeMapper cRegistrationDocTypeMapper) {
        this.cRegistrationDocTypeRepository = cRegistrationDocTypeRepository;
        this.cRegistrationDocTypeMapper = cRegistrationDocTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CRegistrationDocTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CRegistrationDocTypeDTO> findByCriteria(CRegistrationDocTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CRegistrationDocType> specification = createSpecification(criteria);
        return cRegistrationDocTypeMapper.toDto(cRegistrationDocTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CRegistrationDocTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CRegistrationDocTypeDTO> findByCriteria(CRegistrationDocTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CRegistrationDocType> specification = createSpecification(criteria);
        return cRegistrationDocTypeRepository.findAll(specification, page)
            .map(cRegistrationDocTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CRegistrationDocTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CRegistrationDocType> specification = createSpecification(criteria);
        return cRegistrationDocTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CRegistrationDocTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CRegistrationDocType> createSpecification(CRegistrationDocTypeCriteria criteria) {
        Specification<CRegistrationDocType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CRegistrationDocType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CRegistrationDocType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CRegistrationDocType_.description));
            }
            if (criteria.getHasExpirationDate() != null) {
                specification = specification.and(buildSpecification(criteria.getHasExpirationDate(), CRegistrationDocType_.hasExpirationDate));
            }
            if (criteria.getMandatoryBusinessCategories() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMandatoryBusinessCategories(), CRegistrationDocType_.mandatoryBusinessCategories));
            }
            if (criteria.getAdditionalBusinessCategories() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalBusinessCategories(), CRegistrationDocType_.additionalBusinessCategories));
            }
            if (criteria.getMandatoryForCompany() != null) {
                specification = specification.and(buildSpecification(criteria.getMandatoryForCompany(), CRegistrationDocType_.mandatoryForCompany));
            }
            if (criteria.getAdditionalForCompany() != null) {
                specification = specification.and(buildSpecification(criteria.getAdditionalForCompany(), CRegistrationDocType_.additionalForCompany));
            }
            if (criteria.getMandatoryForProfessional() != null) {
                specification = specification.and(buildSpecification(criteria.getMandatoryForProfessional(), CRegistrationDocType_.mandatoryForProfessional));
            }
            if (criteria.getAdditionalForProfessional() != null) {
                specification = specification.and(buildSpecification(criteria.getAdditionalForProfessional(), CRegistrationDocType_.additionalForProfessional));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CRegistrationDocType_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CRegistrationDocType_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CRegistrationDocType_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
