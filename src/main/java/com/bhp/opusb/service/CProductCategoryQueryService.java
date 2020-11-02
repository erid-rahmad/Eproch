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

import com.bhp.opusb.domain.CProductCategory;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CProductCategoryRepository;
import com.bhp.opusb.service.dto.CProductCategoryCriteria;
import com.bhp.opusb.service.dto.CProductCategoryDTO;
import com.bhp.opusb.service.mapper.CProductCategoryMapper;

/**
 * Service for executing complex queries for {@link CProductCategory} entities in the database.
 * The main input is a {@link CProductCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CProductCategoryDTO} or a {@link Page} of {@link CProductCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CProductCategoryQueryService extends QueryService<CProductCategory> {

    private final Logger log = LoggerFactory.getLogger(CProductCategoryQueryService.class);

    private final CProductCategoryRepository cProductCategoryRepository;

    private final CProductCategoryMapper cProductCategoryMapper;

    public CProductCategoryQueryService(CProductCategoryRepository cProductCategoryRepository, CProductCategoryMapper cProductCategoryMapper) {
        this.cProductCategoryRepository = cProductCategoryRepository;
        this.cProductCategoryMapper = cProductCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link CProductCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CProductCategoryDTO> findByCriteria(CProductCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CProductCategory> specification = createSpecification(criteria);
        return cProductCategoryMapper.toDto(cProductCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CProductCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductCategoryDTO> findByCriteria(CProductCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CProductCategory> specification = createSpecification(criteria);
        return cProductCategoryRepository.findAll(specification, page)
            .map(cProductCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CProductCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CProductCategory> specification = createSpecification(criteria);
        return cProductCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CProductCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CProductCategory> createSpecification(CProductCategoryCriteria criteria) {
        Specification<CProductCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CProductCategory_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CProductCategory_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CProductCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CProductCategory_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CProductCategory_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CProductCategory_.active));
            }
            if (criteria.getCProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCProductCategoryId(),
                    root -> root.join(CProductCategory_.cProductCategories, JoinType.LEFT).get(CProductCategory_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CProductCategory_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getParentCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentCategoryId(),
                    root -> root.join(CProductCategory_.parentCategory, JoinType.LEFT).get(CProductCategory_.id)));
            }
        }
        return specification;
    }
}
