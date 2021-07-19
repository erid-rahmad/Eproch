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

import com.bhp.opusb.domain.MPrequalAnnouncement;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalAnnouncementRepository;
import com.bhp.opusb.service.dto.MPrequalAnnouncementCriteria;
import com.bhp.opusb.service.dto.MPrequalAnnouncementDTO;
import com.bhp.opusb.service.mapper.MPrequalAnnouncementMapper;

/**
 * Service for executing complex queries for {@link MPrequalAnnouncement} entities in the database.
 * The main input is a {@link MPrequalAnnouncementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalAnnouncementDTO} or a {@link Page} of {@link MPrequalAnnouncementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalAnnouncementQueryService extends QueryService<MPrequalAnnouncement> {

    private final Logger log = LoggerFactory.getLogger(MPrequalAnnouncementQueryService.class);

    private final MPrequalAnnouncementRepository mPrequalAnnouncementRepository;

    private final MPrequalAnnouncementMapper mPrequalAnnouncementMapper;

    public MPrequalAnnouncementQueryService(MPrequalAnnouncementRepository mPrequalAnnouncementRepository, MPrequalAnnouncementMapper mPrequalAnnouncementMapper) {
        this.mPrequalAnnouncementRepository = mPrequalAnnouncementRepository;
        this.mPrequalAnnouncementMapper = mPrequalAnnouncementMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalAnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalAnnouncementDTO> findByCriteria(MPrequalAnnouncementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalAnnouncement> specification = createSpecification(criteria);
        return mPrequalAnnouncementMapper.toDto(mPrequalAnnouncementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalAnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalAnnouncementDTO> findByCriteria(MPrequalAnnouncementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalAnnouncement> specification = createSpecification(criteria);
        return mPrequalAnnouncementRepository.findAll(specification, page)
            .map(mPrequalAnnouncementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalAnnouncementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalAnnouncement> specification = createSpecification(criteria);
        return mPrequalAnnouncementRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalAnnouncementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalAnnouncement> createSpecification(MPrequalAnnouncementCriteria criteria) {
        Specification<MPrequalAnnouncement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalAnnouncement_.id));
            }
            if (criteria.getPublishDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublishDate(), MPrequalAnnouncement_.publishDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalAnnouncement_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalAnnouncement_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalAnnouncement_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalAnnouncement_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getPrequalificationScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationScheduleId(),
                    root -> root.join(MPrequalAnnouncement_.prequalificationSchedule, JoinType.LEFT).get(MPrequalificationSchedule_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MPrequalAnnouncement_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
