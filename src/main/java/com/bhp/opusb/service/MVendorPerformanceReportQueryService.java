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

import com.bhp.opusb.domain.MVendorPerformanceReport;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MVendorPerformanceReportRepository;
import com.bhp.opusb.service.dto.MVendorPerformanceReportCriteria;
import com.bhp.opusb.service.dto.MVendorPerformanceReportDTO;
import com.bhp.opusb.service.mapper.MVendorPerformanceReportMapper;

/**
 * Service for executing complex queries for {@link MVendorPerformanceReport} entities in the database.
 * The main input is a {@link MVendorPerformanceReportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MVendorPerformanceReportDTO} or a {@link Page} of {@link MVendorPerformanceReportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MVendorPerformanceReportQueryService extends QueryService<MVendorPerformanceReport> {

    private final Logger log = LoggerFactory.getLogger(MVendorPerformanceReportQueryService.class);

    private final MVendorPerformanceReportRepository mVendorPerformanceReportRepository;

    private final MVendorPerformanceReportMapper mVendorPerformanceReportMapper;

    public MVendorPerformanceReportQueryService(MVendorPerformanceReportRepository mVendorPerformanceReportRepository, MVendorPerformanceReportMapper mVendorPerformanceReportMapper) {
        this.mVendorPerformanceReportRepository = mVendorPerformanceReportRepository;
        this.mVendorPerformanceReportMapper = mVendorPerformanceReportMapper;
    }

    /**
     * Return a {@link List} of {@link MVendorPerformanceReportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MVendorPerformanceReportDTO> findByCriteria(MVendorPerformanceReportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MVendorPerformanceReport> specification = createSpecification(criteria);
        return mVendorPerformanceReportMapper.toDto(mVendorPerformanceReportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MVendorPerformanceReportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorPerformanceReportDTO> findByCriteria(MVendorPerformanceReportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MVendorPerformanceReport> specification = createSpecification(criteria);
        return mVendorPerformanceReportRepository.findAll(specification, page)
            .map(mVendorPerformanceReportMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MVendorPerformanceReportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MVendorPerformanceReport> specification = createSpecification(criteria);
        return mVendorPerformanceReportRepository.count(specification);
    }

    /**
     * Function to convert {@link MVendorPerformanceReportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MVendorPerformanceReport> createSpecification(MVendorPerformanceReportCriteria criteria) {
        Specification<MVendorPerformanceReport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MVendorPerformanceReport_.id));
            }
            if (criteria.getEvaluationScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEvaluationScore(), MVendorPerformanceReport_.evaluationScore));
            }
            if (criteria.getComplaints() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getComplaints(), MVendorPerformanceReport_.complaints));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MVendorPerformanceReport_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MVendorPerformanceReport_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
        }
        return specification;
    }
}
