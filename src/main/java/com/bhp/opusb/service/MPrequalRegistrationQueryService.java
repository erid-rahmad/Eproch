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

import com.bhp.opusb.domain.MPrequalRegistration;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalRegistrationRepository;
import com.bhp.opusb.service.dto.MPrequalRegistrationCriteria;
import com.bhp.opusb.service.dto.MPrequalRegistrationDTO;
import com.bhp.opusb.service.mapper.MPrequalRegistrationMapper;

/**
 * Service for executing complex queries for {@link MPrequalRegistration} entities in the database.
 * The main input is a {@link MPrequalRegistrationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalRegistrationDTO} or a {@link Page} of {@link MPrequalRegistrationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalRegistrationQueryService extends QueryService<MPrequalRegistration> {

    private final Logger log = LoggerFactory.getLogger(MPrequalRegistrationQueryService.class);

    private final MPrequalRegistrationRepository mPrequalRegistrationRepository;

    private final MPrequalRegistrationMapper mPrequalRegistrationMapper;

    public MPrequalRegistrationQueryService(MPrequalRegistrationRepository mPrequalRegistrationRepository, MPrequalRegistrationMapper mPrequalRegistrationMapper) {
        this.mPrequalRegistrationRepository = mPrequalRegistrationRepository;
        this.mPrequalRegistrationMapper = mPrequalRegistrationMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalRegistrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalRegistrationDTO> findByCriteria(MPrequalRegistrationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalRegistration> specification = createSpecification(criteria);
        return mPrequalRegistrationMapper.toDto(mPrequalRegistrationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalRegistrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalRegistrationDTO> findByCriteria(MPrequalRegistrationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalRegistration> specification = createSpecification(criteria);
        return mPrequalRegistrationRepository.findAll(specification, page)
            .map(mPrequalRegistrationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalRegistrationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalRegistration> specification = createSpecification(criteria);
        return mPrequalRegistrationRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalRegistrationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalRegistration> createSpecification(MPrequalRegistrationCriteria criteria) {
        Specification<MPrequalRegistration> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalRegistration_.id));
            }
            if (criteria.getRegistrationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegistrationStatus(), MPrequalRegistration_.registrationStatus));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), MPrequalRegistration_.reason));
            }
            if (criteria.getAnswerDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnswerDate(), MPrequalRegistration_.answerDate));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalRegistration_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalRegistration_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalRegistration_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAnnouncementId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnnouncementId(),
                    root -> root.join(MPrequalRegistration_.announcement, JoinType.LEFT).get(MPrequalAnnouncement_.id)));
            }
            if (criteria.getPrequalificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrequalificationId(),
                    root -> root.join(MPrequalRegistration_.prequalification, JoinType.LEFT).get(MPrequalificationInformation_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MPrequalRegistration_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
