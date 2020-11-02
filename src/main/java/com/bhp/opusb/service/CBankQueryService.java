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

import com.bhp.opusb.domain.CBank;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CBankRepository;
import com.bhp.opusb.service.dto.CBankCriteria;
import com.bhp.opusb.service.dto.CBankDTO;
import com.bhp.opusb.service.mapper.CBankMapper;

/**
 * Service for executing complex queries for {@link CBank} entities in the database.
 * The main input is a {@link CBankCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CBankDTO} or a {@link Page} of {@link CBankDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CBankQueryService extends QueryService<CBank> {

    private final Logger log = LoggerFactory.getLogger(CBankQueryService.class);

    private final CBankRepository cBankRepository;

    private final CBankMapper cBankMapper;

    public CBankQueryService(CBankRepository cBankRepository, CBankMapper cBankMapper) {
        this.cBankRepository = cBankRepository;
        this.cBankMapper = cBankMapper;
    }

    /**
     * Return a {@link List} of {@link CBankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CBankDTO> findByCriteria(CBankCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CBank> specification = createSpecification(criteria);
        return cBankMapper.toDto(cBankRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CBankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CBankDTO> findByCriteria(CBankCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CBank> specification = createSpecification(criteria);
        return cBankRepository.findAll(specification, page)
            .map(cBankMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CBankCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CBank> specification = createSpecification(criteria);
        return cBankRepository.count(specification);
    }

    /**
     * Function to convert {@link CBankCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CBank> createSpecification(CBankCriteria criteria) {
        Specification<CBank> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CBank_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CBank_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), CBank_.value));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), CBank_.shortName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CBank_.description));
            }
            if (criteria.getSwiftCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSwiftCode(), CBank_.swiftCode));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CBank_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CBank_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CBank_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
