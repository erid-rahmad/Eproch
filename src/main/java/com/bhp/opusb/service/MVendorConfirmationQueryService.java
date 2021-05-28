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

import com.bhp.opusb.domain.MVendorConfirmation;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorConfirmationRepository;
import com.bhp.opusb.service.dto.MVendorConfirmationCriteria;
import com.bhp.opusb.service.dto.MVendorConfirmationDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationMapper;

/**
 * Service for executing complex queries for {@link MVendorConfirmation} entities in the database.
 * The main input is a {@link MVendorConfirmationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorConfirmationDTO} or a {@link Page} of {@link MVendorConfirmationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorConfirmationQueryService extends QueryService<MVendorConfirmation> {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationQueryService.class);

    private final MVendorConfirmationRepository mVendorConfirmationRepository;

    private final MVendorConfirmationMapper mVendorConfirmationMapper;

    public MVendorConfirmationQueryService(MVendorConfirmationRepository mVendorConfirmationRepository, MVendorConfirmationMapper mVendorConfirmationMapper) {
        this.mVendorConfirmationRepository = mVendorConfirmationRepository;
        this.mVendorConfirmationMapper = mVendorConfirmationMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorConfirmationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorConfirmationDTO> findByCriteria(MVendorConfirmationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorConfirmation> specification = createSpecification(criteria);
        return mVendorConfirmationMapper.toDto(mVendorConfirmationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorConfirmationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationDTO> findByCriteria(MVendorConfirmationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorConfirmation> specification = createSpecification(criteria);
        return mVendorConfirmationRepository.findAll(specification, page)
            .map(mVendorConfirmationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorConfirmationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorConfirmation> specification = createSpecification(criteria);
        return mVendorConfirmationRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorConfirmationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorConfirmation> createSpecification(MVendorConfirmationCriteria criteria) {
        Specification<MVendorConfirmation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorConfirmation_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorConfirmation_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorConfirmation_.active));
            }
            if (criteria.getBiddingId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingId(),
                    root -> root.join(MVendorConfirmation_.bidding, JoinType.LEFT).get(MBidding_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorConfirmation_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MVendorConfirmation_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MVendorConfirmation_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getPicId() != null) {
                specification = specification.and(buildSpecification(criteria.getPicId(),
                    root -> root.join(MVendorConfirmation_.pic, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
