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

import com.bhp.opusb.domain.MPrequalificationResult;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationResultRepository;
import com.bhp.opusb.service.dto.MPrequalificationResultCriteria;
import com.bhp.opusb.service.dto.MPrequalificationResultDTO;
import com.bhp.opusb.service.mapper.MPrequalificationResultMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationResult} entities in the database.
 * The main input is a {@link MPrequalificationResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationResultDTO} or a {@link Page} of {@link MPrequalificationResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationResultQueryService extends QueryService<MPrequalificationResult> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationResultQueryService.class);

    private final MPrequalificationResultRepository mPrequalificationResultRepository;

    private final MPrequalificationResultMapper mPrequalificationResultMapper;

    public MPrequalificationResultQueryService(MPrequalificationResultRepository mPrequalificationResultRepository, MPrequalificationResultMapper mPrequalificationResultMapper) {
        this.mPrequalificationResultRepository = mPrequalificationResultRepository;
        this.mPrequalificationResultMapper = mPrequalificationResultMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationResultDTO> findByCriteria(MPrequalificationResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationResult> specification = createSpecification(criteria);
        return mPrequalificationResultMapper.toDto(mPrequalificationResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationResultDTO> findByCriteria(MPrequalificationResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationResult> specification = createSpecification(criteria);
        return mPrequalificationResultRepository.findAll(specification, page)
            .map(mPrequalificationResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationResult> specification = createSpecification(criteria);
        return mPrequalificationResultRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationResult> createSpecification(MPrequalificationResultCriteria criteria) {
        Specification<MPrequalificationResult> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationResult_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationResult_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationResult_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationResult_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAnnouncementResultId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnnouncementResultId(),
                    root -> root.join(MPrequalificationResult_.announcementResult, JoinType.LEFT).get(MPrequalAnnouncementResult_.id)));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalificationResult_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MPrequalificationResult_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubmissionId(),
                    root -> root.join(MPrequalificationResult_.submission, JoinType.LEFT).get(MPrequalificationSubmission_.id)));
            }
        }
        return specification;
    }
}
