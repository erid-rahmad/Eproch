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

import com.bhp.opusb.domain.MRequisition;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MRequisitionRepository;
import com.bhp.opusb.service.dto.MRequisitionCriteria;
import com.bhp.opusb.service.dto.MRequisitionDTO;
import com.bhp.opusb.service.mapper.MRequisitionMapper;

/**
 * Service for executing complex queries for {@link MRequisition} entities in the database.
 * The main input is a {@link MRequisitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MRequisitionDTO} or a {@link Page} of {@link MRequisitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MRequisitionQueryService extends QueryService<MRequisition> {

    private final Logger log = LoggerFactory.getLogger(MRequisitionQueryService.class);

    private final MRequisitionRepository mRequisitionRepository;

    private final MRequisitionMapper mRequisitionMapper;

    public MRequisitionQueryService(MRequisitionRepository mRequisitionRepository, MRequisitionMapper mRequisitionMapper) {
        this.mRequisitionRepository = mRequisitionRepository;
        this.mRequisitionMapper = mRequisitionMapper;
    }

    /**
     * Return a {@link List} of {@link MRequisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MRequisitionDTO> findByCriteria(MRequisitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MRequisition> specification = createSpecification(criteria);
        return mRequisitionMapper.toDto(mRequisitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MRequisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MRequisitionDTO> findByCriteria(MRequisitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MRequisition> specification = createSpecification(criteria);
        return mRequisitionRepository.findAll(specification, page)
            .map(mRequisitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MRequisitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MRequisition> specification = createSpecification(criteria);
        return mRequisitionRepository.count(specification);
    }

    /**
     * Function to convert {@link MRequisitionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MRequisition> createSpecification(MRequisitionCriteria criteria) {
        Specification<MRequisition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MRequisition_.id));
            }
            if (criteria.getDateTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTrx(), MRequisition_.dateTrx));
            }
            if (criteria.getDocumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentNo(), MRequisition_.documentNo));
            }
            if (criteria.getDocumentAction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentAction(), MRequisition_.documentAction));
            }
            if (criteria.getDocumentStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentStatus(), MRequisition_.documentStatus));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MRequisition_.approved));
            }
            if (criteria.getProcessed() != null) {
                specification = specification.and(buildSpecification(criteria.getProcessed(), MRequisition_.processed));
            }
            if (criteria.getDatePromised() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePromised(), MRequisition_.datePromised));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MRequisition_.description));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MRequisition_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MRequisition_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MRequisition_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getDocumentTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentTypeId(),
                    root -> root.join(MRequisition_.documentType, JoinType.LEFT).get(CDocumentType_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(MRequisition_.currency, JoinType.LEFT).get(CCurrency_.id)));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarehouseId(),
                    root -> root.join(MRequisition_.warehouse, JoinType.LEFT).get(CWarehouse_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MRequisition_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
        }
        return specification;
    }
}
