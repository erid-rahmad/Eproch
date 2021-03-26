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

import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADColumnRepository;
import com.bhp.opusb.service.dto.ADColumnCriteria;
import com.bhp.opusb.service.dto.ADColumnDTO;
import com.bhp.opusb.service.mapper.ADColumnMapper;

/**
 * Service for executing complex queries for {@link ADColumn} entities in the database.
 * The main input is a {@link ADColumnCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADColumnDTO} or a {@link Page} of {@link ADColumnDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADColumnQueryService extends QueryService<ADColumn> {

    private final Logger log = LoggerFactory.getLogger(ADColumnQueryService.class);

    private final ADColumnRepository aDColumnRepository;

    private final ADColumnMapper aDColumnMapper;

    public ADColumnQueryService(ADColumnRepository aDColumnRepository, ADColumnMapper aDColumnMapper) {
        this.aDColumnRepository = aDColumnRepository;
        this.aDColumnMapper = aDColumnMapper;
    }

    /**
     * Return a {@link List} of {@link ADColumnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADColumnDTO> findByCriteria(ADColumnCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADColumn> specification = createSpecification(criteria);
        return aDColumnMapper.toDto(aDColumnRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADColumnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADColumnDTO> findByCriteria(ADColumnCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADColumn> specification = createSpecification(criteria);
        return aDColumnRepository.findAll(specification, page)
            .map(aDColumnMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADColumnCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADColumn> specification = createSpecification(criteria);
        return aDColumnRepository.count(specification);
    }

    /**
     * Function to convert {@link ADColumnCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADColumn> createSpecification(ADColumnCriteria criteria) {
        Specification<ADColumn> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADColumn_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), ADColumn_.uid));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADColumn_.name));
            }
            if (criteria.getSqlName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSqlName(), ADColumn_.sqlName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ADColumn_.description));
            }
            if (criteria.getFieldLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldLength(), ADColumn_.fieldLength));
            }
            if (criteria.getKey() != null) {
                specification = specification.and(buildSpecification(criteria.getKey(), ADColumn_.key));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), ADColumn_.type));
            }
            if (criteria.getForeignKey() != null) {
                specification = specification.and(buildSpecification(criteria.getForeignKey(), ADColumn_.foreignKey));
            }
            if (criteria.getImportedTable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImportedTable(), ADColumn_.importedTable));
            }
            if (criteria.getImportedColumn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImportedColumn(), ADColumn_.importedColumn));
            }
            if (criteria.getMandatory() != null) {
                specification = specification.and(buildSpecification(criteria.getMandatory(), ADColumn_.mandatory));
            }
            if (criteria.getMandatoryLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMandatoryLogic(), ADColumn_.mandatoryLogic));
            }
            if (criteria.getDisplayLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayLogic(), ADColumn_.displayLogic));
            }
            if (criteria.getReadOnlyLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadOnlyLogic(), ADColumn_.readOnlyLogic));
            }
            if (criteria.getUpdatable() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdatable(), ADColumn_.updatable));
            }
            if (criteria.getAlwaysUpdatable() != null) {
                specification = specification.and(buildSpecification(criteria.getAlwaysUpdatable(), ADColumn_.alwaysUpdatable));
            }
            if (criteria.getCopyable() != null) {
                specification = specification.and(buildSpecification(criteria.getCopyable(), ADColumn_.copyable));
            }
            if (criteria.getDefaultValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultValue(), ADColumn_.defaultValue));
            }
            if (criteria.getFormatPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormatPattern(), ADColumn_.formatPattern));
            }
            if (criteria.getMinLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinLength(), ADColumn_.minLength));
            }
            if (criteria.getMaxLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxLength(), ADColumn_.maxLength));
            }
            if (criteria.getMinValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinValue(), ADColumn_.minValue));
            }
            if (criteria.getMaxValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxValue(), ADColumn_.maxValue));
            }
            if (criteria.getIdentifier() != null) {
                specification = specification.and(buildSpecification(criteria.getIdentifier(), ADColumn_.identifier));
            }
            if (criteria.getDefaultSelection() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultSelection(), ADColumn_.defaultSelection));
            }
            if (criteria.getSelectionSequence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSelectionSequence(), ADColumn_.selectionSequence));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADColumn_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ADColumn_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getReferenceTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenceTypeId(),
                    root -> root.join(ADColumn_.referenceType, JoinType.LEFT).get(ADReference_.id)));
            }
            if (criteria.getAdReferenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdReferenceId(),
                    root -> root.join(ADColumn_.adReference, JoinType.LEFT).get(ADReference_.id)));
            }
            if (criteria.getAdValidationRuleId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdValidationRuleId(),
                    root -> root.join(ADColumn_.adValidationRule, JoinType.LEFT).get(AdValidationRule_.id)));
            }
            if (criteria.getAdTableId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTableId(),
                    root -> root.join(ADColumn_.adTable, JoinType.LEFT).get(ADTable_.id)));
            }
        }
        return specification;
    }
}
