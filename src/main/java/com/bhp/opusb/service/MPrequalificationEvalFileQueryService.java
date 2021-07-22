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

import com.bhp.opusb.domain.MPrequalificationEvalFile;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationEvalFileRepository;
import com.bhp.opusb.service.dto.MPrequalificationEvalFileCriteria;
import com.bhp.opusb.service.dto.MPrequalificationEvalFileDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEvalFileMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationEvalFile} entities in the database.
 * The main input is a {@link MPrequalificationEvalFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationEvalFileDTO} or a {@link Page} of {@link MPrequalificationEvalFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationEvalFileQueryService extends QueryService<MPrequalificationEvalFile> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEvalFileQueryService.class);

    private final MPrequalificationEvalFileRepository mPrequalificationEvalFileRepository;

    private final MPrequalificationEvalFileMapper mPrequalificationEvalFileMapper;

    public MPrequalificationEvalFileQueryService(MPrequalificationEvalFileRepository mPrequalificationEvalFileRepository, MPrequalificationEvalFileMapper mPrequalificationEvalFileMapper) {
        this.mPrequalificationEvalFileRepository = mPrequalificationEvalFileRepository;
        this.mPrequalificationEvalFileMapper = mPrequalificationEvalFileMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationEvalFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationEvalFileDTO> findByCriteria(MPrequalificationEvalFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationEvalFile> specification = createSpecification(criteria);
        return mPrequalificationEvalFileMapper.toDto(mPrequalificationEvalFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationEvalFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationEvalFileDTO> findByCriteria(MPrequalificationEvalFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationEvalFile> specification = createSpecification(criteria);
        return mPrequalificationEvalFileRepository.findAll(specification, page)
            .map(mPrequalificationEvalFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationEvalFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationEvalFile> specification = createSpecification(criteria);
        return mPrequalificationEvalFileRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationEvalFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationEvalFile> createSpecification(MPrequalificationEvalFileCriteria criteria) {
        Specification<MPrequalificationEvalFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationEvalFile_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationEvalFile_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationEvalFile_.active));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MPrequalificationEvalFile_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationEvalFile_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPrequalificationSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationSubmissionId(),
                    root -> root.join(MPrequalificationEvalFile_.prequalificationSubmission, JoinType.LEFT).get(MPrequalificationSubmission_.id)));
            }
            if (criteria.getBiddingSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaId(),
                    root -> root.join(MPrequalificationEvalFile_.biddingSubCriteria, JoinType.LEFT).get(CBiddingSubCriteria_.id)));
            }
        }
        return specification;
    }
}
