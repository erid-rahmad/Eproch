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

import com.bhp.opusb.domain.MComplaint;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MComplaintRepository;
import com.bhp.opusb.service.dto.MComplaintCriteria;
import com.bhp.opusb.service.dto.MComplaintDTO;
import com.bhp.opusb.service.mapper.MComplaintMapper;

/**
 * Service for executing complex queries for {@link MComplaint} entities in the database.
 * The main input is a {@link MComplaintCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MComplaintDTO} or a {@link Page} of {@link MComplaintDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MComplaintQueryService extends QueryService<MComplaint> {

    private final Logger log = LoggerFactory.getLogger(MComplaintQueryService.class);

    private final MComplaintRepository mComplaintRepository;

    private final MComplaintMapper mComplaintMapper;

    public MComplaintQueryService(MComplaintRepository mComplaintRepository, MComplaintMapper mComplaintMapper) {
        this.mComplaintRepository = mComplaintRepository;
        this.mComplaintMapper = mComplaintMapper;
    }

    /**
     * Return a {@link List} of {@link MComplaintDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MComplaintDTO> findByCriteria(MComplaintCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MComplaint> specification = createSpecification(criteria);
        return mComplaintMapper.toDto(mComplaintRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MComplaintDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MComplaintDTO> findByCriteria(MComplaintCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MComplaint> specification = createSpecification(criteria);
        return mComplaintRepository.findAll(specification, page)
            .map(mComplaintMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MComplaintCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MComplaint> specification = createSpecification(criteria);
        return mComplaintRepository.count(specification);
    }

    /**
     * Function to convert {@link MComplaintCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MComplaint> createSpecification(MComplaintCriteria criteria) {
        Specification<MComplaint> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MComplaint_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MComplaint_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MComplaint_.active));
            }
            if (criteria.getReportDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReportDate(), MComplaint_.reportDate));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), MComplaint_.type));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MComplaint_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MComplaint_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MComplaint_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MComplaint_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MComplaint_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MComplaint_.processed));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MComplaint_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(MComplaint_.vendor, JoinType.LEFT).get(CVendor_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MComplaint_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractId(),
                    root -> root.join(MComplaint_.contract, JoinType.LEFT).get(MContract_.id)));
            }
            if (criteria.getBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getBusinessCategoryId(),
                    root -> root.join(MComplaint_.businessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
            if (criteria.getSubBusinessCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubBusinessCategoryId(),
                    root -> root.join(MComplaint_.subBusinessCategory, JoinType.LEFT).get(CBusinessCategory_.id)));
            }
        }
        return specification;
    }
}
