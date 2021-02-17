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

import com.bhp.opusb.domain.MDocumentSchedule;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MDocumentScheduleRepository;
import com.bhp.opusb.service.dto.MDocumentScheduleCriteria;
import com.bhp.opusb.service.dto.MDocumentScheduleDTO;
import com.bhp.opusb.service.mapper.MDocumentScheduleMapper;

/**
 * Service for executing complex queries for {@link MDocumentSchedule} entities in the database.
 * The main input is a {@link MDocumentScheduleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MDocumentScheduleDTO} or a {@link Page} of {@link MDocumentScheduleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MDocumentScheduleQueryService extends QueryService<MDocumentSchedule> {

    private final Logger log = LoggerFactory.getLogger(MDocumentScheduleQueryService.class);

    private final MDocumentScheduleRepository mDocumentScheduleRepository;

    private final MDocumentScheduleMapper mDocumentScheduleMapper;

    public MDocumentScheduleQueryService(MDocumentScheduleRepository mDocumentScheduleRepository, MDocumentScheduleMapper mDocumentScheduleMapper) {
        this.mDocumentScheduleRepository = mDocumentScheduleRepository;
        this.mDocumentScheduleMapper = mDocumentScheduleMapper;
    }

    /**
     * Return a {@link List} of {@link MDocumentScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MDocumentScheduleDTO> findByCriteria(MDocumentScheduleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MDocumentSchedule> specification = createSpecification(criteria);
        return mDocumentScheduleMapper.toDto(mDocumentScheduleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MDocumentScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MDocumentScheduleDTO> findByCriteria(MDocumentScheduleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MDocumentSchedule> specification = createSpecification(criteria);
        return mDocumentScheduleRepository.findAll(specification, page)
            .map(mDocumentScheduleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MDocumentScheduleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MDocumentSchedule> specification = createSpecification(criteria);
        return mDocumentScheduleRepository.count(specification);
    }

    /**
     * Function to convert {@link MDocumentScheduleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MDocumentSchedule> createSpecification(MDocumentScheduleCriteria criteria) {
        Specification<MDocumentSchedule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MDocumentSchedule_.id));
            }
            if (criteria.getDocEvent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocEvent(), MDocumentSchedule_.docEvent));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MDocumentSchedule_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MDocumentSchedule_.active));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MDocumentSchedule_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MDocumentSchedule_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorSubmissionId(),
                    root -> root.join(MDocumentSchedule_.vendorSubmission, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
            if (criteria.getVendorEvaluationId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorEvaluationId(),
                    root -> root.join(MDocumentSchedule_.vendorEvaluation, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
        }
        return specification;
    }
}
