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

import com.bhp.opusb.domain.MQuoteSupplier;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MQuoteSupplierRepository;
import com.bhp.opusb.service.dto.MQuoteSupplierCriteria;
import com.bhp.opusb.service.dto.MQuoteSupplierDTO;
import com.bhp.opusb.service.mapper.MQuoteSupplierMapper;

/**
 * Service for executing complex queries for {@link MQuoteSupplier} entities in the database.
 * The main input is a {@link MQuoteSupplierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MQuoteSupplierDTO} or a {@link Page} of {@link MQuoteSupplierDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MQuoteSupplierQueryService extends QueryService<MQuoteSupplier> {

    private final Logger log = LoggerFactory.getLogger(MQuoteSupplierQueryService.class);

    private final MQuoteSupplierRepository mQuoteSupplierRepository;

    private final MQuoteSupplierMapper mQuoteSupplierMapper;

    public MQuoteSupplierQueryService(MQuoteSupplierRepository mQuoteSupplierRepository, MQuoteSupplierMapper mQuoteSupplierMapper) {
        this.mQuoteSupplierRepository = mQuoteSupplierRepository;
        this.mQuoteSupplierMapper = mQuoteSupplierMapper;
    }

    /**
     * Return a {@link List} of {@link MQuoteSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MQuoteSupplierDTO> findByCriteria(MQuoteSupplierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MQuoteSupplier> specification = createSpecification(criteria);
        return mQuoteSupplierMapper.toDto(mQuoteSupplierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MQuoteSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MQuoteSupplierDTO> findByCriteria(MQuoteSupplierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MQuoteSupplier> specification = createSpecification(criteria);
        return mQuoteSupplierRepository.findAll(specification, page)
            .map(mQuoteSupplierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MQuoteSupplierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MQuoteSupplier> specification = createSpecification(criteria);
        return mQuoteSupplierRepository.count(specification);
    }

    /**
     * Function to convert {@link MQuoteSupplierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MQuoteSupplier> createSpecification(MQuoteSupplierCriteria criteria) {
        Specification<MQuoteSupplier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MQuoteSupplier_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MQuoteSupplier_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MQuoteSupplier_.active));
            }
            if (criteria.getDateRequired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRequired(), MQuoteSupplier_.dateRequired));
            }
            if (criteria.getQuotationId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuotationId(),
                    root -> root.join(MQuoteSupplier_.quotation, JoinType.LEFT).get(MRfq_.id)));
            }
            if (criteria.getBusinessClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessClassificationId(),
                    root -> root.join(MQuoteSupplier_.businessClassification, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MQuoteSupplier_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getBusinessSubCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessSubCategoryId(),
                    root -> root.join(MQuoteSupplier_.businessSubCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MQuoteSupplier_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
        }
        return specification;
    }
}
