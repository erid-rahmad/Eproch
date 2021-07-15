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

import com.bhp.opusb.domain.MPrequalificationInvitation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationInvitationRepository;
import com.bhp.opusb.service.dto.MPrequalificationInvitationCriteria;
import com.bhp.opusb.service.dto.MPrequalificationInvitationDTO;
import com.bhp.opusb.service.mapper.MPrequalificationInvitationMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationInvitation} entities in the database.
 * The main input is a {@link MPrequalificationInvitationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationInvitationDTO} or a {@link Page} of {@link MPrequalificationInvitationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationInvitationQueryService extends QueryService<MPrequalificationInvitation> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationInvitationQueryService.class);

    private final MPrequalificationInvitationRepository mPrequalificationInvitationRepository;

    private final MPrequalificationInvitationMapper mPrequalificationInvitationMapper;

    public MPrequalificationInvitationQueryService(MPrequalificationInvitationRepository mPrequalificationInvitationRepository, MPrequalificationInvitationMapper mPrequalificationInvitationMapper) {
        this.mPrequalificationInvitationRepository = mPrequalificationInvitationRepository;
        this.mPrequalificationInvitationMapper = mPrequalificationInvitationMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationInvitationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationInvitationDTO> findByCriteria(MPrequalificationInvitationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationInvitation> specification = createSpecification(criteria);
        return mPrequalificationInvitationMapper.toDto(mPrequalificationInvitationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationInvitationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationInvitationDTO> findByCriteria(MPrequalificationInvitationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationInvitation> specification = createSpecification(criteria);
        return mPrequalificationInvitationRepository.findAll(specification, page)
            .map(mPrequalificationInvitationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationInvitationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationInvitation> specification = createSpecification(criteria);
        return mPrequalificationInvitationRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationInvitationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationInvitation> createSpecification(MPrequalificationInvitationCriteria criteria) {
        Specification<MPrequalificationInvitation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationInvitation_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationInvitation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationInvitation_.active));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalificationInvitation_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationInvitation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MPrequalificationInvitation_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessSubCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessSubCategoryId(),
                    root -> root.join(MPrequalificationInvitation_.businessSubCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
        }
        return specification;
    }
}
