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

import com.bhp.opusb.domain.CQuestionCategory;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CQuestionCategoryRepository;
import com.bhp.opusb.service.dto.CQuestionCategoryCriteria;
import com.bhp.opusb.service.dto.CQuestionCategoryDTO;
import com.bhp.opusb.service.mapper.CQuestionCategoryMapper;

/**
 * Service for executing complex queries for {@link CQuestionCategory} entities in the database.
 * The main input is a {@link CQuestionCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CQuestionCategoryDTO} or a {@link Page} of {@link CQuestionCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CQuestionCategoryQueryService extends QueryService<CQuestionCategory> {

    private final Logger log = LoggerFactory.getLogger(CQuestionCategoryQueryService.class);

    private final CQuestionCategoryRepository cQuestionCategoryRepository;

    private final CQuestionCategoryMapper cQuestionCategoryMapper;

    public CQuestionCategoryQueryService(CQuestionCategoryRepository cQuestionCategoryRepository, CQuestionCategoryMapper cQuestionCategoryMapper) {
        this.cQuestionCategoryRepository = cQuestionCategoryRepository;
        this.cQuestionCategoryMapper = cQuestionCategoryMapper;
    }

    /**
     * Return a {@link List} of {@link CQuestionCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CQuestionCategoryDTO> findByCriteria(CQuestionCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CQuestionCategory> specification = createSpecification(criteria);
        return cQuestionCategoryMapper.toDto(cQuestionCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CQuestionCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CQuestionCategoryDTO> findByCriteria(CQuestionCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CQuestionCategory> specification = createSpecification(criteria);
        return cQuestionCategoryRepository.findAll(specification, page)
            .map(cQuestionCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CQuestionCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CQuestionCategory> specification = createSpecification(criteria);
        return cQuestionCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CQuestionCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CQuestionCategory> createSpecification(CQuestionCategoryCriteria criteria) {
        Specification<CQuestionCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CQuestionCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CQuestionCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CQuestionCategory_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CQuestionCategory_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CQuestionCategory_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CQuestionCategory_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
