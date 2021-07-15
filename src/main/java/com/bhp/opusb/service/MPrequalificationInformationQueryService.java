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

import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MPrequalificationInformationRepository;
import com.bhp.opusb.service.dto.MPrequalificationInformationCriteria;
import com.bhp.opusb.service.dto.MPrequalificationInformationDTO;
import com.bhp.opusb.service.mapper.MPrequalificationInformationMapper;

/**
 * Service for executing complex queries for {@link MPrequalificationInformation} entities in the database.
 * The main input is a {@link MPrequalificationInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MPrequalificationInformationDTO} or a {@link Page} of {@link MPrequalificationInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MPrequalificationInformationQueryService extends QueryService<MPrequalificationInformation> {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationInformationQueryService.class);

    private final MPrequalificationInformationRepository mPrequalificationInformationRepository;

    private final MPrequalificationInformationMapper mPrequalificationInformationMapper;

    public MPrequalificationInformationQueryService(MPrequalificationInformationRepository mPrequalificationInformationRepository, MPrequalificationInformationMapper mPrequalificationInformationMapper) {
        this.mPrequalificationInformationRepository = mPrequalificationInformationRepository;
        this.mPrequalificationInformationMapper = mPrequalificationInformationMapper;
    }

    /**
     * Return a {@link List} of {@link MPrequalificationInformationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MPrequalificationInformationDTO> findByCriteria(MPrequalificationInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MPrequalificationInformation> specification = createSpecification(criteria);
        return mPrequalificationInformationMapper.toDto(mPrequalificationInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MPrequalificationInformationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationInformationDTO> findByCriteria(MPrequalificationInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MPrequalificationInformation> specification = createSpecification(criteria);
        return mPrequalificationInformationRepository.findAll(specification, page)
            .map(mPrequalificationInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MPrequalificationInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MPrequalificationInformation> specification = createSpecification(criteria);
        return mPrequalificationInformationRepository.count(specification);
    }

    /**
     * Function to convert {@link MPrequalificationInformationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MPrequalificationInformation> createSpecification(MPrequalificationInformationCriteria criteria) {
        Specification<MPrequalificationInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MPrequalificationInformation_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MPrequalificationInformation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MPrequalificationInformation_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MPrequalificationInformation_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), MPrequalificationInformation_.type));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MPrequalificationInformation_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MPrequalificationInformation_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MPrequalificationInformation_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MPrequalificationInformation_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MPrequalificationInformation_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MPrequalificationInformation_.processed));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MPrequalificationInformation_.dateApprove));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MPrequalificationInformation_.dateReject));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MPrequalificationInformation_.rejectedReason));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MPrequalificationInformation_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MPrequalificationInformation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
