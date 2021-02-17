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

import com.bhp.opusb.domain.ScAccess;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ScAccessRepository;
import com.bhp.opusb.service.dto.ScAccessCriteria;
import com.bhp.opusb.service.dto.ScAccessDTO;
import com.bhp.opusb.service.mapper.ScAccessMapper;

/**
 * Service for executing complex queries for {@link ScAccess} entities in the database.
 * The main input is a {@link ScAccessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScAccessDTO} or a {@link Page} of {@link ScAccessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScAccessQueryService extends QueryService<ScAccess> {

    private final Logger log = LoggerFactory.getLogger(ScAccessQueryService.class);

    private final ScAccessRepository scAccessRepository;

    private final ScAccessMapper scAccessMapper;

    public ScAccessQueryService(ScAccessRepository scAccessRepository, ScAccessMapper scAccessMapper) {
        this.scAccessRepository = scAccessRepository;
        this.scAccessMapper = scAccessMapper;
    }

    /**
     * Return a {@link List} of {@link ScAccessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScAccessDTO> findByCriteria(ScAccessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ScAccess> specification = createSpecification(criteria);
        return scAccessMapper.toDto(scAccessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScAccessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScAccessDTO> findByCriteria(ScAccessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ScAccess> specification = createSpecification(criteria);
        return scAccessRepository.findAll(specification, page)
            .map(scAccessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScAccessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ScAccess> specification = createSpecification(criteria);
        return scAccessRepository.count(specification);
    }

    /**
     * Function to convert {@link ScAccessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ScAccess> createSpecification(ScAccessCriteria criteria) {
        Specification<ScAccess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ScAccess_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), ScAccess_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ScAccess_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ScAccess_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ScAccess_.description));
            }
            if (criteria.getCanWrite() != null) {
                specification = specification.and(buildSpecification(criteria.getCanWrite(), ScAccess_.canWrite));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ScAccess_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(ScAccess_.type, JoinType.INNER).get(ScAccessType_.id)));
            }
            if (criteria.getTypeName() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeName(),
                    root -> root.join(ScAccess_.type, JoinType.INNER).get(ScAccessType_.name)));
            }
            if (criteria.getWindowId() != null) {
                specification = specification.and(buildSpecification(criteria.getWindowId(),
                    root -> root.join(ScAccess_.window, JoinType.LEFT).get(ADWindow_.id)));
            }
            if (criteria.getFormId() != null) {
                specification = specification.and(buildSpecification(criteria.getFormId(),
                    root -> root.join(ScAccess_.form, JoinType.LEFT).get(AdForm_.id)));
            }
            if (criteria.getPaDashboardId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaDashboardId(),
                    root -> root.join(ScAccess_.paDashboard, JoinType.LEFT).get(PaDashboard_.id)));
            }
            if (criteria.getPaDashboardItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaDashboardItemId(),
                    root -> root.join(ScAccess_.paDashboardItem, JoinType.LEFT).get(PaDashboardItem_.id)));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(ScAccess_.documentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getReferenceListId() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenceListId(),
                    root -> root.join(ScAccess_.referenceList, JoinType.LEFT).get(ADReferenceList_.id)));
            }
            if (criteria.getAuthorityId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthorityId(),
                    root -> root.join(ScAccess_.authority, JoinType.INNER).get(ScAuthority_.id)));
            }
            if (criteria.getAuthorityName() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthorityName(),
                    root -> root
                        .join(ScAccess_.authority, JoinType.INNER)
                        .join(ScAuthority_.authority, JoinType.INNER).get(Authority_.name)));
            }
        }
        return specification;
    }
}
