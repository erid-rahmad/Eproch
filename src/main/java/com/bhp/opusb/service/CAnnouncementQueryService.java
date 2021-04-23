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

import com.bhp.opusb.domain.CAnnouncement;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CAnnouncementRepository;
import com.bhp.opusb.service.dto.CAnnouncementCriteria;
import com.bhp.opusb.service.dto.CAnnouncementDTO;
import com.bhp.opusb.service.mapper.CAnnouncementMapper;

/**
 * Service for executing complex queries for {@link CAnnouncement} entities in the database.
 * The main input is a {@link CAnnouncementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CAnnouncementDTO} or a {@link Page} of {@link CAnnouncementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CAnnouncementQueryService extends QueryService<CAnnouncement> {

    private final Logger log = LoggerFactory.getLogger(CAnnouncementQueryService.class);

    private final CAnnouncementRepository cAnnouncementRepository;

    private final CAnnouncementMapper cAnnouncementMapper;

    public CAnnouncementQueryService(CAnnouncementRepository cAnnouncementRepository, CAnnouncementMapper cAnnouncementMapper) {
        this.cAnnouncementRepository = cAnnouncementRepository;
        this.cAnnouncementMapper = cAnnouncementMapper;
    }

    /**
     * Return a {@link List} of {@link CAnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CAnnouncementDTO> findByCriteria(CAnnouncementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CAnnouncement> specification = createSpecification(criteria);
        return cAnnouncementMapper.toDto(cAnnouncementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CAnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CAnnouncementDTO> findByCriteria(CAnnouncementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CAnnouncement> specification = createSpecification(criteria);
        return cAnnouncementRepository.findAll(specification, page)
            .map(cAnnouncementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CAnnouncementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CAnnouncement> specification = createSpecification(criteria);
        return cAnnouncementRepository.count(specification);
    }

    /**
     * Function to convert {@link CAnnouncementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CAnnouncement> createSpecification(CAnnouncementCriteria criteria) {
        Specification<CAnnouncement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CAnnouncement_.id));
            }
            if (criteria.getPublishDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublishDate(), CAnnouncement_.publishDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CAnnouncement_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CAnnouncement_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CAnnouncement_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(CAnnouncement_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getBiddingScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingScheduleId(),
                    root -> root.join(CAnnouncement_.biddingSchedule, JoinType.LEFT).get(MBiddingSchedule_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(CAnnouncement_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
