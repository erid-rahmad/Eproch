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

import com.bhp.opusb.domain.MPrequalificationSubmission;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationSubmissionRepository;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionCriteria;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionDTO;
import com.bhp.opusb.service.mapper.MPrequalificationSubmissionMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationSubmission} entities in the database.
 * The main input is a {@link MPrequalificationSubmissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationSubmissionDTO} or a {@link Page} of {@link MPrequalificationSubmissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationSubmissionQueryService extends QueryService<MPrequalificationSubmission> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationSubmissionQueryService.class);

    private final MPrequalificationSubmissionRepository mPrequalificationSubmissionRepository;

    private final MPrequalificationSubmissionMapper mPrequalificationSubmissionMapper;

    public MPrequalificationSubmissionQueryService(MPrequalificationSubmissionRepository mPrequalificationSubmissionRepository, MPrequalificationSubmissionMapper mPrequalificationSubmissionMapper) {
        this.mPrequalificationSubmissionRepository = mPrequalificationSubmissionRepository;
        this.mPrequalificationSubmissionMapper = mPrequalificationSubmissionMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationSubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationSubmissionDTO> findByCriteria(MPrequalificationSubmissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationSubmission> specification = createSpecification(criteria);
        return mPrequalificationSubmissionMapper.toDto(mPrequalificationSubmissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationSubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationSubmissionDTO> findByCriteria(MPrequalificationSubmissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationSubmission> specification = createSpecification(criteria);
        return mPrequalificationSubmissionRepository.findAll(specification, page)
            .map(mPrequalificationSubmissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationSubmissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationSubmission> specification = createSpecification(criteria);
        return mPrequalificationSubmissionRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationSubmissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationSubmission> createSpecification(MPrequalificationSubmissionCriteria criteria) {
        Specification<MPrequalificationSubmission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationSubmission_.id));
            }
            if (criteria.getJoined() != null) {
                specification = specification.and(buildSpecification(criteria.getJoined(), MPrequalificationSubmission_.joined));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MPrequalificationSubmission_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MPrequalificationSubmission_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MPrequalificationSubmission_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MPrequalificationSubmission_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MPrequalificationSubmission_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MPrequalificationSubmission_.processed));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MPrequalificationSubmission_.dateApprove));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MPrequalificationSubmission_.dateReject));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MPrequalificationSubmission_.rejectedReason));
            }
            if (criteria.getDateSubmit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateSubmit(), MPrequalificationSubmission_.dateSubmit));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationSubmission_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationSubmission_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationSubmission_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalificationSubmission_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(MPrequalificationSubmission_.documentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MPrequalificationSubmission_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
