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

import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CBusinessCategoryRepository;
import com.bhp.opusb.service.dto.CBusinessCategoryCriteria;
import com.bhp.opusb.service.dto.CBusinessCategoryDTO;
import com.bhp.opusb.service.mapper.CBusinessCategoryMapper;

/**
 * Service for executing complex queries for {@link CBusinessCategory} entities in the database.
 * The main input is a {@link CBusinessCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CBusinessCategoryDTO} or a {@link Page} of {@link CBusinessCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CBusinessCategoryQueryService extends QueryService<CBusinessCategory> {

    private final Logger log = LoggerFactory.getLogger(CBusinessCategoryQueryService.class);

    private final CBusinessCategoryRepository cBusinessCategoryRepository;

    private final CBusinessCategoryMapper cBusinessCategoryMapper;

    public CBusinessCategoryQueryService(CBusinessCategoryRepository cBusinessCategoryRepository, CBusinessCategoryMapper cBusinessCategoryMapper) {
        this.cBusinessCategoryRepository = cBusinessCategoryRepository;
        this.cBusinessCategoryMapper = cBusinessCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link CBusinessCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CBusinessCategoryDTO> findByCriteria(CBusinessCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CBusinessCategory> specification = createSpecification(criteria);
        return cBusinessCategoryMapper.toDto(cBusinessCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CBusinessCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CBusinessCategoryDTO> findByCriteria(CBusinessCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CBusinessCategory> specification = createSpecification(criteria);
        return cBusinessCategoryRepository.findAll(specification, page)
            .map(cBusinessCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CBusinessCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CBusinessCategory> specification = createSpecification(criteria);
        return cBusinessCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CBusinessCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CBusinessCategory> createSpecification(CBusinessCategoryCriteria criteria) {
        Specification<CBusinessCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CBusinessCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CBusinessCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CBusinessCategory_.description));
            }
            if (criteria.getSector() != null) {
                specification = specification.and(buildSpecification(criteria.getSector(), CBusinessCategory_.sector));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CBusinessCategory_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CBusinessCategory_.active));
            }
            if (criteria.getCBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCBusinessCategoryId(),
                    root -> root.join(CBusinessCategory_.cBusinessCategories, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CBusinessCategory_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getParentCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentCategoryId(),
                    root -> root.join(CBusinessCategory_.parentCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
        }
        return specification;
    }
}
