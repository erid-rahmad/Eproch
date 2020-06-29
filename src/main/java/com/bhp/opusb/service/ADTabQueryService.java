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

import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADTabRepository;
import com.bhp.opusb.service.dto.ADTabCriteria;
import com.bhp.opusb.service.dto.ADTabDTO;
import com.bhp.opusb.service.mapper.ADTabMapper;

/**
 * Service for executing complex queries for {@link ADTab} entities in the database.
 * The main input is a {@link ADTabCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADTabDTO} or a {@link Page} of {@link ADTabDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADTabQueryService extends QueryService<ADTab> {

    private final Logger log = LoggerFactory.getLogger(ADTabQueryService.class);

    private final ADTabRepository aDTabRepository;

    private final ADTabMapper aDTabMapper;

    public ADTabQueryService(ADTabRepository aDTabRepository, ADTabMapper aDTabMapper) {
        this.aDTabRepository = aDTabRepository;
        this.aDTabMapper = aDTabMapper;
    }

    /**
     * Return a {@link List} of {@link ADTabDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADTabDTO> findByCriteria(ADTabCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADTab> specification = createSpecification(criteria);
        return aDTabMapper.toDto(aDTabRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADTabDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADTabDTO> findByCriteria(ADTabCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADTab> specification = createSpecification(criteria);
        return aDTabRepository.findAll(specification, page)
            .map(aDTabMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADTabCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADTab> specification = createSpecification(criteria);
        return aDTabRepository.count(specification);
    }

    /**
     * Function to convert {@link ADTabCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADTab> createSpecification(ADTabCriteria criteria) {
        Specification<ADTab> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADTab_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), ADTab_.uid));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADTab_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ADTab_.description));
            }
            if (criteria.getIconName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconName(), ADTab_.iconName));
            }
            if (criteria.getTargetEndpoint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTargetEndpoint(), ADTab_.targetEndpoint));
            }
            if (criteria.getWritable() != null) {
                specification = specification.and(buildSpecification(criteria.getWritable(), ADTab_.writable));
            }
            if (criteria.getDisplayLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayLogic(), ADTab_.displayLogic));
            }
            if (criteria.getReadOnlyLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadOnlyLogic(), ADTab_.readOnlyLogic));
            }
            if (criteria.getFilterQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilterQuery(), ADTab_.filterQuery));
            }
            if (criteria.getOrderQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderQuery(), ADTab_.orderQuery));
            }
            if (criteria.getTabSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTabSequence(), ADTab_.tabSequence));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADTab_.active));
            }
            if (criteria.getADTabId() != null) {
                specification = specification.and(buildSpecification(criteria.getADTabId(),
                    root -> root.join(ADTab_.aDTabs, JoinType.LEFT).get(ADTab_.id)));
            }
            if (criteria.getADFieldId() != null) {
                specification = specification.and(buildSpecification(criteria.getADFieldId(),
                    root -> root.join(ADTab_.aDFields, JoinType.LEFT).get(ADField_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ADTab_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdTableId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTableId(),
                    root -> root.join(ADTab_.adTable, JoinType.LEFT).get(ADTable_.id)));
            }
            if (criteria.getParentColumnId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentColumnId(),
                    root -> root.join(ADTab_.parentColumn, JoinType.LEFT).get(ADColumn_.id)));
            }
            if (criteria.getForeignColumnId() != null) {
                specification = specification.and(buildSpecification(criteria.getForeignColumnId(),
                    root -> root.join(ADTab_.foreignColumn, JoinType.LEFT).get(ADColumn_.id)));
            }
            if (criteria.getAdWindowId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdWindowId(),
                    root -> root.join(ADTab_.adWindow, JoinType.LEFT).get(ADWindow_.id)));
            }
            if (criteria.getParentTabId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentTabId(),
                    root -> root.join(ADTab_.parentTab, JoinType.LEFT).get(ADTab_.id)));
            }
        }
        return specification;
    }
}
