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

import com.bhp.opusb.domain.AiExchangeIn;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.AiExchangeInRepository;
import com.bhp.opusb.service.dto.AiExchangeInCriteria;
import com.bhp.opusb.service.dto.AiExchangeInDTO;
import com.bhp.opusb.service.mapper.AiExchangeInMapper;

/**
 * Service for executing complex queries for {@link AiExchangeIn} entities in the database.
 * The main input is a {@link AiExchangeInCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AiExchangeInDTO} or a {@link Page} of {@link AiExchangeInDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AiExchangeInQueryService extends QueryService<AiExchangeIn> {

    private final Logger log = LoggerFactory.getLogger(AiExchangeInQueryService.class);

    private final AiExchangeInRepository aiExchangeInRepository;

    private final AiExchangeInMapper aiExchangeInMapper;

    public AiExchangeInQueryService(AiExchangeInRepository aiExchangeInRepository, AiExchangeInMapper aiExchangeInMapper) {
        this.aiExchangeInRepository = aiExchangeInRepository;
        this.aiExchangeInMapper = aiExchangeInMapper;
    }

    /**
     * Return a {@link List} of {@link AiExchangeInDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AiExchangeInDTO> findByCriteria(AiExchangeInCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AiExchangeIn> specification = createSpecification(criteria);
        return aiExchangeInMapper.toDto(aiExchangeInRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AiExchangeInDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AiExchangeInDTO> findByCriteria(AiExchangeInCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AiExchangeIn> specification = createSpecification(criteria);
        return aiExchangeInRepository.findAll(specification, page)
            .map(aiExchangeInMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AiExchangeInCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AiExchangeIn> specification = createSpecification(criteria);
        return aiExchangeInRepository.count(specification);
    }

    /**
     * Function to convert {@link AiExchangeInCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AiExchangeIn> createSpecification(AiExchangeInCriteria criteria) {
        Specification<AiExchangeIn> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AiExchangeIn_.id));
            }
            if (criteria.getEntityType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntityType(), AiExchangeIn_.entityType));
            }
            if (criteria.getEntityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntityId(), AiExchangeIn_.entityId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), AiExchangeIn_.status));
            }
        }
        return specification;
    }
}
