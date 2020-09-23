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

import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.CWarehouseRepository;
import com.bhp.opusb.service.dto.CWarehouseCriteria;
import com.bhp.opusb.service.dto.CWarehouseDTO;
import com.bhp.opusb.service.mapper.CWarehouseMapper;

/**
 * Service for executing complex queries for {@link CWarehouse} entities in the database.
 * The main input is a {@link CWarehouseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CWarehouseDTO} or a {@link Page} of {@link CWarehouseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CWarehouseQueryService extends QueryService<CWarehouse> {

    private final Logger log = LoggerFactory.getLogger(CWarehouseQueryService.class);

    private final CWarehouseRepository cWarehouseRepository;

    private final CWarehouseMapper cWarehouseMapper;

    public CWarehouseQueryService(CWarehouseRepository cWarehouseRepository, CWarehouseMapper cWarehouseMapper) {
        this.cWarehouseRepository = cWarehouseRepository;
        this.cWarehouseMapper = cWarehouseMapper;
    }

    /**
     * Return a {@link List} of {@link CWarehouseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CWarehouseDTO> findByCriteria(CWarehouseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CWarehouse> specification = createSpecification(criteria);
        return cWarehouseMapper.toDto(cWarehouseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CWarehouseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CWarehouseDTO> findByCriteria(CWarehouseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CWarehouse> specification = createSpecification(criteria);
        return cWarehouseRepository.findAll(specification, page)
            .map(cWarehouseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CWarehouseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CWarehouse> specification = createSpecification(criteria);
        return cWarehouseRepository.count(specification);
    }

    /**
     * Function to convert {@link CWarehouseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CWarehouse> createSpecification(CWarehouseCriteria criteria) {
        Specification<CWarehouse> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CWarehouse_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CWarehouse_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CWarehouse_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CWarehouse_.description));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), CWarehouse_.address));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), CWarehouse_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), CWarehouse_.active));
            }
            if (criteria.getAdOrganizationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdOrganizationId(),
                    root -> root.join(CWarehouse_.adOrganization, JoinType.LEFT).get(ADOrganization_.id)));
            }
        }
        return specification;
    }
}
