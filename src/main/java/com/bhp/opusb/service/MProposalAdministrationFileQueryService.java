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

import com.bhp.opusb.domain.MProposalAdministrationFile;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProposalAdministrationFileRepository;
import com.bhp.opusb.service.dto.MProposalAdministrationFileCriteria;
import com.bhp.opusb.service.dto.MProposalAdministrationFileDTO;
import com.bhp.opusb.service.mapper.MProposalAdministrationFileMapper;

/**
 * Service for executing complex queries for {@link MProposalAdministrationFile} entities in the database.
 * The main input is a {@link MProposalAdministrationFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProposalAdministrationFileDTO} or a {@link Page} of {@link MProposalAdministrationFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProposalAdministrationFileQueryService extends QueryService<MProposalAdministrationFile> {

    private final Logger log = LoggerFactory.getLogger(MProposalAdministrationFileQueryService.class);

    private final MProposalAdministrationFileRepository mProposalAdministrationFileRepository;

    private final MProposalAdministrationFileMapper mProposalAdministrationFileMapper;

    public MProposalAdministrationFileQueryService(MProposalAdministrationFileRepository mProposalAdministrationFileRepository, MProposalAdministrationFileMapper mProposalAdministrationFileMapper) {
        this.mProposalAdministrationFileRepository = mProposalAdministrationFileRepository;
        this.mProposalAdministrationFileMapper = mProposalAdministrationFileMapper;
    }

    /**
     * Return a {@link List} of {@link MProposalAdministrationFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProposalAdministrationFileDTO> findByCriteria(MProposalAdministrationFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProposalAdministrationFile> specification = createSpecification(criteria);
        return mProposalAdministrationFileMapper.toDto(mProposalAdministrationFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProposalAdministrationFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalAdministrationFileDTO> findByCriteria(MProposalAdministrationFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProposalAdministrationFile> specification = createSpecification(criteria);
        return mProposalAdministrationFileRepository.findAll(specification, page)
            .map(mProposalAdministrationFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProposalAdministrationFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProposalAdministrationFile> specification = createSpecification(criteria);
        return mProposalAdministrationFileRepository.count(specification);
    }

    /**
     * Function to convert {@link MProposalAdministrationFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProposalAdministrationFile> createSpecification(MProposalAdministrationFileCriteria criteria) {
        Specification<MProposalAdministrationFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProposalAdministrationFile_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProposalAdministrationFile_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProposalAdministrationFile_.active));
            }
            if (criteria.getCAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCAttachmentId(),
                    root -> root.join(MProposalAdministrationFile_.cAttachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProposalAdministrationFile_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubmissionId(),
                    root -> root.join(MProposalAdministrationFile_.biddingSubmission, JoinType.LEFT).get(MBiddingSubmission_.id)));
            }
            if (criteria.getBiddingSubCriteriaId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaId(),
                    root -> root.join(MProposalAdministrationFile_.biddingSubCriteria, JoinType.LEFT).get(CBiddingSubCriteria_.id)));
            }
        }
        return specification;
    }
}
