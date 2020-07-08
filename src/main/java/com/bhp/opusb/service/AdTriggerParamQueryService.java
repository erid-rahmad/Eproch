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

import com.bhp.opusb.domain.AdTriggerParam;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdTriggerParamRepository;
import com.bhp.opusb.service.dto.AdTriggerParamCriteria;
import com.bhp.opusb.service.dto.AdTriggerParamDTO;
import com.bhp.opusb.service.mapper.AdTriggerParamMapper;

/**
 * Service for executing complex queries for {@link AdTriggerParam} entities in the database.
 * The main input is a {@link AdTriggerParamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdTriggerParamDTO} or a {@link Page} of {@link AdTriggerParamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdTriggerParamQueryService extends QueryService<AdTriggerParam> {

    private final Logger log = LoggerFactory.getLogger(AdTriggerParamQueryService.class);

    private final AdTriggerParamRepository adTriggerParamRepository;

    private final AdTriggerParamMapper adTriggerParamMapper;

    public AdTriggerParamQueryService(AdTriggerParamRepository adTriggerParamRepository, AdTriggerParamMapper adTriggerParamMapper) {
        this.adTriggerParamRepository = adTriggerParamRepository;
        this.adTriggerParamMapper = adTriggerParamMapper;
    }

    /**
     * Return a {@link List} of {@link AdTriggerParamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdTriggerParamDTO> findByCriteria(AdTriggerParamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdTriggerParam> specification = createSpecification(criteria);
        return adTriggerParamMapper.toDto(adTriggerParamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdTriggerParamDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTriggerParamDTO> findByCriteria(AdTriggerParamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdTriggerParam> specification = createSpecification(criteria);
        return adTriggerParamRepository.findAll(specification, page)
            .map(adTriggerParamMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdTriggerParamCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdTriggerParam> specification = createSpecification(criteria);
        return adTriggerParamRepository.count(specification);
    }

    /**
     * Function to convert {@link AdTriggerParamCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdTriggerParam> createSpecification(AdTriggerParamCriteria criteria) {
        Specification<AdTriggerParam> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdTriggerParam_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdTriggerParam_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdTriggerParam_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdTriggerParam_.name));
            }
            if (criteria.getMandatory() != null) {
                specification = specification.and(buildSpecification(criteria.getMandatory(), AdTriggerParam_.mandatory));
            }
            if (criteria.getMandatoryLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMandatoryLogic(), AdTriggerParam_.mandatoryLogic));
            }
            if (criteria.getDisplayLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayLogic(), AdTriggerParam_.displayLogic));
            }
            if (criteria.getReadOnlyLogic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadOnlyLogic(), AdTriggerParam_.readOnlyLogic));
            }
            if (criteria.getDefaultValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultValue(), AdTriggerParam_.defaultValue));
            }
            if (criteria.getFormatPattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormatPattern(), AdTriggerParam_.formatPattern));
            }
            if (criteria.getMinLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinLength(), AdTriggerParam_.minLength));
            }
            if (criteria.getMaxLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxLength(), AdTriggerParam_.maxLength));
            }
            if (criteria.getMinValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinValue(), AdTriggerParam_.minValue));
            }
            if (criteria.getMaxValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxValue(), AdTriggerParam_.maxValue));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdTriggerParam_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdTriggerId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTriggerId(),
                    root -> root.join(AdTriggerParam_.adTrigger, JoinType.LEFT).get(AdTrigger_.id)));
            }
        }
        return specification;
    }
}
