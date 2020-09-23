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

import com.bhp.opusb.domain.CProductClassification;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CProductClassificationRepository;
import com.bhp.opusb.service.dto.CProductClassificationCriteria;
import com.bhp.opusb.service.dto.CProductClassificationDTO;
import com.bhp.opusb.service.mapper.CProductClassificationMapper;

/**
 * Service for executing complex queries for {@link CProductClassification} entities in the database.
 * The main input is a {@link CProductClassificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CProductClassificationDTO} or a {@link Page} of {@link CProductClassificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CProductClassificationQueryService extends QueryService<CProductClassification> {

    private final Logger log = LoggerFactory.getLogger(CProductClassificationQueryService.class);

    private final CProductClassificationRepository cProductClassificationRepository;

    private final CProductClassificationMapper cProductClassificationMapper;

    public CProductClassificationQueryService(CProductClassificationRepository cProductClassificationRepository, CProductClassificationMapper cProductClassificationMapper) {
        this.cProductClassificationRepository = cProductClassificationRepository;
        this.cProductClassificationMapper = cProductClassificationMapper;
    }

    /**
     * Return a {@link List} of {@link CProductClassificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CProductClassificationDTO> findByCriteria(CProductClassificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CProductClassification> specification = createSpecification(criteria);
        return cProductClassificationMapper.toDto(cProductClassificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CProductClassificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductClassificationDTO> findByCriteria(CProductClassificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CProductClassification> specification = createSpecification(criteria);
        return cProductClassificationRepository.findAll(specification, page)
            .map(cProductClassificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CProductClassificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CProductClassification> specification = createSpecification(criteria);
        return cProductClassificationRepository.count(specification);
    }

    /**
     * Function to convert {@link CProductClassificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CProductClassification> createSpecification(CProductClassificationCriteria criteria) {
        Specification<CProductClassification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CProductClassification_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CProductClassification_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CProductClassification_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CProductClassification_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CProductClassification_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CProductClassification_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
