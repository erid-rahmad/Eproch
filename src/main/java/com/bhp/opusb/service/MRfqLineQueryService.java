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

import com.bhp.opusb.domain.MRfqLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MRfqLineRepository;
import com.bhp.opusb.service.dto.MRfqLineCriteria;
import com.bhp.opusb.service.dto.MRfqLineDTO;
import com.bhp.opusb.service.mapper.MRfqLineMapper;

/**
 * Service for executing complex queries for {@link MRfqLine} entities in the database.
 * The main input is a {@link MRfqLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MRfqLineDTO} or a {@link Page} of {@link MRfqLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MRfqLineQueryService extends QueryService<MRfqLine> {

    private final Logger log = LoggerFactory.getLogger(MRfqLineQueryService.class);

    private final MRfqLineRepository mRfqLineRepository;

    private final MRfqLineMapper mRfqLineMapper;

    public MRfqLineQueryService(MRfqLineRepository mRfqLineRepository, MRfqLineMapper mRfqLineMapper) {
        this.mRfqLineRepository = mRfqLineRepository;
        this.mRfqLineMapper = mRfqLineMapper;
    }

    /**
     * Return a {@link List} of {@link MRfqLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MRfqLineDTO> findByCriteria(MRfqLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MRfqLine> specification = createSpecification(criteria);
        return mRfqLineMapper.toDto(mRfqLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MRfqLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqLineDTO> findByCriteria(MRfqLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MRfqLine> specification = createSpecification(criteria);
        return mRfqLineRepository.findAll(specification, page)
            .map(mRfqLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MRfqLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MRfqLine> specification = createSpecification(criteria);
        return mRfqLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MRfqLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MRfqLine> createSpecification(MRfqLineCriteria criteria) {
        Specification<MRfqLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MRfqLine_.id));
            }
            if (criteria.getLineNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNo(), MRfqLine_.lineNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MRfqLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MRfqLine_.active));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MRfqLine_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MRfqLine_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MRfqLine_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MRfqLine_.processed));
            }
            if (criteria.getReleaseQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReleaseQty(), MRfqLine_.releaseQty));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), MRfqLine_.unitPrice));
            }
            if (criteria.getOrderAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderAmount(), MRfqLine_.orderAmount));
            }
            if (criteria.getDocumentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentDate(), MRfqLine_.documentDate));
            }
            if (criteria.getDatePromised() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePromised(), MRfqLine_.datePromised));
            }
            if (criteria.getDateRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRequired(), MRfqLine_.dateRequired));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), MRfqLine_.remark));
            }
            if (criteria.getQuotationId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuotationId(),
                    root -> root.join(MRfqLine_.quotation, JoinType.LEFT).get(MRfq_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MRfqLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MRfqLine_.product, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MRfqLine_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MRfqLine_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessClassificationId(),
                    root -> root.join(MRfqLine_.businessClassification, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarehouseId(),
                    root -> root.join(MRfqLine_.warehouse, JoinType.LEFT).get(CWarehouse_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MRfqLine_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
        }
        return specification;
    }
}
