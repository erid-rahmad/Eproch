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

import com.bhp.opusb.domain.AdButton;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdButtonRepository;
import com.bhp.opusb.service.dto.AdButtonCriteria;
import com.bhp.opusb.service.dto.AdButtonDTO;
import com.bhp.opusb.service.mapper.AdButtonMapper;

/**
 * Service for executing complex queries for {@link AdButton} entities in the database.
 * The main input is a {@link AdButtonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdButtonDTO} or a {@link Page} of {@link AdButtonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdButtonQueryService extends QueryService<AdButton> {

    private final Logger log = LoggerFactory.getLogger(AdButtonQueryService.class);

    private final AdButtonRepository adButtonRepository;

    private final AdButtonMapper adButtonMapper;

    public AdButtonQueryService(AdButtonRepository adButtonRepository, AdButtonMapper adButtonMapper) {
        this.adButtonRepository = adButtonRepository;
        this.adButtonMapper = adButtonMapper;
    }

    /**
     * Return a {@link List} of {@link AdButtonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdButtonDTO> findByCriteria(AdButtonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdButton> specification = createSpecification(criteria);
        return adButtonMapper.toDto(adButtonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdButtonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdButtonDTO> findByCriteria(AdButtonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdButton> specification = createSpecification(criteria);
        return adButtonRepository.findAll(specification, page)
            .map(adButtonMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdButtonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdButton> specification = createSpecification(criteria);
        return adButtonRepository.count(specification);
    }

    /**
     * Function to convert {@link AdButtonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdButton> createSpecification(AdButtonCriteria criteria) {
        Specification<AdButton> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdButton_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdButton_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdButton_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdButton_.name));
            }
            if (criteria.getTooltip() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTooltip(), AdButton_.tooltip));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdButton_.description));
            }
            if (criteria.getToolbar() != null) {
                specification = specification.and(buildSpecification(criteria.getToolbar(), AdButton_.toolbar));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), AdButton_.icon));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdButton_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAdTriggerId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdTriggerId(),
                    root -> root.join(AdButton_.adTrigger, JoinType.LEFT).get(AdTrigger_.id)));
            }
        }
        return specification;
    }
}
