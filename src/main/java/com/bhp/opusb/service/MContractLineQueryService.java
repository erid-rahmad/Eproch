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

import com.bhp.opusb.domain.MContractLine;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MContractLineRepository;
import com.bhp.opusb.service.dto.MContractLineCriteria;
import com.bhp.opusb.service.dto.MContractLineDTO;
import com.bhp.opusb.service.mapper.MContractLineMapper;

/**
 * Service for executing complex queries for {@link MContractLine} entities in the database.
 * The main input is a {@link MContractLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MContractLineDTO} or a {@link Page} of {@link MContractLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MContractLineQueryService extends QueryService<MContractLine> {

    private final Logger log = LoggerFactory.getLogger(MContractLineQueryService.class);

    private final MContractLineRepository mContractLineRepository;

    private final MContractLineMapper mContractLineMapper;

    public MContractLineQueryService(MContractLineRepository mContractLineRepository, MContractLineMapper mContractLineMapper) {
        this.mContractLineRepository = mContractLineRepository;
        this.mContractLineMapper = mContractLineMapper;
    }

    /**
     * Return a {@link List} of {@link MContractLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MContractLineDTO> findByCriteria(MContractLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MContractLine> specification = createSpecification(criteria);
        return mContractLineMapper.toDto(mContractLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MContractLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractLineDTO> findByCriteria(MContractLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MContractLine> specification = createSpecification(criteria);
        return mContractLineRepository.findAll(specification, page)
            .map(mContractLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MContractLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MContractLine> specification = createSpecification(criteria);
        return mContractLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MContractLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MContractLine> createSpecification(MContractLineCriteria criteria) {
        Specification<MContractLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MContractLine_.id));
            }
            if (criteria.getLineNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNo(), MContractLine_.lineNo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), MContractLine_.quantity));
            }
            if (criteria.getQuantityBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantityBalance(), MContractLine_.quantityBalance));
            }
            if (criteria.getCeilingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCeilingPrice(), MContractLine_.ceilingPrice));
            }
            if (criteria.getTotalCeilingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalCeilingPrice(), MContractLine_.totalCeilingPrice));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), MContractLine_.deliveryDate));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), MContractLine_.remark));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MContractLine_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MContractLine_.active));
            }
            if (criteria.getContractId() != null) {
                specification = specification.and(buildSpecification(criteria.getContractId(),
                    root -> root.join(MContractLine_.contract, JoinType.LEFT).get(MContract_.id)));
            }
            if (criteria.getContractNo() != null) {
                specification = specification.and(buildSpecification(criteria.getContractNo(),
                    root -> root.join(MContractLine_.contract, JoinType.LEFT).get(MContract_.documentNo)));
            }
            if (criteria.getContractStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getContractStatus(),
                    root -> root.join(MContractLine_.contract, JoinType.LEFT).get(MContract_.documentStatus)));
            }

            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(MContractLine_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getCostCenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCostCenterId(),
                    root -> root.join(MContractLine_.costCenter, JoinType.LEFT).get(CCostCenter_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(MContractLine_.product, JoinType.LEFT).get(CProduct_.id)));
            }
            if (criteria.getUomId() != null) {
                specification = specification.and(buildSpecification(criteria.getUomId(),
                    root -> root.join(MContractLine_.uom, JoinType.LEFT).get(CUnitOfMeasure_.id)));
            }
        }
        return specification;
    }
}
