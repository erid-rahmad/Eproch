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

import com.bhp.opusb.domain.CBiddingType;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CBiddingTypeRepository;
import com.bhp.opusb.service.dto.CBiddingTypeCriteria;
import com.bhp.opusb.service.dto.CBiddingTypeDTO;
import com.bhp.opusb.service.mapper.CBiddingTypeMapper;

/**
 * Service for executing complex queries for {@link CBiddingType} entities in the database.
 * The main input is a {@link CBiddingTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CBiddingTypeDTO} or a {@link Page} of {@link CBiddingTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CBiddingTypeQueryService extends QueryService<CBiddingType> {

    private final Logger log = LoggerFactory.getLogger(CBiddingTypeQueryService.class);

    private final CBiddingTypeRepository cBiddingTypeRepository;

    private final CBiddingTypeMapper cBiddingTypeMapper;

    public CBiddingTypeQueryService(CBiddingTypeRepository cBiddingTypeRepository, CBiddingTypeMapper cBiddingTypeMapper) {
        this.cBiddingTypeRepository = cBiddingTypeRepository;
        this.cBiddingTypeMapper = cBiddingTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CBiddingTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CBiddingTypeDTO> findByCriteria(CBiddingTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CBiddingType> specification = createSpecification(criteria);
        return cBiddingTypeMapper.toDto(cBiddingTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CBiddingTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CBiddingTypeDTO> findByCriteria(CBiddingTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CBiddingType> specification = createSpecification(criteria);
        return cBiddingTypeRepository.findAll(specification, page)
            .map(cBiddingTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CBiddingTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CBiddingType> specification = createSpecification(criteria);
        return cBiddingTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CBiddingTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CBiddingType> createSpecification(CBiddingTypeCriteria criteria) {
        Specification<CBiddingType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CBiddingType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CBiddingType_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CBiddingType_.description));
            }
            if (criteria.getCostEvaluationSelection() != null) {
                specification = specification.and(buildSpecification(criteria.getCostEvaluationSelection(), CBiddingType_.costEvaluationSelection));
            }
            if (criteria.getSelectedWinner() != null) {
                specification = specification.and(buildSpecification(criteria.getSelectedWinner(), CBiddingType_.selectedWinner));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CBiddingType_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CBiddingType_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CBiddingType_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
            if (criteria.getProductClassificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductClassificationId(),
                    root -> root.join(CBiddingType_.productClassification, JoinType.LEFT).get(CProductClassification_.id)));
            }
        }
        return specification;
    }
}
