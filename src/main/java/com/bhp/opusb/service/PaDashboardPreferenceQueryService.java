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

import com.bhp.opusb.domain.PaDashboardPreference;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.PaDashboardPreferenceRepository;
import com.bhp.opusb.service.dto.PaDashboardPreferenceCriteria;
import com.bhp.opusb.service.dto.PaDashboardPreferenceDTO;
import com.bhp.opusb.service.mapper.PaDashboardPreferenceMapper;

/**
 * Service for executing complex queries for {@link PaDashboardPreference} entities in the database.
 * The main input is a {@link PaDashboardPreferenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaDashboardPreferenceDTO} or a {@link Page} of {@link PaDashboardPreferenceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaDashboardPreferenceQueryService extends QueryService<PaDashboardPreference> {

    private final Logger log = LoggerFactory.getLogger(PaDashboardPreferenceQueryService.class);

    private final PaDashboardPreferenceRepository paDashboardPreferenceRepository;

    private final PaDashboardPreferenceMapper paDashboardPreferenceMapper;

    public PaDashboardPreferenceQueryService(PaDashboardPreferenceRepository paDashboardPreferenceRepository, PaDashboardPreferenceMapper paDashboardPreferenceMapper) {
        this.paDashboardPreferenceRepository = paDashboardPreferenceRepository;
        this.paDashboardPreferenceMapper = paDashboardPreferenceMapper;
    }

    /**
     * Return a {@link List} of {@link PaDashboardPreferenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaDashboardPreferenceDTO> findByCriteria(PaDashboardPreferenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaDashboardPreference> specification = createSpecification(criteria);
        return paDashboardPreferenceMapper.toDto(paDashboardPreferenceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaDashboardPreferenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaDashboardPreferenceDTO> findByCriteria(PaDashboardPreferenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaDashboardPreference> specification = createSpecification(criteria);
        return paDashboardPreferenceRepository.findAll(specification, page)
            .map(paDashboardPreferenceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaDashboardPreferenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaDashboardPreference> specification = createSpecification(criteria);
        return paDashboardPreferenceRepository.count(specification);
    }

    /**
     * Function to convert {@link PaDashboardPreferenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaDashboardPreference> createSpecification(PaDashboardPreferenceCriteria criteria) {
        Specification<PaDashboardPreference> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaDashboardPreference_.id));
            }
            if (criteria.getColumnNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColumnNo(), PaDashboardPreference_.columnNo));
            }
            if (criteria.getRowNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRowNo(), PaDashboardPreference_.rowNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), PaDashboardPreference_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), PaDashboardPreference_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(PaDashboardPreference_.adOrganization, JoinType.INNER).get(ADOrganization_.id)));
            }
            if (criteria.getAdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdUserId(),
                    root -> root.join(PaDashboardPreference_.adUser, JoinType.INNER).get(AdUser_.id)));
            }
            if (criteria.getAdUserName() != null) {
                specification = specification.and(buildSpecification(criteria.getAdUserName(),
                    root -> root
                        .join(PaDashboardPreference_.adUser, JoinType.INNER)
                        .join(AdUser_.user, JoinType.INNER).get(User_.login)));
            }
            if (criteria.getPaDashboardItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaDashboardItemId(),
                    root -> root.join(PaDashboardPreference_.paDashboardItem, JoinType.INNER).get(PaDashboardItem_.id)));
            }
        }
        return specification;
    }
}
