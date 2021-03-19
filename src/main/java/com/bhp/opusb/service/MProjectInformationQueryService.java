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

import com.bhp.opusb.domain.MProjectInformation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MProjectInformationRepository;
import com.bhp.opusb.service.dto.MProjectInformationCriteria;
import com.bhp.opusb.service.dto.MProjectInformationDTO;
import com.bhp.opusb.service.mapper.MProjectInformationMapper;

/**
 * Service for executing complex queries for {@link MProjectInformation} entities in the database.
 * The main input is a {@link MProjectInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MProjectInformationDTO} or a {@link Page} of {@link MProjectInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MProjectInformationQueryService extends QueryService<MProjectInformation> {

    private final Logger log = LoggerFactory.getLogger(MProjectInformationQueryService.class);

    private final MProjectInformationRepository mProjectInformationRepository;

    private final MProjectInformationMapper mProjectInformationMapper;

    public MProjectInformationQueryService(MProjectInformationRepository mProjectInformationRepository, MProjectInformationMapper mProjectInformationMapper) {
        this.mProjectInformationRepository = mProjectInformationRepository;
        this.mProjectInformationMapper = mProjectInformationMapper;
    }

    /**
     * Return a {@link List} of {@link MProjectInformationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MProjectInformationDTO> findByCriteria(MProjectInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MProjectInformation> specification = createSpecification(criteria);
        return mProjectInformationMapper.toDto(mProjectInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MProjectInformationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MProjectInformationDTO> findByCriteria(MProjectInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MProjectInformation> specification = createSpecification(criteria);
        return mProjectInformationRepository.findAll(specification, page)
            .map(mProjectInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MProjectInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MProjectInformation> specification = createSpecification(criteria);
        return mProjectInformationRepository.count(specification);
    }

    /**
     * Function to convert {@link MProjectInformationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MProjectInformation> createSpecification(MProjectInformationCriteria criteria) {
        Specification<MProjectInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MProjectInformation_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MProjectInformation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MProjectInformation_.active));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MProjectInformation_.name));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MProjectInformation_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MProjectInformation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MProjectInformation_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
