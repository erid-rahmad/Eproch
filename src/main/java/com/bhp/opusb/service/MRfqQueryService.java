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

import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MRfqRepository;
import com.bhp.opusb.service.dto.MRfqCriteria;
import com.bhp.opusb.service.dto.MRfqDTO;
import com.bhp.opusb.service.mapper.MRfqMapper;

/**
 * Service for executing complex queries for {@link MRfq} entities in the database.
 * The main input is a {@link MRfqCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MRfqDTO} or a {@link Page} of {@link MRfqDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MRfqQueryService extends QueryService<MRfq> {

    private final Logger log = LoggerFactory.getLogger(MRfqQueryService.class);

    private final MRfqRepository mRfqRepository;

    private final MRfqMapper mRfqMapper;

    public MRfqQueryService(MRfqRepository mRfqRepository, MRfqMapper mRfqMapper) {
        this.mRfqRepository = mRfqRepository;
        this.mRfqMapper = mRfqMapper;
    }

    /**
     * Return a {@link List} of {@link MRfqDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MRfqDTO> findByCriteria(MRfqCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MRfq> specification = createSpecification(criteria);
        return mRfqMapper.toDto(mRfqRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MRfqDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqDTO> findByCriteria(MRfqCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MRfq> specification = createSpecification(criteria);
        return mRfqRepository.findAll(specification, page)
            .map(mRfqMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MRfqCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MRfq> specification = createSpecification(criteria);
        return mRfqRepository.count(specification);
    }

    /**
     * Function to convert {@link MRfqCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MRfq> createSpecification(MRfqCriteria criteria) {
        Specification<MRfq> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MRfq_.id));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MRfq_.dateTrx));
            }
            if (criteria.getDateRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRequired(), MRfq_.dateRequired));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MRfq_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MRfq_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MRfq_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MRfq_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MRfq_.processed));
            }
            if (criteria.getGrandTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrandTotal(), MRfq_.grandTotal));
            }
            if (criteria.getDatePromised() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePromised(), MRfq_.datePromised));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MRfq_.description));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), MRfq_.title));
            }
            if (criteria.getSelectionMethod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSelectionMethod(), MRfq_.selectionMethod));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MRfq_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MRfq_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MRfq_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getBusinessClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessClassificationId(),
                    root -> root.join(MRfq_.businessClassification, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MRfq_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MRfq_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarehouseId(),
                    root -> root.join(MRfq_.warehouse, JoinType.LEFT).get(CWarehouse_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MRfq_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(MRfq_.documentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
        }
        return specification;
    }
}
