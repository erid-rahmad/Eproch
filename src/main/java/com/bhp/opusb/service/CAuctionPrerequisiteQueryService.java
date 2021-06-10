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

import com.bhp.opusb.domain.CAuctionPrerequisite;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CAuctionPrerequisiteRepository;
import com.bhp.opusb.service.dto.CAuctionPrerequisiteCriteria;
import com.bhp.opusb.service.dto.CAuctionPrerequisiteDTO;
import com.bhp.opusb.service.mapper.CAuctionPrerequisiteMapper;

/**
 * Service for executing complex queries for {@link CAuctionPrerequisite} entities in the database.
 * The main input is a {@link CAuctionPrerequisiteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CAuctionPrerequisiteDTO} or a {@link Page} of {@link CAuctionPrerequisiteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CAuctionPrerequisiteQueryService extends QueryService<CAuctionPrerequisite> {

    private final Logger log = LoggerFactory.getLogger(CAuctionPrerequisiteQueryService.class);

    private final CAuctionPrerequisiteRepository cAuctionPrerequisiteRepository;

    private final CAuctionPrerequisiteMapper cAuctionPrerequisiteMapper;

    public CAuctionPrerequisiteQueryService(CAuctionPrerequisiteRepository cAuctionPrerequisiteRepository, CAuctionPrerequisiteMapper cAuctionPrerequisiteMapper) {
        this.cAuctionPrerequisiteRepository = cAuctionPrerequisiteRepository;
        this.cAuctionPrerequisiteMapper = cAuctionPrerequisiteMapper;
    }

    /**
     * Return a {@link List} of {@link CAuctionPrerequisiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CAuctionPrerequisiteDTO> findByCriteria(CAuctionPrerequisiteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CAuctionPrerequisite> specification = createSpecification(criteria);
        return cAuctionPrerequisiteMapper.toDto(cAuctionPrerequisiteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CAuctionPrerequisiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CAuctionPrerequisiteDTO> findByCriteria(CAuctionPrerequisiteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CAuctionPrerequisite> specification = createSpecification(criteria);
        return cAuctionPrerequisiteRepository.findAll(specification, page)
            .map(cAuctionPrerequisiteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CAuctionPrerequisiteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CAuctionPrerequisite> specification = createSpecification(criteria);
        return cAuctionPrerequisiteRepository.count(specification);
    }

    /**
     * Function to convert {@link CAuctionPrerequisiteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CAuctionPrerequisite> createSpecification(CAuctionPrerequisiteCriteria criteria) {
        Specification<CAuctionPrerequisite> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CAuctionPrerequisite_.id));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CAuctionPrerequisite_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CAuctionPrerequisite_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CAuctionPrerequisite_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
