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

import com.bhp.opusb.domain.AiExchangeOut;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AiExchangeOutRepository;
import com.bhp.opusb.service.dto.AiExchangeOutCriteria;
import com.bhp.opusb.service.dto.AiExchangeOutDTO;
import com.bhp.opusb.service.mapper.AiExchangeOutMapper;

/**
 * Service for executing complex queries for {@link AiExchangeOut} entities in the database.
 * The main input is a {@link AiExchangeOutCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AiExchangeOutDTO} or a {@link Page} of {@link AiExchangeOutDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AiExchangeOutQueryService extends QueryService<AiExchangeOut> {

    private final Logger log = LoggerFactory.getLogger(AiExchangeOutQueryService.class);

    private final AiExchangeOutRepository aiExchangeOutRepository;

    private final AiExchangeOutMapper aiExchangeOutMapper;

    public AiExchangeOutQueryService(AiExchangeOutRepository aiExchangeOutRepository, AiExchangeOutMapper aiExchangeOutMapper) {
        this.aiExchangeOutRepository = aiExchangeOutRepository;
        this.aiExchangeOutMapper = aiExchangeOutMapper;
    }

    /**
     * Return a {@link List} of {@link AiExchangeOutDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AiExchangeOutDTO> findByCriteria(AiExchangeOutCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AiExchangeOut> specification = createSpecification(criteria);
        return aiExchangeOutMapper.toDto(aiExchangeOutRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AiExchangeOutDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AiExchangeOutDTO> findByCriteria(AiExchangeOutCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AiExchangeOut> specification = createSpecification(criteria);
        return aiExchangeOutRepository.findAll(specification, page)
            .map(aiExchangeOutMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AiExchangeOutCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AiExchangeOut> specification = createSpecification(criteria);
        return aiExchangeOutRepository.count(specification);
    }

    /**
     * Function to convert {@link AiExchangeOutCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AiExchangeOut> createSpecification(AiExchangeOutCriteria criteria) {
        Specification<AiExchangeOut> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AiExchangeOut_.id));
            }
            if (criteria.getEntityType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntityType(), AiExchangeOut_.entityType));
            }
            if (criteria.getEntityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntityId(), AiExchangeOut_.entityId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), AiExchangeOut_.status));
            }
        }
        return specification;
    }
}
