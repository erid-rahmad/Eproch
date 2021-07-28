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

import com.bhp.opusb.domain.MContractTaskNegotiation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractTaskNegotiationRepository;
import com.bhp.opusb.service.dto.MContractTaskNegotiationCriteria;
import com.bhp.opusb.service.dto.MContractTaskNegotiationDTO;
import com.bhp.opusb.service.mapper.MContractTaskNegotiationMapper;

/**
 * Service for executing complex queries for {@link MContractTaskNegotiation} entities in the database.
 * The main input is a {@link MContractTaskNegotiationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractTaskNegotiationDTO} or a {@link Page} of {@link MContractTaskNegotiationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractTaskNegotiationQueryService extends QueryService<MContractTaskNegotiation> {

    private final Logger log = LoggerFactory.getLogger(MContractTaskNegotiationQueryService.class);

    private final MContractTaskNegotiationRepository mContractTaskNegotiationRepository;

    private final MContractTaskNegotiationMapper mContractTaskNegotiationMapper;

    public MContractTaskNegotiationQueryService(MContractTaskNegotiationRepository mContractTaskNegotiationRepository, MContractTaskNegotiationMapper mContractTaskNegotiationMapper) {
        this.mContractTaskNegotiationRepository = mContractTaskNegotiationRepository;
        this.mContractTaskNegotiationMapper = mContractTaskNegotiationMapper;
    }

    /**
     * Return a {@link List} of {@link MContractTaskNegotiationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractTaskNegotiationDTO> findByCriteria(MContractTaskNegotiationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContractTaskNegotiation> specification = createSpecification(criteria);
        return mContractTaskNegotiationMapper.toDto(mContractTaskNegotiationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractTaskNegotiationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTaskNegotiationDTO> findByCriteria(MContractTaskNegotiationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContractTaskNegotiation> specification = createSpecification(criteria);
        return mContractTaskNegotiationRepository.findAll(specification, page)
            .map(mContractTaskNegotiationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractTaskNegotiationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContractTaskNegotiation> specification = createSpecification(criteria);
        return mContractTaskNegotiationRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractTaskNegotiationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContractTaskNegotiation> createSpecification(MContractTaskNegotiationCriteria criteria) {
        Specification<MContractTaskNegotiation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContractTaskNegotiation_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MContractTaskNegotiation_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContractTaskNegotiation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContractTaskNegotiation_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContractTaskNegotiation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getContractTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractTaskId(),
                    root -> root.join(MContractTaskNegotiation_.contractTask, JoinType.LEFT).get(MContractTask_.id)));
            }
            if (criteria.getCAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCAttachmentId(),
                    root -> root.join(MContractTaskNegotiation_.cAttachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
