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

import com.bhp.opusb.domain.ADField;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADFieldRepository;
import com.bhp.opusb.service.dto.ADFieldCriteria;
import com.bhp.opusb.service.dto.ADFieldDTO;
import com.bhp.opusb.service.mapper.ADFieldMapper;

/**
 * Service for executing complex queries for {@link ADField} entities in the database.
 * The main input is a {@link ADFieldCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADFieldDTO} or a {@link Page} of {@link ADFieldDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADFieldQueryService extends QueryService<ADField> {

    private final Logger log = LoggerFactory.getLogger(ADFieldQueryService.class);

    private final ADFieldRepository aDFieldRepository;

    private final ADFieldMapper aDFieldMapper;

    public ADFieldQueryService(ADFieldRepository aDFieldRepository, ADFieldMapper aDFieldMapper) {
        this.aDFieldRepository = aDFieldRepository;
        this.aDFieldMapper = aDFieldMapper;
    }

    /**
     * Return a {@link List} of {@link ADFieldDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADFieldDTO> findByCriteria(ADFieldCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADField> specification = createSpecification(criteria);
        return aDFieldMapper.toDto(aDFieldRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADFieldDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADFieldDTO> findByCriteria(ADFieldCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADField> specification = createSpecification(criteria);
        return aDFieldRepository.findAll(specification, page)
            .map(aDFieldMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADFieldCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADField> specification = createSpecification(criteria);
        return aDFieldRepository.count(specification);
    }

    /**
     * Function to convert {@link ADFieldCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADField> createSpecification(ADFieldCriteria criteria) {
        Specification<ADField> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADField_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADField_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ADField_.description));
            }
            if (criteria.getHint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHint(), ADField_.hint));
            }
            if (criteria.getStaticText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStaticText(), ADField_.staticText));
            }
            if (criteria.getStaticField() != null) {
                specification = specification.and(buildSpecification(criteria.getStaticField(), ADField_.staticField));
            }
            if (criteria.getLabelOnly() != null) {
                specification = specification.and(buildSpecification(criteria.getLabelOnly(), ADField_.labelOnly));
            }
            if (criteria.getShowLabel() != null) {
                specification = specification.and(buildSpecification(criteria.getShowLabel(), ADField_.showLabel));
            }
            if (criteria.getShowInGrid() != null) {
                specification = specification.and(buildSpecification(criteria.getShowInGrid(), ADField_.showInGrid));
            }
            if (criteria.getShowInDetail() != null) {
                specification = specification.and(buildSpecification(criteria.getShowInDetail(), ADField_.showInDetail));
            }
            if (criteria.getGridSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGridSequence(), ADField_.gridSequence));
            }
            if (criteria.getDetailSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDetailSequence(), ADField_.detailSequence));
            }
            if (criteria.getDisplayLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayLogic(), ADField_.displayLogic));
            }
            if (criteria.getReadOnlyLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadOnlyLogic(), ADField_.readOnlyLogic));
            }
            if (criteria.getWritable() != null) {
                specification = specification.and(buildSpecification(criteria.getWritable(), ADField_.writable));
            }
            if (criteria.getColumnNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColumnNo(), ADField_.columnNo));
            }
            if (criteria.getColumnSpan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColumnSpan(), ADField_.columnSpan));
            }
            if (criteria.getUpdatable() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdatable(), ADField_.updatable));
            }
            if (criteria.getAlwaysUpdatable() != null) {
                specification = specification.and(buildSpecification(criteria.getAlwaysUpdatable(), ADField_.alwaysUpdatable));
            }
            if (criteria.getCopyable() != null) {
                specification = specification.and(buildSpecification(criteria.getCopyable(), ADField_.copyable));
            }
            if (criteria.getDefaultValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultValue(), ADField_.defaultValue));
            }
            if (criteria.getFormatPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormatPattern(), ADField_.formatPattern));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADField_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ADField_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdReferenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdReferenceId(),
                    root -> root.join(ADField_.adReference, JoinType.LEFT).get(ADReference_.id)));
            }
            if (criteria.getAdColumnId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdColumnId(),
                    root -> root.join(ADField_.adColumn, JoinType.LEFT).get(ADColumn_.id)));
            }
            if (criteria.getAdValidationRuleId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdValidationRuleId(),
                    root -> root.join(ADField_.adValidationRule, JoinType.LEFT).get(AdValidationRule_.id)));
            }
            if (criteria.getAdTabId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTabId(),
                    root -> root.join(ADField_.adTab, JoinType.LEFT).get(ADTab_.id)));
            }
        }
        return specification;
    }
}
