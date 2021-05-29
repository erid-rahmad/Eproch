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

import com.bhp.opusb.domain.MVendorConfirmationResponse;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorConfirmationResponseRepository;
import com.bhp.opusb.service.dto.MVendorConfirmationResponseCriteria;
import com.bhp.opusb.service.dto.MVendorConfirmationResponseDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationResponseMapper;

/**
 * Service for executing complex queries for {@link MVendorConfirmationResponse} entities in the database.
 * The main input is a {@link MVendorConfirmationResponseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorConfirmationResponseDTO} or a {@link Page} of {@link MVendorConfirmationResponseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorConfirmationResponseQueryService extends QueryService<MVendorConfirmationResponse> {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationResponseQueryService.class);

    private final MVendorConfirmationResponseRepository mVendorConfirmationResponseRepository;

    private final MVendorConfirmationResponseMapper mVendorConfirmationResponseMapper;

    public MVendorConfirmationResponseQueryService(MVendorConfirmationResponseRepository mVendorConfirmationResponseRepository, MVendorConfirmationResponseMapper mVendorConfirmationResponseMapper) {
        this.mVendorConfirmationResponseRepository = mVendorConfirmationResponseRepository;
        this.mVendorConfirmationResponseMapper = mVendorConfirmationResponseMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorConfirmationResponseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorConfirmationResponseDTO> findByCriteria(MVendorConfirmationResponseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorConfirmationResponse> specification = createSpecification(criteria);
        return mVendorConfirmationResponseMapper.toDto(mVendorConfirmationResponseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorConfirmationResponseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationResponseDTO> findByCriteria(MVendorConfirmationResponseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorConfirmationResponse> specification = createSpecification(criteria);
        return mVendorConfirmationResponseRepository.findAll(specification, page)
            .map(mVendorConfirmationResponseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorConfirmationResponseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorConfirmationResponse> specification = createSpecification(criteria);
        return mVendorConfirmationResponseRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorConfirmationResponseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorConfirmationResponse> createSpecification(MVendorConfirmationResponseCriteria criteria) {
        Specification<MVendorConfirmationResponse> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorConfirmationResponse_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorConfirmationResponse_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorConfirmationResponse_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorConfirmationResponse_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorConfirmationLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorConfirmationLineId(),
                    root -> root.join(MVendorConfirmationResponse_.vendorConfirmationLine, JoinType.LEFT).get(MVendorConfirmationLine_.id)));
            }
            if (criteria.getVendorConfirmationContractId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorConfirmationContractId(),
                    root -> root.join(MVendorConfirmationResponse_.vendorConfirmationContract, JoinType.LEFT).get(MVendorConfirmationContract_.id)));
            }
        }
        return specification;
    }
}
