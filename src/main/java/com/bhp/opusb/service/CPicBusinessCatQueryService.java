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

import com.bhp.opusb.domain.CPicBusinessCat;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CPicBusinessCatRepository;
import com.bhp.opusb.service.dto.CPicBusinessCatCriteria;
import com.bhp.opusb.service.dto.CPicBusinessCatDTO;
import com.bhp.opusb.service.mapper.CPicBusinessCatMapper;

/**
 * Service for executing complex queries for {@link CPicBusinessCat} entities in the database.
 * The main input is a {@link CPicBusinessCatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CPicBusinessCatDTO} or a {@link Page} of {@link CPicBusinessCatDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CPicBusinessCatQueryService extends QueryService<CPicBusinessCat> {

    private final Logger log = LoggerFactory.getLogger(CPicBusinessCatQueryService.class);

    private final CPicBusinessCatRepository cPicBusinessCatRepository;

    private final CPicBusinessCatMapper cPicBusinessCatMapper;

    public CPicBusinessCatQueryService(CPicBusinessCatRepository cPicBusinessCatRepository, CPicBusinessCatMapper cPicBusinessCatMapper) {
        this.cPicBusinessCatRepository = cPicBusinessCatRepository;
        this.cPicBusinessCatMapper = cPicBusinessCatMapper;
    }

    /**
     * Return a {@link List} of {@link CPicBusinessCatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CPicBusinessCatDTO> findByCriteria(CPicBusinessCatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CPicBusinessCat> specification = createSpecification(criteria);
        return cPicBusinessCatMapper.toDto(cPicBusinessCatRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CPicBusinessCatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CPicBusinessCatDTO> findByCriteria(CPicBusinessCatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CPicBusinessCat> specification = createSpecification(criteria);
        return cPicBusinessCatRepository.findAll(specification, page)
            .map(cPicBusinessCatMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CPicBusinessCatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CPicBusinessCat> specification = createSpecification(criteria);
        return cPicBusinessCatRepository.count(specification);
    }

    /**
     * Function to convert {@link CPicBusinessCatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CPicBusinessCat> createSpecification(CPicBusinessCatCriteria criteria) {
        Specification<CPicBusinessCat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CPicBusinessCat_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CPicBusinessCat_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CPicBusinessCat_.active));
            }
            if (criteria.getPicUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getPicUserId(),
                    root -> root.join(CPicBusinessCat_.pic, JoinType.LEFT).get(AdUser_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(CPicBusinessCat_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CPicBusinessCat_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
