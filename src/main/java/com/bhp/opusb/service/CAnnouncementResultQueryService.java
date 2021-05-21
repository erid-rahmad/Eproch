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

import com.bhp.opusb.domain.CAnnouncementResult;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CAnnouncementResultRepository;
import com.bhp.opusb.service.dto.CAnnouncementResultCriteria;
import com.bhp.opusb.service.dto.CAnnouncementResultDTO;
import com.bhp.opusb.service.mapper.CAnnouncementResultMapper;

/**
 * Service for executing complex queries for {@link CAnnouncementResult} entities in the database.
 * The main input is a {@link CAnnouncementResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CAnnouncementResultDTO} or a {@link Page} of {@link CAnnouncementResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CAnnouncementResultQueryService extends QueryService<CAnnouncementResult> {

    private final Logger log = LoggerFactory.getLogger(CAnnouncementResultQueryService.class);

    private final CAnnouncementResultRepository cAnnouncementResultRepository;

    private final CAnnouncementResultMapper cAnnouncementResultMapper;

    public CAnnouncementResultQueryService(CAnnouncementResultRepository cAnnouncementResultRepository, CAnnouncementResultMapper cAnnouncementResultMapper) {
        this.cAnnouncementResultRepository = cAnnouncementResultRepository;
        this.cAnnouncementResultMapper = cAnnouncementResultMapper;
    }

    /**
     * Return a {@link List} of {@link CAnnouncementResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CAnnouncementResultDTO> findByCriteria(CAnnouncementResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CAnnouncementResult> specification = createSpecification(criteria);
        return cAnnouncementResultMapper.toDto(cAnnouncementResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CAnnouncementResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CAnnouncementResultDTO> findByCriteria(CAnnouncementResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CAnnouncementResult> specification = createSpecification(criteria);
        return cAnnouncementResultRepository.findAll(specification, page)
            .map(cAnnouncementResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CAnnouncementResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CAnnouncementResult> specification = createSpecification(criteria);
        return cAnnouncementResultRepository.count(specification);
    }

    /**
     * Function to convert {@link CAnnouncementResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CAnnouncementResult> createSpecification(CAnnouncementResultCriteria criteria) {
        Specification<CAnnouncementResult> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CAnnouncementResult_.id));
            }
            if (criteria.getPublishDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublishDate(), CAnnouncementResult_.publishDate));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), CAnnouncementResult_.documentNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CAnnouncementResult_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CAnnouncementResult_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CAnnouncementResult_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(CAnnouncementResult_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getBiddingScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingScheduleId(),
                    root -> root.join(CAnnouncementResult_.biddingSchedule, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(CAnnouncementResult_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
