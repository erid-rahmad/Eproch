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

import com.bhp.opusb.domain.MVendorInvitation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorInvitationRepository;
import com.bhp.opusb.service.dto.MVendorInvitationCriteria;
import com.bhp.opusb.service.dto.MVendorInvitationDTO;
import com.bhp.opusb.service.mapper.MVendorInvitationMapper;

/**
 * Service for executing complex queries for {@link MVendorInvitation} entities in the database.
 * The main input is a {@link MVendorInvitationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorInvitationDTO} or a {@link Page} of {@link MVendorInvitationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorInvitationQueryService extends QueryService<MVendorInvitation> {

    private final Logger log = LoggerFactory.getLogger(MVendorInvitationQueryService.class);

    private final MVendorInvitationRepository mVendorInvitationRepository;

    private final MVendorInvitationMapper mVendorInvitationMapper;

    public MVendorInvitationQueryService(MVendorInvitationRepository mVendorInvitationRepository, MVendorInvitationMapper mVendorInvitationMapper) {
        this.mVendorInvitationRepository = mVendorInvitationRepository;
        this.mVendorInvitationMapper = mVendorInvitationMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorInvitationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorInvitationDTO> findByCriteria(MVendorInvitationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorInvitation> specification = createSpecification(criteria);
        return mVendorInvitationMapper.toDto(mVendorInvitationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorInvitationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorInvitationDTO> findByCriteria(MVendorInvitationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorInvitation> specification = createSpecification(criteria);
        return mVendorInvitationRepository.findAll(specification, page)
            .map(mVendorInvitationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorInvitationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorInvitation> specification = createSpecification(criteria);
        return mVendorInvitationRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorInvitationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorInvitation> createSpecification(MVendorInvitationCriteria criteria) {
        Specification<MVendorInvitation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorInvitation_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorInvitation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorInvitation_.active));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MVendorInvitation_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorInvitation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBusinessClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessClassificationId(),
                    root -> root.join(MVendorInvitation_.businessClassification, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MVendorInvitation_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessSubCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessSubCategoryId(),
                    root -> root.join(MVendorInvitation_.businessSubCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
        }
        return specification;
    }
}
