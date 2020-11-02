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

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.service.dto.AdUserCriteria;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.mapper.AdUserMapper;

/**
 * Service for executing complex queries for {@link AdUser} entities in the database.
 * The main input is a {@link AdUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdUserDTO} or a {@link Page} of {@link AdUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdUserQueryService extends QueryService<AdUser> {

    private final Logger log = LoggerFactory.getLogger(AdUserQueryService.class);

    private final AdUserRepository adUserRepository;

    private final AdUserMapper adUserMapper;

    public AdUserQueryService(AdUserRepository adUserRepository, AdUserMapper adUserMapper) {
        this.adUserRepository = adUserRepository;
        this.adUserMapper = adUserMapper;
    }

    /**
     * Return a {@link List} of {@link AdUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdUserDTO> findByCriteria(AdUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdUser> specification = createSpecification(criteria);
        return adUserMapper.toDto(adUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdUserDTO> findByCriteria(AdUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdUser> specification = createSpecification(criteria);
        return adUserRepository.findAll(specification, page)
            .map(adUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdUser> specification = createSpecification(criteria);
        return adUserRepository.count(specification);
    }

    /**
     * Function to convert {@link AdUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdUser> createSpecification(AdUserCriteria criteria) {
        Specification<AdUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdUser_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), AdUser_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AdUser_.active));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), AdUser_.code));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), AdUser_.phone));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), AdUser_.position));
            }
            if (criteria.getVendor() != null) {
                specification = specification.and(buildSpecification(criteria.getVendor(), AdUser_.vendor));
            }
            if (criteria.getFailedLoginCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFailedLoginCount(), AdUser_.failedLoginCount));
            }
            if (criteria.getLastLoginDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastLoginDate(), AdUser_.lastLoginDate));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(AdUser_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getCVendorId(),
                    root -> root.join(AdUser_.cVendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(AdUser_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
