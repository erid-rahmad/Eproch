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

import com.bhp.opusb.domain.AdTaskApplication;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdTaskApplicationRepository;
import com.bhp.opusb.service.dto.AdTaskApplicationCriteria;
import com.bhp.opusb.service.dto.AdTaskApplicationDTO;
import com.bhp.opusb.service.mapper.AdTaskApplicationMapper;

/**
 * Service for executing complex queries for {@link AdTaskApplication} entities in the database.
 * The main input is a {@link AdTaskApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdTaskApplicationDTO} or a {@link Page} of {@link AdTaskApplicationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdTaskApplicationQueryService extends QueryService<AdTaskApplication> {

    private final Logger log = LoggerFactory.getLogger(AdTaskApplicationQueryService.class);

    private final AdTaskApplicationRepository adTaskApplicationRepository;

    private final AdTaskApplicationMapper adTaskApplicationMapper;

    public AdTaskApplicationQueryService(AdTaskApplicationRepository adTaskApplicationRepository, AdTaskApplicationMapper adTaskApplicationMapper) {
        this.adTaskApplicationRepository = adTaskApplicationRepository;
        this.adTaskApplicationMapper = adTaskApplicationMapper;
    }

    /**
     * Return a {@link List} of {@link AdTaskApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdTaskApplicationDTO> findByCriteria(AdTaskApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdTaskApplication> specification = createSpecification(criteria);
        return adTaskApplicationMapper.toDto(adTaskApplicationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdTaskApplicationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskApplicationDTO> findByCriteria(AdTaskApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdTaskApplication> specification = createSpecification(criteria);
        return adTaskApplicationRepository.findAll(specification, page)
            .map(adTaskApplicationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdTaskApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdTaskApplication> specification = createSpecification(criteria);
        return adTaskApplicationRepository.count(specification);
    }

    /**
     * Function to convert {@link AdTaskApplicationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdTaskApplication> createSpecification(AdTaskApplicationCriteria criteria) {
        Specification<AdTaskApplication> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdTaskApplication_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdTaskApplication_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdTaskApplication_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AdTaskApplication_.name));
            }
            if (criteria.getUri() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUri(), AdTaskApplication_.uri));
            }
            if (criteria.getMetadataUri() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMetadataUri(), AdTaskApplication_.metadataUri));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVersion(), AdTaskApplication_.version));
            }
            if (criteria.getOverrideExisting() != null) {
                specification = specification.and(buildSpecification(criteria.getOverrideExisting(), AdTaskApplication_.overrideExisting));
            }
            if (criteria.getDeployed() != null) {
                specification = specification.and(buildSpecification(criteria.getDeployed(), AdTaskApplication_.deployed));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdTaskApplication_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
