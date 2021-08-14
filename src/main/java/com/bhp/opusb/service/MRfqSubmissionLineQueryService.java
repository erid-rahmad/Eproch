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

import com.bhp.opusb.domain.MRfqSubmissionLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MRfqSubmissionLineRepository;
import com.bhp.opusb.service.dto.MRfqSubmissionLineCriteria;
import com.bhp.opusb.service.dto.MRfqSubmissionLineDTO;
import com.bhp.opusb.service.mapper.MRfqSubmissionLineMapper;

/**
 * Service for executing complex queries for {@link MRfqSubmissionLine} entities in the database.
 * The main input is a {@link MRfqSubmissionLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MRfqSubmissionLineDTO} or a {@link Page} of {@link MRfqSubmissionLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MRfqSubmissionLineQueryService extends QueryService<MRfqSubmissionLine> {

    private final Logger log = LoggerFactory.getLogger(MRfqSubmissionLineQueryService.class);

    private final MRfqSubmissionLineRepository mRfqSubmissionLineRepository;

    private final MRfqSubmissionLineMapper mRfqSubmissionLineMapper;

    public MRfqSubmissionLineQueryService(MRfqSubmissionLineRepository mRfqSubmissionLineRepository, MRfqSubmissionLineMapper mRfqSubmissionLineMapper) {
        this.mRfqSubmissionLineRepository = mRfqSubmissionLineRepository;
        this.mRfqSubmissionLineMapper = mRfqSubmissionLineMapper;
    }

    /**
     * Return a {@link List} of {@link MRfqSubmissionLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MRfqSubmissionLineDTO> findByCriteria(MRfqSubmissionLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MRfqSubmissionLine> specification = createSpecification(criteria);
        return mRfqSubmissionLineMapper.toDto(mRfqSubmissionLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MRfqSubmissionLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqSubmissionLineDTO> findByCriteria(MRfqSubmissionLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MRfqSubmissionLine> specification = createSpecification(criteria);
        return mRfqSubmissionLineRepository.findAll(specification, page)
            .map(mRfqSubmissionLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MRfqSubmissionLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MRfqSubmissionLine> specification = createSpecification(criteria);
        return mRfqSubmissionLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MRfqSubmissionLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MRfqSubmissionLine> createSpecification(MRfqSubmissionLineCriteria criteria) {
        Specification<MRfqSubmissionLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MRfqSubmissionLine_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MRfqSubmissionLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MRfqSubmissionLine_.active));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MRfqSubmissionLine_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MRfqSubmissionLine_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MRfqSubmissionLine_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MRfqSubmissionLine_.processed));
            }
            if (criteria.getReleaseQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReleaseQty(), MRfqSubmissionLine_.releaseQty));
            }
            if (criteria.getSubmissionPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmissionPrice(), MRfqSubmissionLine_.submissionPrice));
            }
            if (criteria.getTotalSubmissionPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalSubmissionPrice(), MRfqSubmissionLine_.totalSubmissionPrice));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), MRfqSubmissionLine_.remark));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MRfqSubmissionLine_.dateTrx));
            }
            if (criteria.getDateRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRequired(), MRfqSubmissionLine_.dateRequired));
            }
            if (criteria.getDateSubmitted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateSubmitted(), MRfqSubmissionLine_.dateSubmitted));
            }
            if (criteria.getSubmissionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubmissionId(),
                    root -> root.join(MRfqSubmissionLine_.submission, JoinType.LEFT).get(MRfqSubmission_.id)));
            }
            if (criteria.getQuotationLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuotationLineId(),
                    root -> root.join(MRfqSubmissionLine_.quotationLine, JoinType.LEFT).get(MRfqLine_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MRfqSubmissionLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MRfqSubmissionLine_.product, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MRfqSubmissionLine_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MRfqSubmissionLine_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
        }
        return specification;
    }
}
