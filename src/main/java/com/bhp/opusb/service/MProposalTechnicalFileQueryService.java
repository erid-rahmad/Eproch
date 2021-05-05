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

import com.bhp.opusb.domain.MProposalTechnicalFile;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProposalTechnicalFileRepository;
import com.bhp.opusb.service.dto.MProposalTechnicalFileCriteria;
import com.bhp.opusb.service.dto.MProposalTechnicalFileDTO;
import com.bhp.opusb.service.mapper.MProposalTechnicalFileMapper;

/**
 * Service for executing complex queries for {@link MProposalTechnicalFile} entities in the database.
 * The main input is a {@link MProposalTechnicalFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProposalTechnicalFileDTO} or a {@link Page} of {@link MProposalTechnicalFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProposalTechnicalFileQueryService extends QueryService<MProposalTechnicalFile> {

    private final Logger log = LoggerFactory.getLogger(MProposalTechnicalFileQueryService.class);

    private final MProposalTechnicalFileRepository mProposalTechnicalFileRepository;

    private final MProposalTechnicalFileMapper mProposalTechnicalFileMapper;

    public MProposalTechnicalFileQueryService(MProposalTechnicalFileRepository mProposalTechnicalFileRepository, MProposalTechnicalFileMapper mProposalTechnicalFileMapper) {
        this.mProposalTechnicalFileRepository = mProposalTechnicalFileRepository;
        this.mProposalTechnicalFileMapper = mProposalTechnicalFileMapper;
    }

    /**
     * Return a {@link List} of {@link MProposalTechnicalFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProposalTechnicalFileDTO> findByCriteria(MProposalTechnicalFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProposalTechnicalFile> specification = createSpecification(criteria);
        return mProposalTechnicalFileMapper.toDto(mProposalTechnicalFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProposalTechnicalFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalTechnicalFileDTO> findByCriteria(MProposalTechnicalFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProposalTechnicalFile> specification = createSpecification(criteria);
        return mProposalTechnicalFileRepository.findAll(specification, page)
            .map(mProposalTechnicalFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProposalTechnicalFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProposalTechnicalFile> specification = createSpecification(criteria);
        return mProposalTechnicalFileRepository.count(specification);
    }

    /**
     * Function to convert {@link MProposalTechnicalFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProposalTechnicalFile> createSpecification(MProposalTechnicalFileCriteria criteria) {
        Specification<MProposalTechnicalFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProposalTechnicalFile_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProposalTechnicalFile_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProposalTechnicalFile_.active));
            }
            if (criteria.getCAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCAttachmentId(),
                    root -> root.join(MProposalTechnicalFile_.cAttachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProposalTechnicalFile_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubmissionId(),
                    root -> root.join(MProposalTechnicalFile_.biddingSubmission, JoinType.LEFT).get(MBiddingSubmission_.id)));
            }
            if (criteria.getBiddingSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaId(),
                    root -> root.join(MProposalTechnicalFile_.biddingSubCriteria, JoinType.LEFT).get(CBiddingSubCriteria_.id)));
            }
        }
        return specification;
    }
}
