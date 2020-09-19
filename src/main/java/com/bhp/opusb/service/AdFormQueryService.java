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

import com.bhp.opusb.domain.AdForm;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdFormRepository;
import com.bhp.opusb.service.dto.AdFormCriteria;
import com.bhp.opusb.service.dto.AdFormDTO;
import com.bhp.opusb.service.mapper.AdFormMapper;

/**
 * Service for executing complex queries for {@link AdForm} entities in the database.
 * The main input is a {@link AdFormCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdFormDTO} or a {@link Page} of {@link AdFormDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdFormQueryService extends QueryService<AdForm> {

    private final Logger log = LoggerFactory.getLogger(AdFormQueryService.class);

    private final AdFormRepository adFormRepository;

    private final AdFormMapper adFormMapper;

    public AdFormQueryService(AdFormRepository adFormRepository, AdFormMapper adFormMapper) {
        this.adFormRepository = adFormRepository;
        this.adFormMapper = adFormMapper;
    }

    /**
     * Return a {@link List} of {@link AdFormDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdFormDTO> findByCriteria(AdFormCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdForm> specification = createSpecification(criteria);
        return adFormMapper.toDto(adFormRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdFormDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdFormDTO> findByCriteria(AdFormCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdForm> specification = createSpecification(criteria);
        return adFormRepository.findAll(specification, page)
            .map(adFormMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdFormCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdForm> specification = createSpecification(criteria);
        return adFormRepository.count(specification);
    }

    /**
     * Function to convert {@link AdFormCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdForm> createSpecification(AdFormCriteria criteria) {
        Specification<AdForm> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdForm_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdForm_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdForm_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdForm_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdForm_.description));
            }
            if (criteria.getFormName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormName(), AdForm_.formName));
            }
            if (criteria.getAccessLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getAccessLevel(), AdForm_.accessLevel));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdForm_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
