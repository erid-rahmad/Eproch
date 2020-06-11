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

import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.ADTableRepository;
import com.bhp.opusb.service.dto.ADTableCriteria;
import com.bhp.opusb.service.dto.ADTableDTO;
import com.bhp.opusb.service.mapper.ADTableMapper;

/**
 * Service for executing complex queries for {@link ADTable} entities in the database.
 * The main input is a {@link ADTableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ADTableDTO} or a {@link Page} of {@link ADTableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ADTableQueryService extends QueryService<ADTable> {

    private final Logger log = LoggerFactory.getLogger(ADTableQueryService.class);

    private final ADTableRepository aDTableRepository;

    private final ADTableMapper aDTableMapper;

    public ADTableQueryService(ADTableRepository aDTableRepository, ADTableMapper aDTableMapper) {
        this.aDTableRepository = aDTableRepository;
        this.aDTableMapper = aDTableMapper;
    }

    /**
     * Return a {@link List} of {@link ADTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ADTableDTO> findByCriteria(ADTableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ADTable> specification = createSpecification(criteria);
        return aDTableMapper.toDto(aDTableRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ADTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ADTableDTO> findByCriteria(ADTableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ADTable> specification = createSpecification(criteria);
        return aDTableRepository.findAll(specification, page)
            .map(aDTableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ADTableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ADTable> specification = createSpecification(criteria);
        return aDTableRepository.count(specification);
    }

    /**
     * Function to convert {@link ADTableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ADTable> createSpecification(ADTableCriteria criteria) {
        Specification<ADTable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ADTable_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ADTable_.name));
            }
            if (criteria.getView() != null) {
                specification = specification.and(buildSpecification(criteria.getView(), ADTable_.view));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), ADTable_.active));
            }
            if (criteria.getADColumnId() != null) {
                specification = specification.and(buildSpecification(criteria.getADColumnId(),
                    root -> root.join(ADTable_.aDColumns, JoinType.LEFT).get(ADColumn_.id)));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(ADTable_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
