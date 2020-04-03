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

import com.bhp.opusb.domain.ReferenceList;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ReferenceListRepository;
import com.bhp.opusb.service.dto.ReferenceListCriteria;
import com.bhp.opusb.service.dto.ReferenceListDTO;
import com.bhp.opusb.service.mapper.ReferenceListMapper;

/**
 * Service for executing complex queries for {@link ReferenceList} entities in the database.
 * The main input is a {@link ReferenceListCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReferenceListDTO} or a {@link Page} of {@link ReferenceListDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReferenceListQueryService extends QueryService<ReferenceList> {

    private final Logger log = LoggerFactory.getLogger(ReferenceListQueryService.class);

    private final ReferenceListRepository referenceListRepository;

    private final ReferenceListMapper referenceListMapper;

    public ReferenceListQueryService(ReferenceListRepository referenceListRepository, ReferenceListMapper referenceListMapper) {
        this.referenceListRepository = referenceListRepository;
        this.referenceListMapper = referenceListMapper;
    }

    /**
     * Return a {@link List} of {@link ReferenceListDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReferenceListDTO> findByCriteria(ReferenceListCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReferenceList> specification = createSpecification(criteria);
        return referenceListMapper.toDto(referenceListRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReferenceListDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferenceListDTO> findByCriteria(ReferenceListCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReferenceList> specification = createSpecification(criteria);
        return referenceListRepository.findAll(specification, page)
            .map(referenceListMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReferenceListCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReferenceList> specification = createSpecification(criteria);
        return referenceListRepository.count(specification);
    }

    /**
     * Function to convert {@link ReferenceListCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReferenceList> createSpecification(ReferenceListCriteria criteria) {
        Specification<ReferenceList> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReferenceList_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ReferenceList_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ReferenceList_.description));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ReferenceList_.value));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenceId(),
                    root -> root.join(ReferenceList_.reference, JoinType.LEFT).get(Reference_.id)));
            }
        }
        return specification;
    }
}
