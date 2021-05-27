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

import com.bhp.opusb.domain.MVendorConfirmationLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorConfirmationLineRepository;
import com.bhp.opusb.service.dto.MVendorConfirmationLineCriteria;
import com.bhp.opusb.service.dto.MVendorConfirmationLineDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationLineMapper;

/**
 * Service for executing complex queries for {@link MVendorConfirmationLine} entities in the database.
 * The main input is a {@link MVendorConfirmationLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorConfirmationLineDTO} or a {@link Page} of {@link MVendorConfirmationLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorConfirmationLineQueryService extends QueryService<MVendorConfirmationLine> {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationLineQueryService.class);

    private final MVendorConfirmationLineRepository mVendorConfirmationLineRepository;

    private final MVendorConfirmationLineMapper mVendorConfirmationLineMapper;

    public MVendorConfirmationLineQueryService(MVendorConfirmationLineRepository mVendorConfirmationLineRepository, MVendorConfirmationLineMapper mVendorConfirmationLineMapper) {
        this.mVendorConfirmationLineRepository = mVendorConfirmationLineRepository;
        this.mVendorConfirmationLineMapper = mVendorConfirmationLineMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorConfirmationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorConfirmationLineDTO> findByCriteria(MVendorConfirmationLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorConfirmationLine> specification = createSpecification(criteria);
        return mVendorConfirmationLineMapper.toDto(mVendorConfirmationLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorConfirmationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationLineDTO> findByCriteria(MVendorConfirmationLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorConfirmationLine> specification = createSpecification(criteria);
        return mVendorConfirmationLineRepository.findAll(specification, page)
            .map(mVendorConfirmationLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorConfirmationLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorConfirmationLine> specification = createSpecification(criteria);
        return mVendorConfirmationLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorConfirmationLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorConfirmationLine> createSpecification(MVendorConfirmationLineCriteria criteria) {
        Specification<MVendorConfirmationLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorConfirmationLine_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MVendorConfirmationLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MVendorConfirmationLine_.active));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), MVendorConfirmationLine_.status));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MVendorConfirmationLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MVendorConfirmationLine_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getBiddingEvalResultId() != null) {
                specification = specification.and(buildSpecification(criteria.getBiddingEvalResultId(),
                    root -> root.join(MVendorConfirmationLine_.biddingEvalResult, JoinType.LEFT).get(MBiddingEvalResult_.id)));
            }
            if (criteria.getVendorConfirmationId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorConfirmationId(),
                    root -> root.join(MVendorConfirmationLine_.vendorConfirmation, JoinType.LEFT).get(MVendorConfirmation_.id)));
            }
        }
        return specification;
    }
}
