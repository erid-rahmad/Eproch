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

import com.bhp.opusb.domain.CVendorBusinessCat;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CVendorBusinessCatRepository;
import com.bhp.opusb.service.dto.CVendorBusinessCatCriteria;
import com.bhp.opusb.service.dto.CVendorBusinessCatDTO;
import com.bhp.opusb.service.mapper.CVendorBusinessCatMapper;

/**
 * Service for executing complex queries for {@link CVendorBusinessCat} entities in the database.
 * The main input is a {@link CVendorBusinessCatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CVendorBusinessCatDTO} or a {@link Page} of {@link CVendorBusinessCatDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CVendorBusinessCatQueryService extends QueryService<CVendorBusinessCat> {

    private final Logger log = LoggerFactory.getLogger(CVendorBusinessCatQueryService.class);

    private final CVendorBusinessCatRepository cVendorBusinessCatRepository;

    private final CVendorBusinessCatMapper cVendorBusinessCatMapper;

    public CVendorBusinessCatQueryService(CVendorBusinessCatRepository cVendorBusinessCatRepository, CVendorBusinessCatMapper cVendorBusinessCatMapper) {
        this.cVendorBusinessCatRepository = cVendorBusinessCatRepository;
        this.cVendorBusinessCatMapper = cVendorBusinessCatMapper;
    }

    /**
     * Return a {@link List} of {@link CVendorBusinessCatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CVendorBusinessCatDTO> findByCriteria(CVendorBusinessCatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CVendorBusinessCat> specification = createSpecification(criteria);
        return cVendorBusinessCatMapper.toDto(cVendorBusinessCatRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CVendorBusinessCatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorBusinessCatDTO> findByCriteria(CVendorBusinessCatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CVendorBusinessCat> specification = createSpecification(criteria);
        return cVendorBusinessCatRepository.findAll(specification, page)
            .map(cVendorBusinessCatMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CVendorBusinessCatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CVendorBusinessCat> specification = createSpecification(criteria);
        return cVendorBusinessCatRepository.count(specification);
    }

    /**
     * Function to convert {@link CVendorBusinessCatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CVendorBusinessCat> createSpecification(CVendorBusinessCatCriteria criteria) {
        Specification<CVendorBusinessCat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CVendorBusinessCat_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CVendorBusinessCat_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CVendorBusinessCat_.active));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CVendorBusinessCat_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getBusinessClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessClassificationId(),
                    root -> root.join(CVendorBusinessCat_.businessClassification, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(CVendorBusinessCat_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getSubBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubBusinessCategoryId(),
                    root -> root.join(CVendorBusinessCat_.subBusinessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CVendorBusinessCat_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
