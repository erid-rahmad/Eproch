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

import com.bhp.opusb.domain.ScAuthority;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ScAuthorityRepository;
import com.bhp.opusb.service.dto.ScAuthorityCriteria;
import com.bhp.opusb.service.dto.ScAuthorityDTO;
import com.bhp.opusb.service.mapper.ScAuthorityMapper;

/**
 * Service for executing complex queries for {@link ScAuthority} entities in the database.
 * The main input is a {@link ScAuthorityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScAuthorityDTO} or a {@link Page} of {@link ScAuthorityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScAuthorityQueryService extends QueryService<ScAuthority> {

    private final Logger log = LoggerFactory.getLogger(ScAuthorityQueryService.class);

    private final ScAuthorityRepository scAuthorityRepository;

    private final ScAuthorityMapper scAuthorityMapper;

    public ScAuthorityQueryService(ScAuthorityRepository scAuthorityRepository, ScAuthorityMapper scAuthorityMapper) {
        this.scAuthorityRepository = scAuthorityRepository;
        this.scAuthorityMapper = scAuthorityMapper;
    }

    /**
     * Return a {@link List} of {@link ScAuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScAuthorityDTO> findByCriteria(ScAuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ScAuthority> specification = createSpecification(criteria);
        return scAuthorityMapper.toDto(scAuthorityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScAuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScAuthorityDTO> findByCriteria(ScAuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ScAuthority> specification = createSpecification(criteria);
        return scAuthorityRepository.findAll(specification, page)
            .map(scAuthorityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScAuthorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ScAuthority> specification = createSpecification(criteria);
        return scAuthorityRepository.count(specification);
    }

    /**
     * Function to convert {@link ScAuthorityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ScAuthority> createSpecification(ScAuthorityCriteria criteria) {
        Specification<ScAuthority> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ScAuthority_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), ScAuthority_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ScAuthority_.active));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ScAuthority_.description));
            }
            if (criteria.getMaster() != null) {
                specification = specification.and(buildSpecification(criteria.getMaster(), ScAuthority_.master));
            }
            if (criteria.getAuthorityName() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthorityName(),
                    root -> root.join(ScAuthority_.authority, JoinType.INNER).get(Authority_.name)));
            }
            if (criteria.getAccessAllOrgs() != null) {
                specification = specification.and(buildSpecification(criteria.getAccessAllOrgs(), ScAuthority_.accessAllOrgs));
            }
            if (criteria.getUseUserOrgs() != null) {
                specification = specification.and(buildSpecification(criteria.getUseUserOrgs(), ScAuthority_.useUserOrgs));
            }
            if (criteria.getScAccessId() != null) {
                specification = specification.and(buildSpecification(criteria.getScAccessId(),
                    root -> root.join(ScAuthority_.scAccesses, JoinType.LEFT).get(ScAccess_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ScAuthority_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
