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

import com.bhp.opusb.domain.MVendorConfirmationContract;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorConfirmationContractRepository;
import com.bhp.opusb.service.dto.MVendorConfirmationContractCriteria;
import com.bhp.opusb.service.dto.MVendorConfirmationContractDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationContractMapper;

/**
 * Service for executing complex queries for {@link MVendorConfirmationContract} entities in the database.
 * The main input is a {@link MVendorConfirmationContractCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorConfirmationContractDTO} or a {@link Page} of {@link MVendorConfirmationContractDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorConfirmationContractQueryService extends QueryService<MVendorConfirmationContract> {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationContractQueryService.class);

    private final MVendorConfirmationContractRepository mVendorConfirmationContractRepository;

    private final MVendorConfirmationContractMapper mVendorConfirmationContractMapper;

    public MVendorConfirmationContractQueryService(MVendorConfirmationContractRepository mVendorConfirmationContractRepository, MVendorConfirmationContractMapper mVendorConfirmationContractMapper) {
        this.mVendorConfirmationContractRepository = mVendorConfirmationContractRepository;
        this.mVendorConfirmationContractMapper = mVendorConfirmationContractMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorConfirmationContractDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorConfirmationContractDTO> findByCriteria(MVendorConfirmationContractCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorConfirmationContract> specification = createSpecification(criteria);
        return mVendorConfirmationContractMapper.toDto(mVendorConfirmationContractRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorConfirmationContractDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationContractDTO> findByCriteria(MVendorConfirmationContractCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorConfirmationContract> specification = createSpecification(criteria);
        return mVendorConfirmationContractRepository.findAll(specification, page)
            .map(mVendorConfirmationContractMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorConfirmationContractCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorConfirmationContract> specification = createSpecification(criteria);
        return mVendorConfirmationContractRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorConfirmationContractCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorConfirmationContract> createSpecification(MVendorConfirmationContractCriteria criteria) {
        Specification<MVendorConfirmationContract> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorConfirmationContract_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorConfirmationContract_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorConfirmationContract_.active));
            }
            if (criteria.getContractStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractStartDate(), MVendorConfirmationContract_.contractStartDate));
            }
            if (criteria.getContractEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContractEndDate(), MVendorConfirmationContract_.contractEndDate));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorConfirmationContract_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getAttachmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAttachmentId(),
                    root -> root.join(MVendorConfirmationContract_.attachment, JoinType.LEFT).get(CAttachment_.id)));
            }
            if (criteria.getVendorConfirmationLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorConfirmationLineId(),
                    root -> root.join(MVendorConfirmationContract_.vendorConfirmationLine, JoinType.LEFT).get(MVendorConfirmationLine_.id)));
            }
        }
        return specification;
    }
}
