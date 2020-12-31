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

import com.bhp.opusb.domain.MBrand;
import com.bhp.opusb.domain.*; // for static metamodels
import com.bhp.opusb.repository.MBrandRepository;
import com.bhp.opusb.service.dto.MBrandCriteria;
import com.bhp.opusb.service.dto.MBrandDTO;
import com.bhp.opusb.service.mapper.MBrandMapper;

/**
 * Service for executing complex queries for {@link MBrand} entities in the database.
 * The main input is a {@link MBrandCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MBrandDTO} or a {@link Page} of {@link MBrandDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MBrandQueryService extends QueryService<MBrand> {

    private final Logger log = LoggerFactory.getLogger(MBrandQueryService.class);

    private final MBrandRepository mBrandRepository;

    private final MBrandMapper mBrandMapper;

    public MBrandQueryService(MBrandRepository mBrandRepository, MBrandMapper mBrandMapper) {
        this.mBrandRepository = mBrandRepository;
        this.mBrandMapper = mBrandMapper;
    }

    /**
     * Return a {@link List} of {@link MBrandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MBrandDTO> findByCriteria(MBrandCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MBrand> specification = createSpecification(criteria);
        return mBrandMapper.toDto(mBrandRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MBrandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MBrandDTO> findByCriteria(MBrandCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MBrand> specification = createSpecification(criteria);
        return mBrandRepository.findAll(specification, page)
            .map(mBrandMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MBrandCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MBrand> specification = createSpecification(criteria);
        return mBrandRepository.count(specification);
    }

    /**
     * Function to convert {@link MBrandCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MBrand> createSpecification(MBrandCriteria criteria) {
        Specification<MBrand> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MBrand_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MBrand_.name));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), MBrand_.uid));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), MBrand_.active));
            }
        }
        return specification;
    }
}
