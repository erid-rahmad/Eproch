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

import com.bhp.opusb.domain.AdUserAuthority;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdUserAuthorityRepository;
import com.bhp.opusb.service.dto.AdUserAuthorityCriteria;
import com.bhp.opusb.service.dto.AdUserAuthorityDTO;
import com.bhp.opusb.service.mapper.AdUserAuthorityMapper;

/**
 * Service for executing complex queries for {@link AdUserAuthority} entities in the database.
 * The main input is a {@link AdUserAuthorityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdUserAuthorityDTO} or a {@link Page} of {@link AdUserAuthorityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdUserAuthorityQueryService extends QueryService<AdUserAuthority> {

    private final Logger log = LoggerFactory.getLogger(AdUserAuthorityQueryService.class);

    private final AdUserAuthorityRepository adUserAuthorityRepository;

    private final AdUserAuthorityMapper adUserAuthorityMapper;

    public AdUserAuthorityQueryService(AdUserAuthorityRepository adUserAuthorityRepository, AdUserAuthorityMapper adUserAuthorityMapper) {
        this.adUserAuthorityRepository = adUserAuthorityRepository;
        this.adUserAuthorityMapper = adUserAuthorityMapper;
    }

    /**
     * Return a {@link List} of {@link AdUserAuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdUserAuthorityDTO> findByCriteria(AdUserAuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdUserAuthority> specification = createSpecification(criteria);
        return adUserAuthorityMapper.toDto(adUserAuthorityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdUserAuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdUserAuthorityDTO> findByCriteria(AdUserAuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdUserAuthority> specification = createSpecification(criteria);
        return adUserAuthorityRepository.findAll(specification, page)
            .map(adUserAuthorityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdUserAuthorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdUserAuthority> specification = createSpecification(criteria);
        return adUserAuthorityRepository.count(specification);
    }

    /**
     * Function to convert {@link AdUserAuthorityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdUserAuthority> createSpecification(AdUserAuthorityCriteria criteria) {
        Specification<AdUserAuthority> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdUserAuthority_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdUserAuthority_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdUserAuthority_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdUserAuthority_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getUserUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserUserId(),
                    root -> root.join(AdUserAuthority_.user, JoinType.INNER).get(AdUser_.id)));
            }
            if (criteria.getAuthorityId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthorityId(),
                    root -> root.join(AdUserAuthority_.authority, JoinType.INNER).get(ScAuthority_.id)));
            }
        }
        return specification;
    }
}
