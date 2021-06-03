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

import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MAuctionRepository;
import com.bhp.opusb.service.dto.MAuctionCriteria;
import com.bhp.opusb.service.dto.MAuctionDTO;
import com.bhp.opusb.service.mapper.MAuctionMapper;

/**
 * Service for executing complex queries for {@link MAuction} entities in the database.
 * The main input is a {@link MAuctionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MAuctionDTO} or a {@link Page} of {@link MAuctionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MAuctionQueryService extends QueryService<MAuction> {

    private final Logger log = LoggerFactory.getLogger(MAuctionQueryService.class);

    private final MAuctionRepository mAuctionRepository;

    private final MAuctionMapper mAuctionMapper;

    public MAuctionQueryService(MAuctionRepository mAuctionRepository, MAuctionMapper mAuctionMapper) {
        this.mAuctionRepository = mAuctionRepository;
        this.mAuctionMapper = mAuctionMapper;
    }

    /**
     * Return a {@link List} of {@link MAuctionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MAuctionDTO> findByCriteria(MAuctionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MAuction> specification = createSpecification(criteria);
        return mAuctionMapper.toDto(mAuctionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MAuctionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionDTO> findByCriteria(MAuctionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MAuction> specification = createSpecification(criteria);
        return mAuctionRepository.findAll(specification, page)
            .map(mAuctionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MAuctionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MAuction> specification = createSpecification(criteria);
        return mAuctionRepository.count(specification);
    }

    /**
     * Function to convert {@link MAuctionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MAuction> createSpecification(MAuctionCriteria criteria) {
        Specification<MAuction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MAuction_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MAuction_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MAuction_.description));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MAuction_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MAuction_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MAuction_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MAuction_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MAuction_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MAuction_.processed));
            }
            if (criteria.getDateApprove() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateApprove(), MAuction_.dateApprove));
            }
            if (criteria.getDateReject() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReject(), MAuction_.dateReject));
            }
            if (criteria.getRejectedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRejectedReason(), MAuction_.rejectedReason));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MAuction_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MAuction_.active));
            }
            if (criteria.getContentId() != null) {
                specification = specification.and(buildSpecification(criteria.getContentId(),
                    root -> root.join(MAuction_.content, JoinType.LEFT).get(MAuctionContent_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MAuction_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MAuction_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MAuction_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getOwnerUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getOwnerUserId(),
                    root -> root.join(MAuction_.owner, JoinType.LEFT).get(AdUser_.id)));
            }
        }
        return specification;
    }
}
