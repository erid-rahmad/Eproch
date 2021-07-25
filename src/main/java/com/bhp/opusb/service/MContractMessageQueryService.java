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

import com.bhp.opusb.domain.MContractMessage;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractMessageRepository;
import com.bhp.opusb.service.dto.MContractMessageCriteria;
import com.bhp.opusb.service.dto.MContractMessageDTO;
import com.bhp.opusb.service.mapper.MContractMessageMapper;

/**
 * Service for executing complex queries for {@link MContractMessage} entities in the database.
 * The main input is a {@link MContractMessageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractMessageDTO} or a {@link Page} of {@link MContractMessageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractMessageQueryService extends QueryService<MContractMessage> {

    private final Logger log = LoggerFactory.getLogger(MContractMessageQueryService.class);

    private final MContractMessageRepository mContractMessageRepository;

    private final MContractMessageMapper mContractMessageMapper;

    public MContractMessageQueryService(MContractMessageRepository mContractMessageRepository, MContractMessageMapper mContractMessageMapper) {
        this.mContractMessageRepository = mContractMessageRepository;
        this.mContractMessageMapper = mContractMessageMapper;
    }

    /**
     * Return a {@link List} of {@link MContractMessageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractMessageDTO> findByCriteria(MContractMessageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContractMessage> specification = createSpecification(criteria);
        return mContractMessageMapper.toDto(mContractMessageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractMessageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractMessageDTO> findByCriteria(MContractMessageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContractMessage> specification = createSpecification(criteria);
        return mContractMessageRepository.findAll(specification, page)
            .map(mContractMessageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractMessageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContractMessage> specification = createSpecification(criteria);
        return mContractMessageRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractMessageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContractMessage> createSpecification(MContractMessageCriteria criteria) {
        Specification<MContractMessage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContractMessage_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContractMessage_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContractMessage_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContractMessage_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractId(),
                    root -> root.join(MContractMessage_.contract, JoinType.LEFT).get(MContract_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MContractMessage_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MContractMessage_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
        }
        return specification;
    }
}
