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

import com.bhp.opusb.domain.MProposalTechnical;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProposalTechnicalRepository;
import com.bhp.opusb.service.dto.MProposalTechnicalCriteria;
import com.bhp.opusb.service.dto.MProposalTechnicalDTO;
import com.bhp.opusb.service.mapper.MProposalTechnicalMapper;

/**
 * Service for executing complex queries for {@link MProposalTechnical} entities in the database.
 * The main input is a {@link MProposalTechnicalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProposalTechnicalDTO} or a {@link Page} of {@link MProposalTechnicalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProposalTechnicalQueryService extends QueryService<MProposalTechnical> {

    private final Logger log = LoggerFactory.getLogger(MProposalTechnicalQueryService.class);

    private final MProposalTechnicalRepository mProposalTechnicalRepository;

    private final MProposalTechnicalMapper mProposalTechnicalMapper;

    public MProposalTechnicalQueryService(MProposalTechnicalRepository mProposalTechnicalRepository, MProposalTechnicalMapper mProposalTechnicalMapper) {
        this.mProposalTechnicalRepository = mProposalTechnicalRepository;
        this.mProposalTechnicalMapper = mProposalTechnicalMapper;
    }

    /**
     * Return a {@link List} of {@link MProposalTechnicalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProposalTechnicalDTO> findByCriteria(MProposalTechnicalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProposalTechnical> specification = createSpecification(criteria);
        return mProposalTechnicalMapper.toDto(mProposalTechnicalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProposalTechnicalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalTechnicalDTO> findByCriteria(MProposalTechnicalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProposalTechnical> specification = createSpecification(criteria);
        return mProposalTechnicalRepository.findAll(specification, page)
            .map(mProposalTechnicalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProposalTechnicalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProposalTechnical> specification = createSpecification(criteria);
        return mProposalTechnicalRepository.count(specification);
    }

    /**
     * Function to convert {@link MProposalTechnicalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProposalTechnical> createSpecification(MProposalTechnicalCriteria criteria) {
        Specification<MProposalTechnical> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProposalTechnical_.id));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), MProposalTechnical_.answer));
            }
            if (criteria.getDocumentEvaluation() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentEvaluation(), MProposalTechnical_.documentEvaluation));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProposalTechnical_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProposalTechnical_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProposalTechnical_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubmissionId(),
                    root -> root.join(MProposalTechnical_.biddingSubmission, JoinType.LEFT).get(MBiddingSubmission_.id)));
            }
            if (criteria.getBiddingSubCriteriaLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaLineId(),
                    root -> root.join(MProposalTechnical_.biddingSubCriteriaLine, JoinType.LEFT).get(CBiddingSubCriteriaLine_.id)));
            }
        }
        return specification;
    }
}
