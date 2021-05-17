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

import com.bhp.opusb.domain.MProposalAdministration;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProposalAdministrationRepository;
import com.bhp.opusb.service.dto.MProposalAdministrationCriteria;
import com.bhp.opusb.service.dto.MProposalAdministrationDTO;
import com.bhp.opusb.service.mapper.MProposalAdministrationMapper;

/**
 * Service for executing complex queries for {@link MProposalAdministration} entities in the database.
 * The main input is a {@link MProposalAdministrationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProposalAdministrationDTO} or a {@link Page} of {@link MProposalAdministrationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProposalAdministrationQueryService extends QueryService<MProposalAdministration> {

    private final Logger log = LoggerFactory.getLogger(MProposalAdministrationQueryService.class);

    private final MProposalAdministrationRepository mProposalAdministrationRepository;

    private final MProposalAdministrationMapper mProposalAdministrationMapper;

    public MProposalAdministrationQueryService(MProposalAdministrationRepository mProposalAdministrationRepository, MProposalAdministrationMapper mProposalAdministrationMapper) {
        this.mProposalAdministrationRepository = mProposalAdministrationRepository;
        this.mProposalAdministrationMapper = mProposalAdministrationMapper;
    }

    /**
     * Return a {@link List} of {@link MProposalAdministrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProposalAdministrationDTO> findByCriteria(MProposalAdministrationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProposalAdministration> specification = createSpecification(criteria);
        return mProposalAdministrationMapper.toDto(mProposalAdministrationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProposalAdministrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalAdministrationDTO> findByCriteria(MProposalAdministrationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProposalAdministration> specification = createSpecification(criteria);
        return mProposalAdministrationRepository.findAll(specification, page)
            .map(mProposalAdministrationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProposalAdministrationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProposalAdministration> specification = createSpecification(criteria);
        return mProposalAdministrationRepository.count(specification);
    }

    /**
     * Function to convert {@link MProposalAdministrationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProposalAdministration> createSpecification(MProposalAdministrationCriteria criteria) {
        Specification<MProposalAdministration> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProposalAdministration_.id));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), MProposalAdministration_.answer));
            }
            if (criteria.getDocumentEvaluation() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentEvaluation(), MProposalAdministration_.documentEvaluation));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProposalAdministration_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProposalAdministration_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProposalAdministration_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubmissionId(),
                    root -> root.join(MProposalAdministration_.biddingSubmission, JoinType.LEFT).get(MBiddingSubmission_.id)));
            }
            if (criteria.getBiddingSubCriteriaLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingSubCriteriaLineId(),
                    root -> root.join(MProposalAdministration_.biddingSubCriteriaLine, JoinType.LEFT).get(CBiddingSubCriteriaLine_.id)));
            }
        }
        return specification;
    }
}
