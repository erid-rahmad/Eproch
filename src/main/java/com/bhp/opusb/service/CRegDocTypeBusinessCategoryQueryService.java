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

import com.bhp.opusb.domain.CRegDocTypeBusinessCategory;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CRegDocTypeBusinessCategoryRepository;
import com.bhp.opusb.service.dto.CRegDocTypeBusinessCategoryCriteria;
import com.bhp.opusb.service.dto.CRegDocTypeBusinessCategoryDTO;
import com.bhp.opusb.service.mapper.CRegDocTypeBusinessCategoryMapper;

/**
 * Service for executing complex queries for {@link CRegDocTypeBusinessCategory} entities in the database.
 * The main input is a {@link CRegDocTypeBusinessCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CRegDocTypeBusinessCategoryDTO} or a {@link Page} of {@link CRegDocTypeBusinessCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CRegDocTypeBusinessCategoryQueryService extends QueryService<CRegDocTypeBusinessCategory> {

    private final Logger log = LoggerFactory.getLogger(CRegDocTypeBusinessCategoryQueryService.class);

    private final CRegDocTypeBusinessCategoryRepository cRegDocTypeBusinessCategoryRepository;

    private final CRegDocTypeBusinessCategoryMapper cRegDocTypeBusinessCategoryMapper;

    public CRegDocTypeBusinessCategoryQueryService(CRegDocTypeBusinessCategoryRepository cRegDocTypeBusinessCategoryRepository, CRegDocTypeBusinessCategoryMapper cRegDocTypeBusinessCategoryMapper) {
        this.cRegDocTypeBusinessCategoryRepository = cRegDocTypeBusinessCategoryRepository;
        this.cRegDocTypeBusinessCategoryMapper = cRegDocTypeBusinessCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link CRegDocTypeBusinessCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CRegDocTypeBusinessCategoryDTO> findByCriteria(CRegDocTypeBusinessCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CRegDocTypeBusinessCategory> specification = createSpecification(criteria);
        return cRegDocTypeBusinessCategoryMapper.toDto(cRegDocTypeBusinessCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CRegDocTypeBusinessCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CRegDocTypeBusinessCategoryDTO> findByCriteria(CRegDocTypeBusinessCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CRegDocTypeBusinessCategory> specification = createSpecification(criteria);
        return cRegDocTypeBusinessCategoryRepository.findAll(specification, page)
            .map(cRegDocTypeBusinessCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CRegDocTypeBusinessCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CRegDocTypeBusinessCategory> specification = createSpecification(criteria);
        return cRegDocTypeBusinessCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CRegDocTypeBusinessCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CRegDocTypeBusinessCategory> createSpecification(CRegDocTypeBusinessCategoryCriteria criteria) {
        Specification<CRegDocTypeBusinessCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CRegDocTypeBusinessCategory_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CRegDocTypeBusinessCategory_.uid));
            }
            if (criteria.getMandatory() != null) {
                specification = specification.and(buildSpecification(criteria.getMandatory(), CRegDocTypeBusinessCategory_.mandatory));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CRegDocTypeBusinessCategory_.active));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(CRegDocTypeBusinessCategory_.documentType, JoinType.LEFT).get(CRegistrationDocType_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(CRegDocTypeBusinessCategory_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CRegDocTypeBusinessCategory_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
