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

import com.bhp.opusb.domain.MPrequalAnnouncementResult;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalAnnouncementResultRepository;
import com.bhp.opusb.service.dto.MPrequalAnnouncementResultCriteria;
import com.bhp.opusb.service.dto.MPrequalAnnouncementResultDTO;
import com.bhp.opusb.service.mapper.MPrequalAnnouncementResultMapper;

/**
 * Service for executing complex queries for {@link MPrequalAnnouncementResult} entities in the database.
 * The main input is a {@link MPrequalAnnouncementResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalAnnouncementResultDTO} or a {@link Page} of {@link MPrequalAnnouncementResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalAnnouncementResultQueryService extends QueryService<MPrequalAnnouncementResult> {

    private final Logger log = LoggerFactory.getLogger(MPrequalAnnouncementResultQueryService.class);

    private final MPrequalAnnouncementResultRepository mPrequalAnnouncementResultRepository;

    private final MPrequalAnnouncementResultMapper mPrequalAnnouncementResultMapper;

    public MPrequalAnnouncementResultQueryService(MPrequalAnnouncementResultRepository mPrequalAnnouncementResultRepository, MPrequalAnnouncementResultMapper mPrequalAnnouncementResultMapper) {
        this.mPrequalAnnouncementResultRepository = mPrequalAnnouncementResultRepository;
        this.mPrequalAnnouncementResultMapper = mPrequalAnnouncementResultMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalAnnouncementResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalAnnouncementResultDTO> findByCriteria(MPrequalAnnouncementResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalAnnouncementResult> specification = createSpecification(criteria);
        return mPrequalAnnouncementResultMapper.toDto(mPrequalAnnouncementResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalAnnouncementResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalAnnouncementResultDTO> findByCriteria(MPrequalAnnouncementResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalAnnouncementResult> specification = createSpecification(criteria);
        return mPrequalAnnouncementResultRepository.findAll(specification, page)
            .map(mPrequalAnnouncementResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalAnnouncementResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalAnnouncementResult> specification = createSpecification(criteria);
        return mPrequalAnnouncementResultRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalAnnouncementResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalAnnouncementResult> createSpecification(MPrequalAnnouncementResultCriteria criteria) {
        Specification<MPrequalAnnouncementResult> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalAnnouncementResult_.id));
            }
            if (criteria.getPublishDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublishDate(), MPrequalAnnouncementResult_.publishDate));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MPrequalAnnouncementResult_.documentNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalAnnouncementResult_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalAnnouncementResult_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalAnnouncementResult_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalAnnouncementResult_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getPrequalificationScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationScheduleId(),
                    root -> root.join(MPrequalAnnouncementResult_.prequalificationSchedule, JoinType.LEFT).get(MPrequalificationSchedule_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MPrequalAnnouncementResult_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
